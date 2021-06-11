package no.nnsn.quakemlwebserviceingestorexecutable.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import lombok.extern.slf4j.Slf4j;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.quakemlwebserviceingestorexecutable.service.CatalogService;
import no.nnsn.quakemlwebserviceingestorexecutable.service.SfileEventService;
import no.nnsn.quakemlwebserviceingestorexecutable.service.SfileCheckerService;
import no.nnsn.quakemlwebserviceingestorexecutable.utils.FileChecker;
import no.nnsn.quakemlwebserviceingestorexecutable.utils.IngestLog;
import no.nnsn.quakemlwebserviceingestorexecutable.utils.TimeLogger;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.QuakeML;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.util.*;

@Slf4j
@Component
public class Ingestor {

    final CatalogService catalogService;
    final SfileEventService sfileEventService;
    final SfileCheckerService sfileCheckerService;
    final Arguments arguments;
    final Converter converter;

    @Autowired
    public Ingestor(CatalogService catalogService, SfileEventService sfileEventService, SfileCheckerService sfileCheckerService, Arguments arguments, Converter converter) {
        this.catalogService = catalogService;
        this.sfileEventService = sfileEventService;
        this.sfileCheckerService = sfileCheckerService;
        this.arguments = arguments;
        this.converter = converter;
    }

    public String getPath() {
        return arguments.hasInput() ? arguments.getInput() : arguments.getCurrentPath();
    }

    public void printFilecount(FileInfo fileInfo) {
        log.info("Number of S-files found: " + fileInfo.getsFileCount());
    }

    public List<SfileCheck> getSfileListFromDB() {
        return sfileCheckerService.getSfileListAll();
    }

    public void ingest(FileInfo fileInfo, String path) throws Exception {

        Instant start = Instant.now();
        System.out.println("Checking S-files for new files or changes to existing...");

        final IngestLog ingestLog = new IngestLog();

        // If catalog status is equal to "default" then read map name, otherwise override with argument
        String catalogName = (arguments.catalogFromPath()) ? fileInfo.getCatalogName() : arguments.getCatalog();
        System.out.println("catalog from arg name: " + catalogName);

        List<SfileCheck> sfileChecks = sfileEventService.getSfileListByCatalogName(catalogName);
        Map<String, String> filesMap = new HashMap<>();

        fileInfo.getFilePaths().forEach(p -> {
            filesMap.put(p.getFileName().toString(), p.toString());
        });

        int mapSizeBeforeCheck = filesMap.size();
        log.info("S-files within database: " + sfileChecks.size());
        log.info("S-files within catalog: " + mapSizeBeforeCheck);

        // Check if files has been modified since last update
        Map<String, String> modFilesMap = new HashMap<>(); // map for keeping record of modified files
        Map<String, String> newFilesMap = new HashMap<>();
        Set<String> delFilesSet = new HashSet<>();

        if (sfileChecks.size() > 0) {
            for (SfileCheck sCheck: sfileChecks) {
                final String dbSffileID = sCheck.getSfileID();
                final String dbSfileChecksum = sCheck.getChecksum();

                if (filesMap.containsKey(dbSffileID)) {
                    String p = filesMap.get(dbSffileID);
                    if (FileChecker.fileUnchanged(p, dbSfileChecksum)) {
                        fileInfo.addSfileEqual();

                        // If force then unchanged files should be updated
                        if (arguments.forceIngestion()) {
                            modFilesMap.put(dbSffileID, p);
                        }
                        filesMap.remove(dbSffileID);

                    } else {
                        modFilesMap.put(dbSffileID, p);
                        filesMap.remove(dbSffileID);
                    }
                } else {
                    delFilesSet.add(dbSffileID);
                }
            }
        }

        // Remaining files should be new
        newFilesMap = filesMap;
        log.info("New files: " + filesMap.size());
        log.info("Modified files: " + modFilesMap.size());
        log.info("Deleted files: " + delFilesSet.size());

        Instant fileCheckFinish = Instant.now();
        log.info(TimeLogger.getTimeUsed(start, fileCheckFinish, "File check time used: "));

        // Delete removed s-files
        deleteFiles(delFilesSet);

        // Start reading files and ingest
        List<IgnoredLineError> convErrors = new ArrayList<>();
        Catalog catalog = catalogInfoHandler(catalogName);
        if (newFilesMap != null && newFilesMap.size() > 0) {
            convErrors.addAll(ingestNewOrUpdateFiles(catalog, ingestLog, newFilesMap));
        }
        if (modFilesMap != null && modFilesMap.size() > 0) {
            convErrors.addAll(ingestNewOrUpdateFiles(catalog, ingestLog, modFilesMap));
        }

        if (convErrors != null && convErrors.size() > 0) {
            log.info("-------------------------------------");
            log.info("Errors found in file and are ignored:");
            convErrors.forEach(er -> {
                log.info(
                        "File: " + er.getFilename() +
                                " | Row: " + er.getRowNumber() +
                                " | Error: " + er.getMessage() +
                                " | LineText:" + er.getLine().getLineText()
                );
            });
            log.info("-------------------------------------");
        } else {
            log.info("No errors found");
        }


        log.info("Number of unchanged files: " + fileInfo.getSFileEqualCount());

        // System.out.println("Number of events ingested: " + ingestLog.getEvents());
        log.info("Number of files ingested: " + ingestLog.getFiles());

        Instant finish = Instant.now();
        log.info(TimeLogger.getTimeUsed(start, finish, "Total time used: "));

    }

    public FileInfo getNumOfFiles(String path) throws Exception {
        FileInfo fileInfo = new FileInfo(path, arguments.getSource());
        return fileInfo;
    }

    public Catalog catalogInfoHandler(String catalogName) {
        // Setting ID info
        IdGenerator idGenerator = IdGenerator.getInstance();
        idGenerator.setPrefix(arguments.getQmlPrefix());
        idGenerator.setAuthorityID(arguments.getQmlAgency());
        idGenerator.setCatalogName(catalogName);

        // Prepare for ingestion
        final String catalogID = idGenerator.genEventParamsPublicID();

        Catalog catalog = null;
        try {
            catalog = catalogService.getCatalogByID(catalogID);
        } catch (Exception e) {

        }

        // Check if generated EventParameters ID exits in database and if not use the new and link to events
        if (catalog == null) {
            catalog = new Catalog();
            catalog.setPublicID(catalogID);
            catalog.setCatalogName(catalogName);
            catalog.setPrefix(arguments.getQmlPrefix());
            catalog.setAuthorityID(arguments.getQmlAgency());
            catalogService.addCatalog(catalog);
        }
        return catalog;
    }

    public List<IgnoredLineError> ingestNewOrUpdateFiles(Catalog catalog, IngestLog ingestLog, Map<String, String> filePaths) {
        List<IgnoredLineError> conversionErrors = new ArrayList<>(0);

        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        df.applyPattern("0.0");

        // Read and create objects
        for (Map.Entry<String, String> entry: filePaths.entrySet()) {
            try {

                byte[] sfileBytes = Files.readAllBytes(Paths.get(entry.getValue()));

                SfileCheck sfileCheck =
                        new SfileCheck(
                                entry.getKey(),
                                sfileBytes,
                                FileChecker.getChecksumString(entry.getValue())
                        );

                sfileCheckerService.addSfile(sfileCheck);

                EventOverview eventOverview = genQuakemlFromSfiles(entry.getValue());
                if (eventOverview != null) {
                    List<Event> events = eventOverview.getEvents();
                    List<IgnoredLineError> errors = eventOverview.getErrors();

                    if (events != null) {
                        for (Event e: events) {
                            String eventID = e.getEventID();

                            String time = null;
                            Double latitude = null;
                            Double longitude = null;
                            Double radius = null; // not active
                            Double depth = null;

                            String author = null;

                            String magType = null;
                            Double magnitude = null;
                            String magAuthor = null;

                            String eventLocation = null;
                            List<EventDescription> descriptions = e.getDescription();
                            if (descriptions != null) {
                                if (descriptions.size() > 0) {
                                    for (EventDescription ed: descriptions) {
                                        EventDescriptionType evType = ed.getType();
                                        if (evType != null) {
                                            switch (evType) {
                                                case NEAREST_CITIES:
                                                case FLINN_ENGDAHL_REGION:
                                                case REGION_NAME:
                                                    eventLocation = ed.getText();
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                }
                            }

                            EventType type = (e.getType() != null) ? e.getType() : null;

                            // Fill attributes from Origin entity
                            List<Origin> origins = e.getOrigin();
                            if (origins != null && origins.size() > 0) {
                                Origin origin = origins.get(0);
                                if (origin.getTime() != null) {
                                    time = origin.getTime().getValue();
                                }
                                if (origin.getLatitude() != null) {
                                    latitude = origin.getLatitude().getValue();
                                }
                                if (origin.getLongitude() != null) {
                                    longitude = origin.getLongitude().getValue();
                                }
                                if (origin.getDepth() != null) {
                                    if (origin.getDepth().getValue() != null) {
                                        depth = (origin.getDepth().getValue() / 1000); // In km
                                    }
                                }
                            }

                            // Fill attributes from Magnitude entity
                            List<Magnitude> magnitudes = e.getMagnitude();
                            if (magnitudes != null && magnitudes.size() > 0) {
                                Magnitude mag = magnitudes.get(0);
                                if (mag.getType() != null) {
                                    magType = mag.getType();
                                }
                                if (mag.getMag() != null) {
                                    magnitude = mag.getMag().getValue();
                                }
                                if (mag.getCreationInfo() != null) {
                                    if (mag.getCreationInfo().getAgencyID() != null) {
                                        magAuthor = mag.getCreationInfo().getAgencyID();
                                    }
                                }
                            }

                            SfileEvent sfileEvent = new SfileEvent();
                            sfileEvent.setEventID(eventID);
                            sfileEvent.setTime(time);
                            sfileEvent.setLatitude(latitude);
                            sfileEvent.setLongitude(longitude);
                            sfileEvent.setRadius(radius);
                            sfileEvent.setDepth(depth);
                            sfileEvent.setMagType(magType);
                            sfileEvent.setMagnitude(magnitude);
                            sfileEvent.setMagAuthor(magAuthor);
                            sfileEvent.setLocationName(eventLocation);
                            sfileEvent.setType(type);

                            ingestLog.increaseEvents(1);

                            sfileEvent.setCatalog(catalog);
                            sfileEvent.setSfile(sfileCheck);

                            if (e.getCreationInfo() != null) {
                                if (e.getCreationInfo().getAuthor() != null) {
                                    sfileEvent.setAuthor(e.getCreationInfo().getAuthor());
                                    sfileEvent.setContributor(e.getCreationInfo().getAgencyID());
                                }
                                if (e.getCreationInfo().getAuthorURI() != null) {
                                    sfileEvent.setContributorID(e.getCreationInfo().getAgencyURI());
                                }
                            }

                            sfileEventService.addOrUpdateEvent(sfileEvent);
                        }
                        ingestLog.increaseFiles(1);
                    }

                    if (errors != null && errors.size() > 0) {
                        conversionErrors.addAll(errors);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Filename: " + entry);
            }

        };

        return conversionErrors;

    }

    public void deleteFiles(Set<String> sfileIDs) {
        sfileIDs.forEach(id -> {
            sfileCheckerService.deleteSfile(id);
        });
    }

    private EventOverview genQuakemlFromSfiles(String path) throws Exception {
        InputStream stream = new FileInputStream(path);
        return converter.getQmlEventsFromNordic(stream, path);
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
    private QuakeML getQuakeml(String path) throws Exception {
        InputStream stream = new FileInputStream(path);
        String xml = inputStreamToString(stream);
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        mapper.registerModule(new JaxbAnnotationModule());
        mapper.registerModule(new Jdk8Module());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(xml, QuakeML.class);
    }

}
