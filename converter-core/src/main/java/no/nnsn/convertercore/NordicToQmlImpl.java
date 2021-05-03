package no.nnsn.convertercore;

import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class NordicToQmlImpl implements NordicToQml {

    final QmlMapper mapper;

    @Autowired
    public NordicToQmlImpl(QmlMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Sfile> readSfile(InputStream is, String filename, CallerType caller) {
        SfileLineReader reader = new SfileLineReader();
        return reader.readAndParse(is, filename, caller);
    }

    @Override
    public EventOverview getEvents(List<Sfile> sFiles, String errorHandling, CallerType caller, ConverterProfile profile, String idSuffix) {

        List<Event> events = new ArrayList<>();
        int eventCount = 0;
        List<IgnoredLineError> errors = new ArrayList<>();

        String preferredOriginID = null;
        String preferredMagnitudeID = null;
        String preferredFocalMechanismID = null;

        sFileLoop:
        for (Sfile sfile: sFiles) {
            eventCount++;
            // Create new Event object to store Sfile data
            Event ev = new Event();

            SfileData data = sfile.getData();
            Line1 firstLine1 = (Line1) data.getLine1s().get(0);

            if (caller.equals(CallerType.STANDALONE)) {
                System.out.println("*** Event:" + firstLine1.getLineText());
            }

            // Lines fetched from JSON object through post
            List<Line1> l1s = data.getLine1s();
            List<Line3> l3s = data.getLine3s();
            List<Line> l4s = data.getLine4s();
            List<Line5> l5s = data.getLine5s();
            List<Line6> l6s = data.getLine6s();
            List<LineE> les = data.getLineEs();
            List<LineF> lfs = data.getLineFs();
            List<LineH> lhs = data.getLineHs(); // currently no mapping for LineH - used for creationInfo in Event Object
            List<LineI> lis = data.getLineIs();
            List<LineM1> lm1s = data.getLineM1s();
            List<LineM2> lm2s = data.getLineM2s();
            List<LineS> lSs = data.getLineSs();

            // Event object's properties to be added
            List<Arrival> arrivals = new ArrayList<>();
            List<Amplitude> amplitudes = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();
            List<Magnitude> magnitudes = new ArrayList<>();
            List<Origin> origins = new ArrayList<>();
            List<Pick> picks = new ArrayList<>();
            List<FocalMechanism> focalMechanisms = new ArrayList<>();
            List<EventDescription> descriptions = new ArrayList<>();

            // loop through each Line1 for multiple mappings
            line1Loop:
            for (int i = 0; i < l1s.size() ; i++) {
                Line1 line1 = l1s.get(i);

                // Map Origin entity
                Origin org = null;
                try {
                    Object orgObj = mapper.mapOrigin(line1, les, errorHandling);
                    if (orgObj instanceof Origin) {
                        org = (Origin) orgObj;
                        origins.add(org);
                        // Set preferred origin to the first line (type 1)
                        if (i == 0) {
                            preferredOriginID = org.getPublicID();
                        }
                    } else if (orgObj instanceof IgnoredLineError) {
                        IgnoredLineError error = (IgnoredLineError) orgObj;
                        error.setEventNumber(eventCount);
                        error.setFilename(sfile.getFilename());
                        if (i == 0) { // Only remove Event if it is the first Line1
                            error.setEventRemoved(true); // Removing Event
                            errors.add(error);
                            continue sFileLoop;
                        } else {
                            errors.add(error);
                            continue line1Loop;
                        }
                    }
                } catch (Exception ex) {
                    throw new CustomException(
                            "Error in mapping to Origin entity. "
                            + "Time: " + line1.getYear() + "-" + line1.getMonth() + "-" + line1.getDay()
                            + "_" + line1.getHour() + ":" + line1.getMinutes() + ":" + line1.getSeconds() + "-"
                            + ex.getMessage()
                    );
                }

                // Map Magnitude entity
                List<Object> magObjs = mapper.mapLine1Magnitudes(line1, org);
                magObjs.forEach(o -> {
                    if (o instanceof Magnitude) {
                        Magnitude m = (Magnitude) o;
                        magnitudes.add(m);
                    } else if (o instanceof IgnoredLineError) {
                        IgnoredLineError e = (IgnoredLineError) o;
                        e.setFilename(sfile.getFilename());
                        errors.add(e);
                    }
                });

                // Map FocalMechanism
                if (lfs != null) {
                    LineFLoop:
                    for (LineF lineF: lfs) {
                        Line1 l1Comparable = lineF.getRelatedLine1();
                        if (l1Comparable != null) {
                            // Check if FocalMechanism is related to current line1
                            if (line1.equals(l1Comparable)) {
                                FocalMechanism focalMechanism;
                                Object fObj = mapper.mapLine1FocalMechanisms(lineF, org);
                                if (fObj instanceof FocalMechanism) {
                                    focalMechanism = (FocalMechanism) fObj;
                                    // Check if the focalmechanism has a related moment tensor
                                    if (lm2s != null && lm2s.size() > 0) {
                                        LineM2 lineM2 = null;

                                        LineM2Loop:
                                        for (LineM2 m2: lm2s) {
                                            LineF lFComparable = m2.getRelatedLineF();
                                            if (lFComparable != null && lineF.equals(lFComparable)) {
                                                // MomentTensor
                                                MomentTensor momentTensor;
                                                Object mtObj = mapper.mapMomentTensor(m2, line1, org);
                                                if (mtObj instanceof MomentTensor) {
                                                    momentTensor = (MomentTensor) mtObj;
                                                    LineM1 relatedM1 = m2.getRelatedLineM1();
                                                    if (relatedM1 != null) {
                                                        // MomentTensor Origin
                                                        Origin originM1;
                                                        Object oM1Obj = mapper.mapMomentTensorOrigin(relatedM1);
                                                        if (oM1Obj instanceof Origin) {
                                                            originM1 = (Origin) oM1Obj;
                                                            momentTensor.setDerivedOriginID(originM1.getPublicID());
                                                            origins.add(originM1);

                                                            // MomentTensor Magnitude
                                                            Magnitude magM1;
                                                            Object mM1Obj = mapper.mapMomentTensorMagnitude(relatedM1);
                                                            if (mM1Obj instanceof Magnitude) {
                                                                magM1 = (Magnitude) mM1Obj;
                                                                magM1.setOriginID(originM1.getPublicID());
                                                                magnitudes.add(magM1);
                                                            } else if (mM1Obj instanceof IgnoredLineError) {
                                                                IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                                e.setFilename(sfile.getFilename());
                                                                errors.add(e);
                                                            }
                                                        } else if (oM1Obj instanceof IgnoredLineError) {
                                                            IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                            e.setFilename(sfile.getFilename());
                                                            errors.add(e);
                                                        }
                                                    }
                                                    focalMechanism.setMomentTensor(momentTensor);
                                                } else if (mtObj instanceof IgnoredLineError) {
                                                    IgnoredLineError e = (IgnoredLineError) mtObj;
                                                    e.setFilename(sfile.getFilename());
                                                    errors.add(e);
                                                }
                                            }
                                        }
                                    }
                                    focalMechanisms.add(focalMechanism);
                                } else if (fObj instanceof IgnoredLineError) {
                                    IgnoredLineError e = (IgnoredLineError) fObj;
                                    e.setFilename(sfile.getFilename());
                                    errors.add(e);
                                }
                            }
                        }
                    }
                }
            }

            if (l3s != null) {
                // loop through each line 3s for multiple mappings
                line3Loop:
                for (Line3 line3: l3s) {
                    // Check if locality exists in line 3
                    String localityID = "LOCALITY:";
                    if (line3.getCommentText() != null) {
                        Boolean containsLocality = line3.getCommentText().contains(localityID);
                        if (containsLocality) {
                            String commentLine = line3.getCommentText();
                            int startOfText = commentLine.indexOf(":");
                            String locName = commentLine.substring(startOfText +1).trim();

                            if (!locName.isEmpty()) {
                                descriptions.add(new EventDescription(
                                        locName,
                                        EventDescriptionType.REGION_NAME
                                ));

                                continue line3Loop;
                            }
                        }
                    }

                    // Each line3 as comment object in QuakeML
                    try {
                        comments.add(mapper.mapL3Comment(line3));
                    } catch (Exception ex) {
                        if (errorHandling.equals("ignore")) {
                            IgnoredLineError e = new IgnoredLineError(eventCount, ex.getMessage());
                            e.setLine(line3);
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                            continue line3Loop;
                        } else {
                            throw new CustomException(
                                "Error found in s-file number: " + eventCount + ". "
                                        + ex.getMessage()
                            );
                        }
                    }

                }
            }

            if (l4s != null) {
                line4Loop:
                for (Line line: l4s) {
                    // Map Pick Entity and link its ID to Arrival and Amplitude entities
                    Pick pick = null;
                    Object pObj = null;
                    if (line instanceof Line4) {
                        Line4 l4 = (Line4) line;
                        pObj = mapper.mapPick(l4, null, l1s);
                    } else if (line instanceof Line4Dto) {
                        Line4Dto l4 = (Line4Dto) line;
                        pObj = mapper.mapPick(null, l4, l1s);
                    }

                    if (pObj instanceof Pick) {
                        pick = (Pick) pObj;
                        picks.add(pick);
                    } else if (pObj instanceof IgnoredLineError) {
                        IgnoredLineError e = (IgnoredLineError) pObj;
                        e.setFilename(sfile.getFilename());
                        errors.add(e);
                        continue line4Loop;
                    }

                    // Map Arrival Entity
                    Arrival arrival;
                    Object arrObj = null;
                    if (line instanceof Line4) {
                        Line4 l4 = (Line4) line;
                        arrObj = mapper.mapArrival(l4, null, pick, l1s);
                    } else if (line instanceof Line4Dto) {
                        Line4Dto l4 = (Line4Dto) line;
                        arrObj = mapper.mapArrival(null, l4, pick, l1s);
                    }

                    if (arrObj instanceof Arrival) {
                        arrival = (Arrival) arrObj;
                        arrivals.add(arrival);
                    }  else if (arrObj instanceof IgnoredLineError) {
                        IgnoredLineError e = (IgnoredLineError) arrObj;
                        e.setFilename(sfile.getFilename());
                        errors.add(e);
                    }

                    Amplitude amplitude;
                    Object ampObj = null;
                    // Map Amplitude Entity - Only map if amplitude value exists
                    if (line instanceof Line4) {
                        Line4 l4 = (Line4) line;
                        if (l4.getAmplitude() != null) {
                            ampObj = mapper.mapAmplitude(l4, null, pick, l1s);
                        }
                    } else if (line instanceof Line4Dto) {
                        Line4Dto l4 = (Line4Dto) line;
                        String parameterOne = l4.getParameterOne();
                        String phaseID = l4.getPhaseID();
                        ParameterOneType parameterOneType = PhaseParameters.identifyParameterOneType(parameterOne, phaseID);
                        if (parameterOneType == ParameterOneType.AMPLITUDE) {
                            ampObj = mapper.mapAmplitude(null, l4, pick, l1s);
                        }
                    }

                    if (ampObj != null) {
                        if (ampObj instanceof Amplitude) {
                            amplitude = (Amplitude) ampObj;
                            amplitudes.add(amplitude);
                        }  else if (ampObj instanceof IgnoredLineError) {
                            IgnoredLineError e = (IgnoredLineError) ampObj;
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                        }
                    }
                }
            }

            if (l5s != null) {
                line5Loop:
                for (Line5 line5: l5s) {
                    try {
                        comments.add(mapper.mapL5Comment(line5));
                    } catch (Exception ex) {
                        if (errorHandling.equals("ignore")) {
                            IgnoredLineError e = new IgnoredLineError(eventCount, ex.getMessage());
                            e.setLine(line5);
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                            continue line5Loop;
                        } else {
                            throw new CustomException(
                                    "Error found in s-file number: " + eventCount + ". "
                                            + ex.getMessage()
                            );
                        }
                    }
                }
            }

            if (l6s != null) {
                line6Loop:
                for (Line6 line6: l6s) {
                    try {
                        comments.add(mapper.mapL6Comment(line6));
                    } catch (Exception ex) {
                        if (errorHandling.equals("ignore")) {
                            IgnoredLineError e = new IgnoredLineError(eventCount, ex.getMessage());
                            e.setLine(line6);
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                            continue line6Loop;
                        } else {
                            throw new CustomException(
                                    "Error found in s-file number: " + eventCount + ". "
                                            + ex.getMessage()
                            );
                        }
                    }
                }
            }

            if (lis != null) {
                lineILoop:
                for (LineI lineI: lis) {
                    try {
                        comments.add(mapper.mapLIComment(lineI));
                    } catch (Exception ex) {
                        if (errorHandling.equals("ignore")) {
                            IgnoredLineError e = new IgnoredLineError(eventCount, ex.getMessage());
                            e.setLine(lineI);
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                            continue lineILoop;
                        } else {
                            throw new CustomException(
                                    "Error found in s-file number: " + eventCount + ". "
                                            + ex.getMessage()
                            );
                        }
                    }
                }
            }

            if (lSs != null) {
                lineSLoop:
                for (LineS lineS: lSs) {
                    try {
                        comments.add(mapper.mapLSComment(lineS));
                    } catch (Exception ex) {
                        if (errorHandling.equals("ignore")) {
                            IgnoredLineError e = new IgnoredLineError(eventCount, ex.getMessage());
                            e.setLine(lineS);
                            e.setFilename(sfile.getFilename());
                            errors.add(e);
                            continue lineSLoop;
                        } else {
                            throw new CustomException(
                                    "Error found in s-file number: " + eventCount + ". "
                                            + ex.getMessage()
                            );
                        }
                    }
                }
            }


            // Attach Arrival objects to the first Origin Entity
            // Set preferredOriginID
            if (origins.size() > 0) {

                CreationInfo eventCreationInfo = new CreationInfo();

                if (origins.get(0) != null) {
                    // First origin should be preferred
                    Origin org = origins.get(0);
                    org.setArrival(arrivals);

                    ev.setPreferredOriginID(org.getPublicID());
                    DecimalFormat df =  (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
                    df.applyPattern("00");

                    String sYear = "";
                    if (origins.get(0).getCompositeTime().get(0).getYear() != null) {
                        sYear = origins.get(0).getCompositeTime().get(0).getYear().getValue().toString();
                    } else {
                        System.out.println("Year not existing for creating ID");
                        continue sFileLoop;
                    }
                    String sMonth = "";
                    if (origins.get(0).getCompositeTime().get(0).getMonth() != null) {
                        sMonth = df.format(origins.get(0).getCompositeTime().get(0).getMonth().getValue());
                    } else {
                        System.out.println("Month not existing for creating ID");
                        continue sFileLoop;
                    }
                    String sDay = "";
                    if (origins.get(0).getCompositeTime().get(0).getDay() != null) {
                        sDay = df.format(origins.get(0).getCompositeTime().get(0).getDay().getValue());
                    } else {
                        System.out.println("Day not existing for creating ID");
                        continue sFileLoop;
                    }
                    String sHour = "";
                    if (origins.get(0).getCompositeTime().get(0).getHour() != null) {
                        sHour = origins.get(0).getCompositeTime().get(0).getHour().getValue().toString();
                    } else {
                        sHour = "0";
                    }
                    String sMinutes = "";
                    if (origins.get(0).getCompositeTime().get(0).getMinute() != null) {
                        sMinutes = origins.get(0).getCompositeTime().get(0).getMinute().getValue().toString();
                    } else {
                        sMinutes = "0.0";
                    }

                    String eventID = "";
                    if (caller.equals(CallerType.INGESTOR)) {
                        eventID = sYear + sMonth + sDay + "_" + idSuffix;
                    } else {
                        eventID= IdGenerator.getInstance().genRandomEventID(
                                sYear, sMonth, sDay, sHour, sMinutes
                        );
                    }

                    ev.setEventID(eventID);
                    ev.setPublicID(IdGenerator.getInstance().genEventPublicID(eventID, Event.class));

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
                    ev.setCreationInfo(eventCreationInfo);
                }
            }

            ev.setOrigin(origins);
            ev.setMagnitude(magnitudes);
            ev.setAmplitude(amplitudes);
            ev.setComment(comments);
            ev.setPick(picks);
            ev.setFocalMechanism(focalMechanisms);
            if(descriptions.size() > 0) {
                ev.setDescription(descriptions);
            } else {
                ev.setDescription(null);
            }

            // Determine Preferred FocalMechanism ID
            if (focalMechanisms != null && focalMechanisms.size() > 0) {
                // First one listed should be the preferred
                FocalMechanism firstFocalMechListed = focalMechanisms.get(0);
                preferredFocalMechanismID = firstFocalMechListed.getPublicID();
            }
            // Determine Preferred Magnitude ID
            if (magnitudes != null && magnitudes.size() > 0) {
                // First should be preferred
                Magnitude firstMagnitude = magnitudes.get(0);
                preferredMagnitudeID = firstMagnitude.getPublicID();
            }

            // Set preferred IDs
            if (preferredOriginID != null) {
                ev.setPreferredOriginID(preferredOriginID);
            }
            if (preferredMagnitudeID != null) {
                ev.setPreferredMagnitudeID(preferredMagnitudeID);
            }
            if (preferredFocalMechanismID != null) {
                ev.setPreferredFocalMechanismID(preferredFocalMechanismID);
            }

            // Set EventType and EventTypeCertainty
            EventTypeSetter.setEventTypeAndUncertainty(ev, l1s.get(0));

            // Set CreationInfo based on profile
            if (profile == ConverterProfile.INTAROS) {
                CreationInfo creationInfo = new CreationInfo();
                String agency;
                String author = null;

                if (l1s.size() > 1) {
                    String hypoCenterRepAgency = l1s.get(1).getHypoCenterRepAgency();
                    if (!StringUtils.isBlank(hypoCenterRepAgency)) {
                        author = l1s.get(1).getHypoCenterRepAgency();
                    }
                }

                if (lhs != null && lhs.size() > 0) {
                    agency = "INT";
                } else {
                    agency = author;
                }
                creationInfo.setAgencyID(agency);
                creationInfo.setAuthor(author);
                ev.setCreationInfo(creationInfo);
            }


            events.add(ev);
        }

        return new EventOverview(events, errors);
    }
}
