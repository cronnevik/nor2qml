package no.nnsn.convertercore.mappers;

import no.nnsn.convertercore.errors.IgnoredQmlError;
import no.nnsn.convertercore.helpers.Line4Entities;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.*;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.LineType;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class NordicMapperImpl implements NordicMapper {
    @Override
    public List<Object> mapLine1s(Event event) {

        List<Object> line1s = new ArrayList<>();
        List<Magnitude> magnitudes = event.getMagnitude();
        List<StationMagnitude> stationMagnitudes = null;

        for (Origin org: event.getOrigin()) {

            List<Magnitude> filteredMags = null;

            // Check if magnitude entities exists
            if (magnitudes != null) {
                // filter magnitude based on OriginID
                filteredMags =
                        magnitudes.stream().filter(
                                (magnitude -> magnitude.getOriginID().equals(org.getPublicID()))
                        ).collect(Collectors.toList());

                // Check number of magnitudes as Line1 can only store 3
                int numMags = filteredMags.size();

                // TODO - Handle more than 3 Magnitudes for each origin

            } else if (stationMagnitudes != null){
                // TODO - Handle mapping without magnitudes
                // filter stationMagnitudes based on OriginID
                filteredMags = null;

                int numStatMags = filteredMags.size();

                // TODO - Handle more than 3 Stationmagnitudes for each origin
            }

            // Mapping for each origin
            try {
                line1s.add(Line1Mapper.INSTANCE.mapLine1(event.getPreferredMagnitudeID(), org, filteredMags, event.getType(), event.getTypeCertainty()));
            } catch (Exception ex) {
                line1s.add(new IgnoredQmlError(ex.getMessage(), "line1"));
            }
        }

        return line1s;
    }

    @Override
    public List<Object> mapLine3s(Event event) {
        List<Object> line3s = new ArrayList<>();

        // Add all event descriptions as Line3
        if (event.getDescription() != null ) {
            for (EventDescription evDesc: event.getDescription()) {

                if (evDesc.getText() != null) {
                    try {
                        line3s.add(Line3Mapper.INSTANCE.mapEventDescriptionText(evDesc));
                    } catch (Exception ex) {
                        line3s.add(new IgnoredQmlError(ex.getMessage(), "line3"));
                    }
                }
            }
        }

        // Add all Event Comments as Line3
        if (event.getComment() != null ) {
            for (Comment comment: event.getComment()) {

                if (comment.getId() != null) {
                    // If an id with seisan line type marker exists, check if it is a type 3
                    if (LineType.LINETYPE_3.equalValue(comment.getId())) {
                        try {
                            line3s.add(Line3Mapper.INSTANCE.mapEventFullComment(comment));
                        } catch (Exception ex) {
                            line3s.add(new IgnoredQmlError(ex.getMessage(), "line3"));
                        }

                    }
                } else {
                    //  All other lines should be mapped to a LineType 3
                    try {
                        line3s.add(Line3Mapper.INSTANCE.mapEventComment(comment));
                    } catch (Exception ex) {
                        line3s.add(new IgnoredQmlError(ex.getMessage(), "line3"));
                    }
                }
            }
        }

        return line3s;
    }

    @Override
    public List<Object> mapLine4s(NordicFormatVersion format, List<Pick> picks, List<Amplitude> amplitudes, List<Origin> origins) {
        List<Object> line4s = new ArrayList<>();

        Map<String, Line4Entities> l4objects= new TreeMap<String, Line4Entities>();

        if (picks != null && picks.size() > 0) {

            String time = picks.get(0).getTime().getValue();
            LocalDateTime phaseTime = null;
            ZonedDateTime phaseTimeZoned = null;
            if (!StringUtils.isBlank(time)) {
                if(time.substring(time.length() -1 ).equals("Z")) {
                    phaseTimeZoned = ZonedDateTime.parse(time);
                } else {
                    phaseTime = LocalDateTime.parse(time);
                }

            }

            for (Pick pick: picks) {
                // Handle phased going over midnight
                if (picks.size() > 1) {
                    String timeCompare = pick.getTime().getValue();
                    if (!StringUtils.isBlank(timeCompare) && !StringUtils.isBlank(time)) {
                        if(timeCompare.substring(timeCompare.length() -1 ).equals("Z")) {
                            ZonedDateTime timeZoned = ZonedDateTime.parse(timeCompare);
                            if (timeZoned.getDayOfMonth() != phaseTimeZoned.getDayOfMonth()) {
                                pick.setTimeOverMidnight(true);
                            }
                        } else {
                            LocalDateTime phaseTimeCompare = LocalDateTime.parse(timeCompare);
                            if (phaseTimeCompare.getDayOfMonth() != phaseTime.getDayOfMonth()) {
                                pick.setTimeOverMidnight(true);
                            }
                        }
                    }



                }
                String pickID = pick.getPublicID();
                l4objects.put(
                        pickID,
                        new Line4Entities(pick)
                );
            }
        }

        if (amplitudes != null) {
            for (Amplitude amp: amplitudes) {
                String pickID = amp.getPickID();
                if (pickID != null) {
                    Line4Entities l4ent = l4objects.get(pickID);
                    if (l4ent != null) {
                        l4ent.setAmplitude(amp);
                    }
                }

                //TODO - handle amplitudes without PickID

            }
        }

        for (Origin org: origins) {
            List<Arrival> arrivals = org.getArrival();
            if (arrivals != null) {
                for (Arrival arr: arrivals) {
                    String pickID = arr.getPickID();
                    Line4Entities l4ent = l4objects.get(pickID);
                    l4ent.setArrival(arr);
                }
            }

        }

        l4objects.forEach((key, value) -> {
            if (format == NordicFormatVersion.VERSION1) {
                Line4 line4;
                try {
                    line4 = Line4Mapper.INSTANCE.mapLine4(value);
                    line4s.add(line4);
                } catch (Exception ex) {
                    //ex.printStackTrace();
                    IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "line4");
                    line4s.add(errObj);
                }
            } else if (format == NordicFormatVersion.VERSION2) {
                Line4Dto line4Dto;
                try {
                    line4Dto = Line4DtoMapper.INSTANCE.mapLine4Dto(value);
                    line4s.add(line4Dto);
                } catch (Exception ex) {
                    IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "line4");
                    line4s.add(errObj);
                }
            } else {
                System.out.println("No nordic format version output specified");
            }
        });
        return line4s;
    }

    @Override
    public List<Object> mapLine6s(Event event) {
        List<Object> line6s = new ArrayList<>();

        if (event.getComment() != null ) {
            for (Comment comment: event.getComment()) {
                if (comment.getId() != null) {
                    if (LineType.LINETYPE_6.equalValue(comment.getId())) {
                        try {
                            line6s.add(Line6Mapper.INSTANCE.mapLine6(comment));
                        } catch (Exception ex) {
                            IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "line6");
                            line6s.add(errObj);
                        }
                    }
                }
            }
        }

        return line6s;
    }

    @Override
    public List<Object> mapLineEs(List<Origin> origins) {
        List<Object> lineES = new ArrayList<>();

        for (Origin org: origins) {
            LineE lineE = null;
            try {
                lineE = LineEMapper.INSTANCE.mapFullLineE(org);
            } catch (Exception ex) {
                IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineE");
                lineES.add(errObj);
            }

            // Only add line to array if it is not empty, meaning that it contains more than the 'E' marker at the end
            if (lineE != null &&!lineE.createLine().trim().equals("E")) {
                try {
                    lineES.add(LineEMapper.INSTANCE.mapFullLineE(org));
                } catch (Exception ex) {
                    IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage());
                    lineES.add(errObj);
                }
            }
        }

        return lineES;
    }

    @Override
    public List<Object> mapLineFs(List<FocalMechanism> focalMechanisms) {
        if (focalMechanisms != null) {
            List<Object> lineFS = new ArrayList<>();

            for (FocalMechanism fMech: focalMechanisms) {
                if (fMech.getNodalPlanes() != null) {
                    try {
                        lineFS.add(LineFMapper.INSTANCE.mapLineFGeneral(fMech));
                    } catch (Exception ex) {
                        IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineF");
                        lineFS.add(errObj);
                    }
                }
            }


            return lineFS;
        } else {
            return null;
        }

    }

    @Override
    public List<Object> mapLineIs(Event event) {

        List<Object> lineIS = new ArrayList<>();

        if (event.getComment() != null ) {
            for (Comment comment: event.getComment()) {
                if (comment.getId() != null) {
                    if (LineType.LINETYPE_I.equalValue(comment.getId())) {
                        try {
                            lineIS.add(LineIMapper.INSTANCE.mapLineI(comment));
                        } catch (Exception ex) {
                            IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineI");
                            lineIS.add(errObj);
                        }
                    }
                }
            }
        }
        return lineIS;
    }

    @Override
    public List<Object> mapLineM1s(List<Origin> origins, List<FocalMechanism> focalMechanisms, List<Magnitude> magnitudes) {

        List<Object> lineM1s = new ArrayList<>();

        if (origins != null && focalMechanisms != null && magnitudes != null) {
            for (FocalMechanism fMech: focalMechanisms) {
                MomentTensor momentTensor = fMech.getMomentTensor();
                if (momentTensor != null) {
                    String originID = momentTensor.getDerivedOriginID();

                    Origin relatedOrigin = null;
                    for (Origin origin: origins) {
                        if (origin.getPublicID().equals(originID)) {
                            relatedOrigin = origin;
                        }
                    }
                    Magnitude relatedMagnitude = null;
                    for (Magnitude magnitude: magnitudes) {
                        if (magnitude.getOriginID().equals(originID)) {
                            relatedMagnitude = magnitude;
                        }
                    }

                    if (relatedOrigin != null ) {
                        Object obj = null;
                        if (relatedMagnitude != null) {
                            try {
                                obj = LineM1Mapper.INSTANCE.mapLineM1Full(relatedOrigin,relatedMagnitude);
                            } catch (Exception ex) {
                                IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineM1");
                                lineM1s.add(errObj);
                            }
                        } else {
                            try {
                                obj = LineM1Mapper.INSTANCE.mapLineM1Org(relatedOrigin);
                            } catch (Exception ex) {
                                IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineM1");
                                lineM1s.add(errObj);
                            }
                        }
                        if (obj instanceof LineM1) {
                            LineM1 lineM1 = (LineM1) obj;
                            if (StringUtils.isBlank(lineM1.getReportingAgency())) {
                                String agencyID =  fMech.getCreationInfo().getAgencyID();
                                // ISC format give the agencyID within author
                                String author = fMech.getCreationInfo().getAuthor();
                                lineM1.setReportingAgency(LineHelper.setMagAgencies(agencyID, author));
                            }
                            if (fMech.getMethodID() != null) {
                                lineM1.setMethodUsed(fMech.getMethodID());
                            }
                            lineM1s.add(lineM1);
                        } else {
                            lineM1s.add(obj);
                        }
                    }
                }
            }
        }
        return lineM1s;
    }

    @Override
    public List<Object> mapLineM2s(List<FocalMechanism> focalMechanisms) {

        List<Object> lineM2s = new ArrayList<>();

        if (focalMechanisms != null) {
            for (FocalMechanism fMech: focalMechanisms) {
                MomentTensor momentTensor = fMech.getMomentTensor();
                if (momentTensor != null) {
                    try {
                        LineM2 lineM2 = LineM2Mapper.INSTANCE.mapLineM2(momentTensor);
                        if (StringUtils.isBlank(lineM2.getReportingAgency())) {
                            String agencyID =  fMech.getCreationInfo().getAgencyID();
                            // ISC format give the agencyID within author
                            String author = fMech.getCreationInfo().getAuthor();
                            lineM2.setReportingAgency(LineHelper.setMagAgencies(agencyID, author));
                        }
                        // Set method equal to focalmechanism method
                        if (fMech.getMethodID() != null) {
                            lineM2.setMethodUsed(fMech.getMethodID());
                        }
                        lineM2s.add(lineM2);
                    } catch (Exception ex) {
                        IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "lineM2");
                        lineM2s.add(errObj);
                    }
                }
            }
        }
        return lineM2s;
    }

    @Override
    public List<Object> mapLineS(Event event) {
        List<Object> lineSs = new ArrayList<>();

        if (event.getComment() != null ) {
            for (Comment comment: event.getComment()) {
                if (comment.getId() != null) {
                    if (LineType.LINETYPE_S.equalValue(comment.getId())) {
                        try {
                            lineSs.add(LineSMapper.INSTANCE.mapLineS(comment));
                        } catch (Exception ex) {
                            IgnoredQmlError errObj = new IgnoredQmlError(ex.getMessage(), "line6");
                            lineSs.add(errObj);
                        }
                    }
                }
            }
        }

        return lineSs;
    }
}
