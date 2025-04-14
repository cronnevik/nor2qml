package no.nnsn.ingestor.components;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import lombok.extern.slf4j.Slf4j;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.ConverterProfile;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.ingestor.dao.CatalogChange;
import no.nnsn.ingestor.dao.IngestorOptions;
import no.nnsn.ingestor.dao.SfileCheckInfo;
import no.nnsn.ingestor.service.CatalogService;
import no.nnsn.ingestor.service.SfileEventService;
import no.nnsn.ingestor.service.SfileCheckerService;
import no.nnsn.ingestor.utils.IngestLog;
import no.nnsn.ingestor.utils.TimeLogger;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.QuakeML;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Component
public class Ingestor {

    private final EntityManager entityManager;
    final CatalogService catalogService;
    final SfileEventService sfileEventService;
    final SfileCheckerService sfileCheckerService;
    final Converter converter;

    @Autowired
    public Ingestor(CatalogService catalogService, SfileEventService sfileEventService, SfileCheckerService sfileCheckerService, Converter converter, EntityManager entityManager) {
        this.catalogService = catalogService;
        this.sfileEventService = sfileEventService;
        this.sfileCheckerService = sfileCheckerService;
        this.converter = converter;
        this.entityManager = entityManager;
    }

    public void execute(FileInfo fileInfo, IngestorOptions options) throws Exception {

        System.out.println("Scanning catalog: " + fileInfo.getCatalogName());
        log.info("catalog: {}", fileInfo.getCatalogName());
        printFilecount(fileInfo);

        ingest(fileInfo, options);
    }

    public void printFilecount(FileInfo fileInfo) {
        log.info("Number of S-files found: " + fileInfo.getsFileCount());
    }


    @Transactional
    public void ingest(FileInfo fileInfo, IngestorOptions options) throws Exception {

        Instant start = Instant.now();

        final IngestLog ingestLog = new IngestLog();

        Set<Path> catalogFilePaths = fileInfo.getFilePaths();
        log.info("S-files identified within catalog: {}", catalogFilePaths.size());
        log.info("Other type of files within catalog: {}", fileInfo.getSkippedFiles().size());

        List<SfileCheckInfo> sfilesInDatabase = sfileEventService.getSfileListByCatalogName(options.getCatalogName());
        log.info("S-files within database: {}", sfilesInDatabase.size());

        System.out.println("Checking s-files in catalog against database for new, change of existing or deleted");
        CatalogChange catalogChange = FileChecker.check(sfilesInDatabase, fileInfo, options);

        Map<String, String> newFiles = catalogChange.getNewFiles();
        Map<String, String> modifiedFiles = catalogChange.getModifiedFiles();
        Set<String> deletedFiles = catalogChange.getDeletedFiles();

        log.info("New files: {}", newFiles.size());
        log.info("Modified files: {}", modifiedFiles.size());
        log.info("Deleted files: {}", deletedFiles.size());

        Instant fileCheckFinish = Instant.now();
        log.info(TimeLogger.getTimeUsed(start, fileCheckFinish, "File check time used: "));

        // Delete removed s-files
        if (!deletedFiles.isEmpty()) {
            System.out.println("Deleting files");
            deleteFiles(catalogChange.getDeletedFiles());
        }

        // Start reading files and ingest
        List<IgnoredLineError> convErrors = new ArrayList<>();
        Catalog catalog = catalogInfoHandler(options);

        if (!newFiles.isEmpty()) {
            System.out.println("Ingesting new files");
            ingestNewOrUpdateFiles(catalog, ingestLog, newFiles, options.getProfile());
        }

        if (!modifiedFiles.isEmpty()) {
            System.out.println("Updating modified files");
            ingestNewOrUpdateFiles(catalog, ingestLog, modifiedFiles, options.getProfile());
        }
/*
        if (convErrors != null && convErrors.size() > 0) {
            log.info("-------------------------------------");
            log.info("Errors found in file and the lines are ignored during conversion to QuakeML format:");
            convErrors.forEach(er -> {
                log.info(
                        "File: " + er.getFilename() +
                                " | Row: " + er.getRowNumber() +
                                " | Error: " + er.getMessage() +
                                " | LineText:" + er.getLine().getLineText()
                );
            });
            log.info("-------------------------------------");
        }
*/

        Instant finish = Instant.now();
        log.info(TimeLogger.getTimeUsed(start, finish, "All operations finished. Total time used: "));

        entityManager.clear();

    }

    public FileInfo getNumOfFiles(String path, String sourceType, List<String> ignoreFolders) throws Exception {
        return new FileInfo(path, sourceType, ignoreFolders);
    }

    public Catalog catalogInfoHandler(IngestorOptions options) {
        // Setting ID info
        IdGenerator idGenerator = IdGenerator.getInstance();
        idGenerator.setPrefix(options.getQmlPrefix());
        idGenerator.setAuthorityID(options.getQmlAgency());
        idGenerator.setCatalogName(options.getCatalogName());

        // Prepare for ingestion
        final String catalogID = idGenerator.genEventParamsPublicID();

        Catalog catalog = null;
        try {
            catalog = catalogService.getCatalogByID(catalogID);
        } catch (Exception e) {
            log.info("Could not get catalog by ID: {}", catalogID);
        }

        // Check if generated EventParameters ID exits in database and if not use the new and link to events
        if (catalog == null) {
            catalog = new Catalog();
            catalog.setPublicID(catalogID);
            catalog.setCatalogName(options.getCatalogName());
            catalog.setPrefix(options.getQmlPrefix());
            catalog.setAuthorityID(options.getQmlAgency());
            catalogService.addCatalog(catalog);
        }
        return catalog;
    }

    public void ingestNewOrUpdateFiles(Catalog catalog, IngestLog ingestLog, Map<String, String> filePaths, ConverterProfile profile) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        df.applyPattern("0.0");

        byte[] sfileBytes;
        SfileInformation sfileInformation;

        // Read and create objects
        for (Map.Entry<String, String> entry: filePaths.entrySet()) {
            try {

                sfileBytes = Files.readAllBytes(Paths.get(entry.getValue()));

                Path file = Paths.get(entry.getValue());
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);

                sfileInformation = new SfileInformation(
                        entry.getKey(),
                        sfileBytes,
                        LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneId.of("Europe/Paris")),
                        LocalDateTime.ofInstant(attr.lastModifiedTime().toInstant(), ZoneId.of("Europe/Paris"))
                );

                sfileCheckerService.addSfile(sfileInformation);

                EventOverview eventOverview = genQuakemlFromSfiles(entry.getValue(), profile);
                if (eventOverview != null) {
                    List<Event> events = eventOverview.getEvents();

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

                            String eventLocation = getEventLocation(e);

                            EventType type = (e.getType() != null) ? e.getType() : null;

                            // Fill attributes from Origin entity
                            List<Origin> origins = e.getOrigin();
                            if (origins != null && !origins.isEmpty()) {
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
                            if (magnitudes != null && !magnitudes.isEmpty()) {
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
                            sfileEvent.setSfile(sfileInformation);

                            if (e.getCreationInfo() != null) {
                                if (e.getCreationInfo().getAuthor() != null) {
                                    sfileEvent.setAuthor(e.getCreationInfo().getAuthor());
                                    sfileEvent.setContributor(e.getCreationInfo().getAgencyID());
                                }
                                if (e.getCreationInfo().getAuthorURI() != null) {
                                    sfileEvent.setContributorID(e.getCreationInfo().getAgencyURI());
                                }
                            }

                            try {
                                sfileEventService.addOrUpdateEvent(sfileEvent);
                            } catch (Exception exception) {
                                System.out.println(exception.getMessage());
                            }

                        }
                        ingestLog.increaseFiles(1);
                    }
                }

            } catch (Exception e) {
                log.error("Filename error: {}", entry);
            } finally {
                sfileBytes = null;
                sfileInformation = null;
            }

        };
    }

    private static String getEventLocation(Event e) {
        String eventLocation = null;
        List<EventDescription> descriptions = e.getDescription();
        if (descriptions != null) {
            if (!descriptions.isEmpty()) {
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
        return eventLocation;
    }

    public void deleteFiles(Set<String> sfileIDs) {
        sfileIDs.forEach(id -> {
            sfileCheckerService.deleteSfile(id);
        });
    }

    private EventOverview genQuakemlFromSfiles(String path, ConverterProfile profile) throws Exception {
        InputStream stream = new FileInputStream(path);
        return converter.getQmlEventsFromNordic(stream, path, profile);
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
