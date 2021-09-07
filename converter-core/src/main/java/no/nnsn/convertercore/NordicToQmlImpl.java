package no.nnsn.convertercore;

import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.helpers.collections.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
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
import org.springframework.util.CollectionUtils;

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
    public EventOverview getEvents(List<Sfile> sFiles, ConverterOptions options) {

        List<Event> events = new ArrayList<>();
        int eventCount = 0;
        List<IgnoredLineError> errors = new ArrayList<>();
        List<Sfile> ignoredSfiles = new ArrayList<>();

        sFileLoop:
        for (Sfile sfile: sFiles) {
            eventCount++;
            SfileInfo sfileInfo = new SfileInfo(eventCount, sfile.getFilename(), options.getErrorHandling());

            // Create new Event object to store Sfile data
            Event ev = new Event();

            SfileData data = sfile.getData();
            Line1 firstLine1 = (Line1) data.getLine1s().get(0);

            if (options.getCaller().equals(CallerType.STANDALONE)) {
                System.out.println("*** Event:" + firstLine1.getLineText());
            }

            // Lines fetched from Sfile object
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

            Line1EntityCollection line1Entities = convertLine1(l1s, les, sfileInfo);
            if (line1Entities == null) continue sFileLoop; // skip event if null
            LineFEntityCollection lineFEntities = convertLineF(l1s, lfs, lm2s, sfileInfo);
            Line3EntityCollection line3Entities = convertLine3(l3s, sfileInfo);
            Line4EntityCollection line4Entities = convertLine4(l1s, l4s, sfileInfo);
            Line5EntityCollection line5Entities = convertLine5(l5s, sfileInfo);
            Line6EntityCollection line6Entities = convertLine6(l6s, sfileInfo);
            LineIEntityCollection lineIEntities = convertLineI(lis, sfileInfo);
            LineSEntityCollection lineSEntities = convertLineS(lSs, sfileInfo);

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
                System.out.println("Error in attaching entities");
                e.printStackTrace();
            }

            // Concatenate MomentTensor origins and magnitudes
            try {
                if (lineFEntities != null && !CollectionUtils.isEmpty(lineFEntities.getLm1Origins())) {
                    origins.addAll(lineFEntities.getLm1Origins());
                }
                if (lineFEntities != null && !CollectionUtils.isEmpty(lineFEntities.getLm1Magnitudes())) {
                    magnitudes.addAll(lineFEntities.getLm1Magnitudes());
                }
            } catch (Exception e) {
                System.out.println("Problem in concatenating MomentTensor orgins and magnitudes");
                e.printStackTrace();
            }

            // Concatenate Comments from multiple lines
            try {
                comments = new ArrayList<>();
                if (line3Entities != null && !CollectionUtils.isEmpty(line3Entities.getCommentList())) {
                    comments.addAll(line3Entities.getCommentList());
                }
                if (line5Entities != null && !CollectionUtils.isEmpty(line5Entities.getCommentList())) {
                    comments.addAll(line5Entities.getCommentList());
                }
                if (line6Entities != null && !CollectionUtils.isEmpty(line6Entities.getCommentList())) {
                    comments.addAll(line6Entities.getCommentList());
                }
                if (lineIEntities != null && !CollectionUtils.isEmpty(lineIEntities.getCommentList())) {
                    comments.addAll(lineIEntities.getCommentList());
                }
                if (lineSEntities != null && !CollectionUtils.isEmpty(lineSEntities.getCommentList())) {
                    comments.addAll(lineSEntities.getCommentList());
                }
            } catch (Exception e) {
                System.out.println("Problem in concatenating comments");
                e.printStackTrace();
            }

            // Concatenate Errors from various conversions
            try {
                if (line1Entities != null && !CollectionUtils.isEmpty(line1Entities.getErrors())) {
                    errors.addAll(line1Entities.getErrors());
                }
                if (lineFEntities != null && !CollectionUtils.isEmpty(lineFEntities.getErrors())) {
                    errors.addAll(lineFEntities.getErrors());
                }
                if (line3Entities != null && !CollectionUtils.isEmpty(line3Entities.getErrors())) {
                    errors.addAll(line3Entities.getErrors());
                }
                if (line4Entities != null && !CollectionUtils.isEmpty(line4Entities.getErrors())) {
                    errors.addAll(line4Entities.getErrors());
                }
                if (line5Entities != null && !CollectionUtils.isEmpty(line5Entities.getErrors())) {
                    errors.addAll(line5Entities.getErrors());
                }
                if (line6Entities != null && !CollectionUtils.isEmpty(line6Entities.getErrors())) {
                    errors.addAll(line6Entities.getErrors());
                }
                if (lineIEntities != null && !CollectionUtils.isEmpty(lineIEntities.getErrors())) {
                    errors.addAll(lineIEntities.getErrors());
                }
                if (lineSEntities != null && !CollectionUtils.isEmpty(lineSEntities.getErrors())) {
                    errors.addAll(lineSEntities.getErrors());
                }
            } catch (Exception e) {
                System.out.println("Error in concatenating errors");
                e.printStackTrace();
            }

            // Set event properties fetched from origin object
            try {
                if (origins.size() > 0) {
                    CreationInfo eventCreationInfo = new CreationInfo();
                    if (origins.get(0) != null) {
                        // Attach Arrival objects to the first Origin Entity
                        // First origin should be preferred
                        Origin org = origins.get(0);
                        org.setArrival(arrivals);

                        // Set preferredOriginID
                        ev.setPreferredOriginID(org.getPublicID());

                        // Generate eventID
                        String eventID = produceEventID(origins.get(0), options);
                        if (eventID == null) {
                            ignoredSfiles.add(sfile);
                            continue sFileLoop; // invalid event and thus skipped
                        }

                        ev.setEventID(eventID);
                        ev.setPublicID(IdGenerator.getInstance().genEventPublicID(eventID, Event.class));

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
                        ev.setCreationInfo(eventCreationInfo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Attach linked objects to event
            try {
                ev.setOrigin(origins);
                ev.setMagnitude(magnitudes);
                ev.setAmplitude(amplitudes);
                ev.setComment(comments);
                ev.setPick(picks);
                ev.setFocalMechanism(focalMechanisms);
                if(descriptions != null && descriptions.size() > 0) {
                    ev.setDescription(descriptions);
                } else {
                    ev.setDescription(null);
                }

                // Set preferred IDs
                if (line1Entities != null && line1Entities.getPreferredMagnitudeID() != null) {
                    ev.setPreferredMagnitudeID(line1Entities.getPreferredMagnitudeID());
                }
                if (lineFEntities != null && lineFEntities.getPreferredFocalMechanismID() != null) {
                    ev.setPreferredFocalMechanismID(lineFEntities.getPreferredFocalMechanismID());
                }

                // Set EventType and EventTypeCertainty
                EventTypeSetter.setEventTypeAndUncertainty(ev, l1s.get(0));

                // Set CreationInfo based on profile
                if (options.getProfile() == ConverterProfile.INTAROS) {
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
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        return new EventOverview(events, errors, ignoredSfiles);
    }

    private Line1EntityCollection convertLine1(List<Line1> l1s, List<LineE> les, SfileInfo sfileInfo) {
        String preferredOriginID = null;
        String preferredMagnitudeID = null;
        List<Origin> origins = new ArrayList<>();
        List<Magnitude> magnitudes = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (l1s != null) {
            line1Loop:
            for (int i = 0; i < l1s.size() ; i++) {
                Line1 line1 = l1s.get(i);

                // Map Origin entity
                Origin org = null;
                try {
                    Object orgObj = mapper.mapOrigin(line1, les, sfileInfo.getErrorHandling());
                    if (orgObj instanceof Origin) {
                        org = (Origin) orgObj;
                        origins.add(org);
                        line1.setOrgID(org.getPublicID());
                        // Set preferred origin to the first line (type 1)
                        if (i == 0) {
                            preferredOriginID = org.getPublicID();
                        }
                    } else if (orgObj instanceof IgnoredLineError) {
                        IgnoredLineError error = (IgnoredLineError) orgObj;
                        error.setEventNumber(sfileInfo.getEventCount());
                        error.setFilename(sfileInfo.getFilename());
                        if (i == 0) { // Only remove Event if it is the first Line1
                            error.setEventRemoved(true); // Removing Event
                            errors.add(error);
                            return null;
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
                        e.setFilename(sfileInfo.getFilename());
                        errors.add(e);
                    }
                });
            }

            // Determine Preferred Magnitude ID
            if (magnitudes != null && magnitudes.size() > 0) {
                // First should be preferred
                Magnitude firstMagnitude = magnitudes.get(0);
                preferredMagnitudeID = firstMagnitude.getPublicID();
            }

            return new Line1EntityCollection(preferredOriginID, preferredMagnitudeID, origins, magnitudes, errors);
        }
        return null;
    }

    private LineFEntityCollection convertLineF(List<Line1> l1s, List<LineF> lfs, List<LineM2> lm2s, SfileInfo sfileInfo) {
        String preferredFocalMechanismID = null;
        List<FocalMechanism> focalMechanisms = new ArrayList<>();
        List<Origin> lm1Origins = new ArrayList<>();
        List<Magnitude> lm1Magnitudes = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (l1s != null && lfs != null) {
            line1Loop:
            for (int i = 0; i < l1s.size() ; i++) {
                Line1 line1 = l1s.get(i);

                // Map FocalMechanism
                if (lfs != null) {
                    LineFLoop:
                    for (LineF lineF: lfs) {
                        Line1 l1Comparable = lineF.getRelatedLine1();
                        if (l1Comparable != null) {
                            // Check if FocalMechanism is related to current line1
                            if (line1.equals(l1Comparable)) {
                                FocalMechanism focalMechanism;
                                Object fObj = mapper.mapLine1FocalMechanisms(lineF);
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
                                                Object mtObj = mapper.mapMomentTensor(m2, line1);
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
                                                            lm1Origins.add(originM1);

                                                            // MomentTensor Magnitude
                                                            Magnitude magM1;
                                                            Object mM1Obj = mapper.mapMomentTensorMagnitude(relatedM1);
                                                            if (mM1Obj instanceof Magnitude) {
                                                                magM1 = (Magnitude) mM1Obj;
                                                                magM1.setOriginID(originM1.getPublicID());
                                                                lm1Magnitudes.add(magM1);
                                                            } else if (mM1Obj instanceof IgnoredLineError) {
                                                                IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                                e.setFilename(sfileInfo.getFilename());
                                                                errors.add(e);
                                                            }
                                                        } else if (oM1Obj instanceof IgnoredLineError) {
                                                            IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                            e.setFilename(sfileInfo.getFilename());
                                                            errors.add(e);
                                                        }
                                                    }
                                                    focalMechanism.setMomentTensor(momentTensor);
                                                } else if (mtObj instanceof IgnoredLineError) {
                                                    IgnoredLineError e = (IgnoredLineError) mtObj;
                                                    e.setFilename(sfileInfo.getFilename());
                                                    errors.add(e);
                                                }
                                            }
                                        }
                                    }
                                    focalMechanisms.add(focalMechanism);
                                } else if (fObj instanceof IgnoredLineError) {
                                    IgnoredLineError e = (IgnoredLineError) fObj;
                                    e.setFilename(sfileInfo.getFilename());
                                    errors.add(e);
                                }
                            }
                        }
                    }
                }
            }
            // Determine Preferred FocalMechanism ID
            if (focalMechanisms != null && focalMechanisms.size() > 0) {
                // First one listed should be the preferred
                FocalMechanism firstFocalMechListed = focalMechanisms.get(0);
                preferredFocalMechanismID = firstFocalMechListed.getPublicID();
            }

            return new LineFEntityCollection(preferredFocalMechanismID, lm1Origins,focalMechanisms, lm1Magnitudes, errors);
        }
        return null;
    }

    private Line3EntityCollection convertLine3(List<Line3> l3s, SfileInfo sfileInfo) {
        if (l3s != null) {
            List<EventDescription> descriptions = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();
            List<IgnoredLineError> errors = new ArrayList<>();

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
                    errors.add(generateError(line3, ex, sfileInfo));
                    continue line3Loop;
                }
            }

            return new Line3EntityCollection(descriptions, comments, errors);
        }
        return null;
    }

    private Line4EntityCollection convertLine4(List<Line1> l1s, List<Line> l4s, SfileInfo sfileInfo) {
        List<Arrival> arrivals = new ArrayList<>();
        List<Amplitude> amplitudes = new ArrayList<>();
        List<Pick> picks = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

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
                    errors.add(modifyError(e, sfileInfo));
                    continue line4Loop; // Pick is required fo the Arrival and Amplitude obj
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
                    errors.add(modifyError(e, sfileInfo));
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
                        errors.add(modifyError(e, sfileInfo));
                    }
                }
            }

            return new Line4EntityCollection(picks, amplitudes, arrivals, errors);
        }

        return null;
    }

    private Line5EntityCollection convertLine5(List<Line5> l5s, SfileInfo sfileInfo) {
        List<Comment> comments = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (l5s != null) {
            line5Loop:
            for (Line5 line5: l5s) {
                try {
                    comments.add(mapper.mapL5Comment(line5));
                } catch (Exception ex) {
                    errors.add(generateError(line5, ex, sfileInfo));
                    continue line5Loop;
                }
            }
            return new Line5EntityCollection(comments, errors);
        }
        return null;
    }

    private Line6EntityCollection convertLine6(List<Line6> l6s, SfileInfo sfileInfo) {
        List<Comment> comments = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (l6s != null) {
            line6Loop:
            for (Line6 line6: l6s) {
                try {
                    comments.add(mapper.mapL6Comment(line6));
                } catch (Exception ex) {
                    errors.add(generateError(line6, ex, sfileInfo));
                    continue line6Loop;
                }
            }
            return new Line6EntityCollection(comments, errors);
        }
        return null;
    }

    private LineIEntityCollection convertLineI(List<LineI> lis, SfileInfo sfileInfo) {
        List<Comment> comments = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (lis != null) {
            lineILoop:
            for (LineI lineI: lis) {
                try {
                    comments.add(mapper.mapLIComment(lineI));
                } catch (Exception ex) {
                    errors.add(generateError(lineI, ex, sfileInfo));
                    continue lineILoop;
                }
            }
            return new LineIEntityCollection(comments, errors);
        }
        return null;
    }

    private LineSEntityCollection convertLineS(List<LineS> lSs, SfileInfo sfileInfo) {
        List<Comment> comments = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (lSs != null) {
            lineSLoop:
            for (LineS lineS: lSs) {
                try {
                    comments.add(mapper.mapLSComment(lineS));
                } catch (Exception ex) {
                    errors.add(generateError(lineS, ex, sfileInfo));
                    continue lineSLoop;
                }
            }
            return new LineSEntityCollection(comments, errors);
        }
        return null;
    }

    private IgnoredLineError generateError(Line line, Exception ex, SfileInfo sfileInfo) {
        if (sfileInfo.getErrorHandling().equals("ignore")) {
            IgnoredLineError e = new IgnoredLineError(sfileInfo.getEventCount(), ex.getMessage());
            e.setLine(line);
            e.setFilename(sfileInfo.getFilename());
            return e;
        } else {
            throw new CustomException(
                    "Error found in s-file number: " + sfileInfo.getEventCount() + ". "
                            + ex.getMessage()
            );
        }
    }

    private IgnoredLineError modifyError(IgnoredLineError e, SfileInfo sfileInfo) {
        e.setFilename(sfileInfo.getFilename());
        e.setEventNumber(sfileInfo.getEventCount());
        return e;
    }

    private String produceEventID(Origin origin, ConverterOptions options) {
        DecimalFormat df =  (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        df.applyPattern("00");

        String sYear = "";
        if (origin.getCompositeTime().get(0).getYear() != null) {
            sYear = origin.getCompositeTime().get(0).getYear().getValue().toString();
        } else {
            System.out.println("Year not existing for creating ID. Event excluded");
            return null;
        }
        String sMonth = "";
        if (origin.getCompositeTime().get(0).getMonth() != null) {
            sMonth = df.format(origin.getCompositeTime().get(0).getMonth().getValue());
        } else {
            System.out.println("Month not existing for creating ID. Event excluded");
            return null;
        }
        String sDay = "";
        if (origin.getCompositeTime().get(0).getDay() != null) {
            sDay = df.format(origin.getCompositeTime().get(0).getDay().getValue());
        } else {
            System.out.println("Day not existing for creating ID. Event excluded");
            return null;
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
            eventID= IdGenerator.getInstance().genRandomEventID(
                    sYear, sMonth, sDay, sHour, sMinutes
            );
        }
        return eventID;
    }

}
