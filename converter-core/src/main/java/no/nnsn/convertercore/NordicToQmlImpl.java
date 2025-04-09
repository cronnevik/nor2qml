package no.nnsn.convertercore;

import lombok.extern.slf4j.Slf4j;
import no.nnsn.convertercore.converters.*;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.exeption.FileReaderException;
import no.nnsn.convertercore.exeption.LineConverterException;
import no.nnsn.convertercore.exeption.LineFetcherException;
import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.helpers.collections.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
public class NordicToQmlImpl implements NordicToQml {

    final QmlMapper mapper;

    @Autowired
    public NordicToQmlImpl(QmlMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Sfile> readSfile(InputStream is, String filename, CallerType caller) throws FileReaderException {
        SfileLineReader reader = new SfileLineReader();
        return reader.readAndParse(is, filename, caller);
    }

    @Override
    public EventOverview convertToQuakeml(List<Sfile> sFiles, ConverterOptions options) throws Exception {

        int eventCount = 0;
        List<Event> events = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();
        List<Sfile> ignoredSfiles = new ArrayList<>();

        boolean isStandaloneApplication = options.getCaller().equals(CallerType.STANDALONE);
        boolean isIngestorApplication = options.getCaller().equals(CallerType.INGESTOR);

        for (Sfile sfile : sFiles) {
            eventCount++;
            SfileInfo sfileInfo = new SfileInfo(eventCount, sfile.getFilename(), options.getErrorHandling());

            Event ev = new Event();
            SfileData data = sfile.getData();

            if (isStandaloneApplication) {
                printFirstLine1ForStandaloneConverter(data.getLine1s().get(0));
            }

            List<Line1> l1s = new ArrayList<>();
            List<Line3> l3s = new ArrayList<>();
            List<Line> l4s = new ArrayList<>();
            List<Line5> l5s = new ArrayList<>();
            List<Line6> l6s = new ArrayList<>();
            List<LineE> les = new ArrayList<>();
            List<LineF> lfs = new ArrayList<>();
            List<LineH> lhs = new ArrayList<>();
            List<LineI> lis = new ArrayList<>();
            List<LineM1> lm1s = new ArrayList<>();
            List<LineM2> lm2s = new ArrayList<>();
            List<LineS> lSs = new ArrayList<>();

            try {
                l1s = data.getLine1s();
                l3s = data.getLine3s();
                l4s = data.getLine4s();
                l5s = data.getLine5s();
                l6s = data.getLine6s();
                les = data.getLineEs();
                lfs = data.getLineFs();
                lhs = data.getLineHs(); // currently no mapping for LineH - used for creationInfo in Event Object
                lis = data.getLineIs();
                lm1s = data.getLineM1s();
                lm2s = data.getLineM2s();
                lSs = data.getLineSs();
            } catch (Exception e) {
                if (isStandaloneApplication) {
                    System.out.println("error in getting data lines from sfile");
                } else if (isIngestorApplication){
                    log.error("error in getting data lines from sfile");
                } else {
                    throw new LineFetcherException("error in getting data lines from sfile");
                }
            }

            Line1QuakemlEntities line1Entities = null;
            try {
                line1Entities = new Line1Converter(l1s, les, sfileInfo).convert(mapper);
                if (line1Entities.hasErrorInFirstLine1()) {
                    log.warn("Sfile {} skipped due to error in first Line 1", sfile.getFilename());
                    line1Entities.getErrors().forEach(er -> {
                        if (isStandaloneApplication) {
                            System.out.println(
                                    "File skipped due to error in first Line 1: "
                                            + er.getFilename()
                                            + ", Error message: " + er.getMessage()
                            );
                        } else {
                            log.error("Line 1 error: {}", er.getMessage());
                        }
                    });
                    continue; // skip event
                }
            } catch (Exception e) {
                if (isStandaloneApplication) {
                    System.out.println("Error in converting line 1");
                } else if (isIngestorApplication) {
                    log.error("Error in converting line 1");
                } else {
                    throw new LineConverterException("Error in converting line 1");
                }
            }


            LineFQuakemlEntities lineFEntities = new LineFConverter(l1s, lfs, lm2s, sfileInfo).convert(mapper);
            Line3QuakemlEntities line3Entities = new Line3Converter(l3s, sfileInfo).convert(mapper);
            Line4QuakemlEntities line4Entities = new Line4Converter(l1s, l4s, sfileInfo).convert(mapper);
            Line5QuakemlEntities line5Entities = new Line5Converter(l5s, sfileInfo).convert(mapper);
            Line6QuakemlEntities line6Entities = new Line6Converter(l6s, sfileInfo).convert(mapper);
            LineIQuakemlEntities lineIEntities = new LineIConverter(lis, sfileInfo).convert(mapper);
            LineSQuakemlEntities lineSEntities = new LineSConverter(lSs, sfileInfo).convert(mapper);

            // Event object's properties to be added
            List<Arrival> arrivals = null;
            List<Amplitude> amplitudes = null;
            List<Comment> comments = null;
            List<Magnitude> magnitudes = null;
            List<Origin> origins = null;
            List<Pick> picks = null;
            List<FocalMechanism> focalMechanisms = null;
            List<EventDescription> descriptions = null;
            try {
                if (line4Entities != null) arrivals = line4Entities.getArrivals();
                if (line4Entities != null) amplitudes = line4Entities.getAmplitudes();
                if (line1Entities != null) magnitudes = line1Entities.getMagnitudes();
                if (line1Entities != null) origins = line1Entities.getOrigins();
                if (line4Entities != null) picks = line4Entities.getPicks();
                if (lineFEntities != null) focalMechanisms = lineFEntities.getFocalMechanisms();
                if (line3Entities != null) descriptions = line3Entities.getDescriptionList();
            } catch (Exception e) {
                System.out.println("Error in attaching line entities");
                // e.printStackTrace();
            }

            // Concatenate MomentTensor origins and magnitudes
            try {
                if (lineFEntities != null && !CollectionUtils.isEmpty(lineFEntities.getLm1Origins())) {
                    if (origins != null) {
                        origins.addAll(lineFEntities.getLm1Origins());
                    }
                }
                if (lineFEntities != null && !CollectionUtils.isEmpty(lineFEntities.getLm1Magnitudes())) {
                    if (magnitudes != null) {
                        magnitudes.addAll(lineFEntities.getLm1Magnitudes());
                    }
                }
            } catch (Exception e) {
                System.out.println("Problem in concatenating MomentTensor orgins and magnitudes");
                //e.printStackTrace();
            }

            // Concatenate Comments from multiple lines
            comments = new ArrayList<>();
            concatenateCommentsFromLineEntities(line3Entities.getCommentList(), comments);
            concatenateCommentsFromLineEntities(line5Entities.getCommentList(), comments);
            concatenateCommentsFromLineEntities(line6Entities.getCommentList(), comments);
            concatenateCommentsFromLineEntities(lineIEntities.getCommentList(), comments);
            concatenateCommentsFromLineEntities(lineSEntities.getCommentList(), comments);

            // Concatenate Errors from various conversions
            concatenateErrorsFromLineEntities(line1Entities.getErrors(), errors);
            concatenateErrorsFromLineEntities(lineFEntities.getErrors(), errors);
            concatenateErrorsFromLineEntities(line3Entities.getErrors(), errors);
            concatenateErrorsFromLineEntities(line4Entities.getErrors(), errors);
            concatenateErrorsFromLineEntities(line5Entities.getErrors(), errors);
            concatenateErrorsFromLineEntities(line6Entities.getErrors(), errors);
            concatenateErrorsFromLineEntities(lineIEntities.getErrors(), errors);
            concatenateErrorsFromLineEntities(lineSEntities.getErrors(), errors);

            // Attach Arrival objects to the first Origin Entity
            Origin org = origins.get(0);
            org.setArrival(arrivals);

            // Set linked entities
            ev.setOrigin(origins);
            ev.setMagnitude(magnitudes);
            ev.setAmplitude(amplitudes);
            ev.setComment(comments);
            ev.setPick(picks);
            ev.setFocalMechanism(focalMechanisms);
            ev.setDescription(descriptions);

            // Set event properties fetched from converted origin object
            Boolean hasSetEventPropertiesFromOrigin = setEventPropertiesFromOrigin(ev, origins, options);
            if (!hasSetEventPropertiesFromOrigin) {
                // missing creation of event id --> invalid event and thus skipped
                ignoredSfiles.add(sfile);
                continue;
            }

            // Set preferred IDs
            if (line1Entities.getPreferredMagnitudeID() != null) {
                ev.setPreferredMagnitudeID(line1Entities.getPreferredMagnitudeID());
            }
            if (lineFEntities.getPreferredFocalMechanismID() != null) {
                ev.setPreferredFocalMechanismID(lineFEntities.getPreferredFocalMechanismID());
            }

            // Set EventType and EventTypeCertainty
            EventTypeSetter.setEventTypeAndUncertainty(ev, l1s.get(0), options.getDefaultEventType(), options.getDefaultEventCertainty());

            // Set CreationInfo based on profile
            if (options.getProfile() == ConverterProfile.INTAROS) {
                setCustomCreationInfoForIntaros(ev, l1s, lhs);
            }

            events.add(ev);
        }

        return new EventOverview(events, errors, ignoredSfiles);
    }

    private void printFirstLine1ForStandaloneConverter(Object line1) {
        Line1 firstLine1 = (Line1) line1;
        System.out.println("*** Event:" + firstLine1.getLineText());
    }


    private void concatenateCommentsFromLineEntities(List<Comment> entitiesComments, List<Comment> comments) {
        try {
            if (entitiesComments != null && !CollectionUtils.isEmpty(entitiesComments)) {
                comments.addAll(entitiesComments);
            }
        } catch (Exception e) {
            System.out.println("Problem in concatenating comments");
        }
    }

    private void concatenateErrorsFromLineEntities(List<IgnoredLineError> entitiesErrors, List<IgnoredLineError> errors) {
        try {
            if (entitiesErrors != null && !CollectionUtils.isEmpty(entitiesErrors)) {
                errors.addAll(entitiesErrors);
            }
        } catch (Exception e) {
            System.out.println("Could not concatenating errors");
        }
    }

    private Boolean setEventPropertiesFromOrigin(Event event, List<Origin> origins, ConverterOptions options) {
        try {
            if (!origins.isEmpty()) {
                CreationInfo eventCreationInfo = new CreationInfo();
                if (origins.get(0) != null) {

                    // Set preferredOriginID - First origin should be preferred
                    Origin org = origins.get(0);
                    event.setPreferredOriginID(org.getPublicID());

                    // Generate eventID
                    String eventID = produceEventID(origins.get(0), options);
                    if (eventID.isEmpty()) {
                        return false;
                    }

                    event.setEventID(eventID);
                    event.setPublicID(IdGenerator.getInstance().genEventPublicID(eventID, Event.class));

                    // Creation Info for Event - agency is same as for main origin
                    if (org.getCreationInfo() != null) {
                        if (org.getCreationInfo().getAgencyID() != null) {
                            String agencyID = org.getCreationInfo().getAgencyID();
                            eventCreationInfo.setAgencyID(agencyID);
                        }
                    }
                }

                // Author or contributor given in 2nd line type-1
                if (origins.size() > 1 && origins.get(1) != null) {
                    Origin org = origins.get(1);
                    if (org.getCreationInfo() != null) {
                        if (org.getCreationInfo().getAgencyID() != null) {
                            String agencyID = org.getCreationInfo().getAgencyID();
                            eventCreationInfo.setAuthor(agencyID);
                        }
                    }
                } else { // Set the author the same as agencyID of first origin
                    if (eventCreationInfo.getAgencyID() != null) {
                        String agencyID = eventCreationInfo.getAgencyID();
                        eventCreationInfo.setAuthor(agencyID);
                    }
                }

                if (eventCreationInfo.getAgencyID() != null || eventCreationInfo.getAuthor() != null) {
                    event.setCreationInfo(eventCreationInfo);
                }
            }
            return true;
        } catch (Exception e) {
            System.out.println("Could not set event properties from Origin entity: " + e.getMessage());
            return false;
        }
    }

    private String produceEventID(Origin origin, ConverterOptions options) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("00");

        String sYear = "";
        if (origin.getCompositeTime().get(0).getYear() != null) {
            sYear = origin.getCompositeTime().get(0).getYear().getValue().toString();
        } else {
            System.out.println("Year not existing for creating ID. Event excluded");
            return "";
        }
        String sMonth = "";
        if (origin.getCompositeTime().get(0).getMonth() != null) {
            sMonth = df.format(origin.getCompositeTime().get(0).getMonth().getValue());
        } else {
            System.out.println("Month not existing for creating ID. Event excluded");
            return "";
        }
        String sDay = "";
        if (origin.getCompositeTime().get(0).getDay() != null) {
            sDay = df.format(origin.getCompositeTime().get(0).getDay().getValue());
        } else {
            System.out.println("Day not existing for creating ID. Event excluded");
            return "";
        }
        String sHour = "";
        if (origin.getCompositeTime().get(0).getHour() != null) {
            sHour = origin.getCompositeTime().get(0).getHour().getValue().toString();
        } else {
            sHour = "0";
        }
        String sMinutes = "";
        if (origin.getCompositeTime().get(0).getMinute() != null) {
            sMinutes = origin.getCompositeTime().get(0).getMinute().getValue().toString();
        } else {
            sMinutes = "0.0";
        }

        String eventID = "";
        if (options.getCaller().equals(CallerType.INGESTOR) || options.getCaller().equals(CallerType.WEBSERVICE)) {
            String[] split = options.getIdSuffix().split("\\."); // First part of filename string
            String suffix = split[0];
            String prefix = split[1];
            eventID = prefix.substring(1) + "_" + suffix;
        } else {
            eventID = IdGenerator.getInstance().genRandomEventID(
                    sYear, sMonth, sDay, sHour, sMinutes
            );
        }
        return eventID;
    }

    private void setCustomCreationInfoForIntaros(Event event, List<Line1> l1s, List<LineH> lhs) {
        CreationInfo creationInfo = new CreationInfo();
        String agency;
        String author = null;

        if (l1s.size() > 1) {
            String hypoCenterRepAgency = l1s.get(1).getHypoCenterRepAgency();
            if (!StringUtils.isBlank(hypoCenterRepAgency)) {
                author = l1s.get(1).getHypoCenterRepAgency();
            }
        }

        if (lhs != null && !lhs.isEmpty()) {
            agency = "INT";
        } else {
            agency = author;
        }
        creationInfo.setAgencyID(agency);
        creationInfo.setAuthor(author);
        event.setCreationInfo(creationInfo);
    }

}
