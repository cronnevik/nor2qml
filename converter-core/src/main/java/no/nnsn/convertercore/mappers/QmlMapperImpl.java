package no.nnsn.convertercore.mappers;

import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.*;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.NodalPlaneSetter;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.NordicDtoToAmplitudeMapper;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.NordicDtoToArrivalMapper;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.NordicDtoToPickMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QmlMapperImpl implements QmlMapper {
    @Override
    public Object mapOrigin(Line1 line1, List<LineE> lEs, String errorHandling) {
        Origin originL1;

        try {
            originL1 =  NordicToOriginMapper.INSTANCE.mapL1Origin(line1);
        } catch (Exception ex) {
            IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
            errObj.setLine(line1);
            errObj.setRowNumber(line1.getRowNumber());
            return errObj;
        }

        if (lEs != null) {
            if (lEs.size() > 0) {
                try {
                    return NordicToOriginMapper.INSTANCE.mapFullOrigin(line1, lEs.get(0));
                } catch (Exception ex) {
                    IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                    errObj.setLine(lEs.get(0));
                    return errObj;
                }

            } else {
                return originL1;
            }
        } else {
            return originL1;
        }

    }

    @Override
    public List<Object> mapLine1Magnitudes(Line1 line1, Origin org) {

        List<Object> magnitudes = new ArrayList<>();

        // Map Magnitude entity
        if (line1.getMagOneNo() != null && !line1.getMagOneNo().trim().isEmpty()) {
            Magnitude mag1;
            try {
                mag1 = NordicToMagnitudeMapper.INSTANCE.mapLine1toMagnitude1(line1 ,org);
                magnitudes.add(mag1);
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line1);
                errObj.setRowNumber(line1.getRowNumber());
                magnitudes.add(errObj);
            }
        }
        if (line1.getMagTwoNo() != null && !line1.getMagTwoNo().trim().isEmpty()) {
            Magnitude mag2;
            try {
                mag2 = NordicToMagnitudeMapper.INSTANCE.mapLine1toMagnitude2(line1, org);
                magnitudes.add(mag2);
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line1);
                errObj.setRowNumber(line1.getRowNumber());
                magnitudes.add(errObj);
            }
        }
        if (line1.getMagThreeNo() != null && !line1.getMagThreeNo().trim().isEmpty()) {
            Magnitude mag3;
            try {
                mag3 = NordicToMagnitudeMapper.INSTANCE.mapLine1toMagnitude3(line1, org);
                magnitudes.add(mag3);
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line1);
                errObj.setRowNumber(line1.getRowNumber());
                magnitudes.add(errObj);
            }
        }

        return magnitudes;
    }

    @Override
    public Object mapLine1FocalMechanisms(LineF lineF) {
        FocalMechanism fmech;
        try {
            fmech = NordicToFocalMechMapper.INSTANCE.mapFocalMech(lineF, lineF.getRelatedLine1());
        } catch (Exception ex) {
            IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
            errObj.setLine(lineF);
            errObj.setRowNumber(lineF.getRowNumber());
            return errObj;
        }
        return fmech;
    }

    @Override
    public List<FocalMechanism> mapLine1FocalMechanismsOld(
            int i,
            List<LineF> lfs,
            List<FocalMechanism> fmechs,
            List<LineM2> lineM2s,
            Event event,
            List<Line1> l1s,
            Origin org
    ) {
        List<FocalMechanism> focalMechanisms = new ArrayList<>();

        // Intaros Spesific
        List<LineF> usedLineFs = new ArrayList<>();
        List<LineM2> usedLineM2s = new ArrayList<>();

        int nextFocRowNum = lfs.get(0).getRowNumber();
        int currLine1RowNum = l1s.get(i).getRowNumber();
        Integer nextLine1RowNum = (i < (l1s.size() - 1)) ? l1s.get(i + 1).getRowNumber() : null;

        // Check if F lines is following current Line 1
        Boolean nextL1BiggerOrNull = false;
        if (nextLine1RowNum == null) {
            nextL1BiggerOrNull = true;
        } else if(nextFocRowNum < nextLine1RowNum) {
            nextL1BiggerOrNull = true;
        }
        if (nextFocRowNum > currLine1RowNum && nextL1BiggerOrNull) {
            int limit = lfs.size() >= 2 ? 2 : lfs.size();

            for (int j = 0; j < limit; j++) {
                LineF lineF = lfs.get(j);

                // Check if LineF has any markers - INTAROS spesifications
                if (StringUtils.isNotBlank(lineF.getBlankField())) {
                    if (lineF.getBlankField().equals("O")) {
                        Line1 line1 = l1s.get(i);
                        FocalMechanism currentFm = NordicToFocalMechMapper.INSTANCE.mapFocalMech(lineF, line1);
                        String currentAgency = currentFm.getCreationInfo().getAgencyID();

                        // Check if the reporting agency is previously listed
                        for (FocalMechanism fm : fmechs) {
                            String agency = fm.getCreationInfo().getAgencyID();
                            if (agency.equals(currentAgency)) {
                                fmechs.remove(fm);
                            }
                        }

                        if (event.getPreferredFocalMechanismID() == null) {
                            event.setPreferredFocalMechanismID(currentFm.getPublicID());
                        }

                        fmechs.add(currentFm);

                    } else if (lineF.getBlankField().equals("+")) {
                        FocalMechanism lastFocalMech = fmechs.get(fmechs.size() - 1);
                        NodalPlaneSetter.setNodalPlane(lastFocalMech, lineF);

                        if (lineM2s != null) {
                            if (lineM2s.size() > 0) {
                                LineM2 lineM2 = lineM2s.get(0);
                                MomentTensor momentTensor = NordicToMomentTensorMapper.INSTANCE.mapLineM2(lineM2, l1s.get(i));
                                lastFocalMech.setMomentTensor(momentTensor);
                                usedLineM2s.add(lineM2);
                            }
                        }
                    }
                    usedLineFs.add(lineF);
                }

                // TODO - Implement Generic LineF mapping
                FocalMechanism focalMechanism = NordicToFocalMechMapper.INSTANCE.mapFocalMech(lineF, l1s.get(i));

            }

            if (usedLineFs != null || usedLineFs.size() > 0) {
                if (lineM2s != null) {
                    if (lineM2s.size() > 1) {
                        lineM2s.removeAll(usedLineM2s);
                    } else {
                        lineM2s.clear();
                    }
                }

                // remove the two used F lines from list
                if (lfs.size() > 2) {
                    lfs.removeAll(usedLineFs);

                } else {
                    lfs.clear();
                }
            }

        }

        return fmechs;
    }

    @Override
    public Object mapMomentTensor(LineM2 lineM2, Line1 line1) {
        MomentTensor mT;
        try {
            mT = NordicToMomentTensorMapper.INSTANCE.mapLineM2(lineM2, line1);
        } catch (Exception ex) {
            IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
            errObj.setLine(lineM2);
            errObj.setRowNumber(lineM2.getRowNumber());
            return errObj;
        }
        return mT;
    }

    @Override
    public Object mapMomentTensorOrigin(LineM1 lineM1) {
        Origin origin;
        try {
            origin = NordicToMomentTensorMapper.INSTANCE.mapLineM1Origin(lineM1);
        } catch (Exception ex) {
            IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
            errObj.setLine(lineM1);
            errObj.setRowNumber(lineM1.getRowNumber());
            return errObj;
        }
        return origin;
    }

    @Override
    public Object mapMomentTensorMagnitude(LineM1 lineM1) {
        Magnitude magnitude;
        try {
            magnitude = NordicToMomentTensorMapper.INSTANCE.mapLineM1Magnitude(lineM1);
        } catch (Exception ex) {
            IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
            errObj.setLine(lineM1);
            errObj.setRowNumber(lineM1.getRowNumber());
            return errObj;
        }
        return magnitude;
    }

    @Override
    public Comment mapL3Comment(Line3 line3) {
        return NordicToCommentMapper.INSTANCE.mapLine3ToComment(line3);
    }

    @Override
    public Comment mapL5Comment(Line5 line5) {
        return NordicToCommentMapper.INSTANCE.mapLine5ToComment(line5);
    }

    @Override
    public Comment mapL6Comment(Line6 line6) {
        return NordicToCommentMapper.INSTANCE.mapLine6ToComment(line6);
    }

    @Override
    public Comment mapLIComment(LineI lineI) {
        return NordicToCommentMapper.INSTANCE.mapLineITOComment(lineI);
    }

    @Override
    public Comment mapLSComment(LineS lineS) {
        return NordicToCommentMapper.INSTANCE.mapLineSToComment(lineS);
    }

    @Override
    public Object mapPick(Line4 line4, Line4Dto line4Dto, List<Line1> l1s) {
        Pick pick = null;
        if (line4 != null) {
            try {
                pick = NordicToPickMapper.INSTANCE.mapLine4ToPick(line4, l1s.get(0));
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4);
                errObj.setRowNumber(line4.getRowNumber());
                return errObj;
            }

        } else if (line4Dto != null) {
            try {
                pick = NordicDtoToPickMapper.INSTANCE.mapLine4DtoToPick(line4Dto, l1s.get(0));
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4Dto);
                errObj.setRowNumber(line4Dto.getRowNumber());
                return errObj;
            }
        }
        return pick;
    }

    @Override
    public Object mapArrival(Line4 line4, Line4Dto line4Dto, Pick pick, List<Line1> l1s) {
        Arrival arr = null;
        if (line4 != null) {
            try {
                arr = NordicToArrivalMapper.INSTANCE.mapLine4ToArrival(line4, l1s.get(0));
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4);
                errObj.setRowNumber(line4.getRowNumber());
                return errObj;
            }

            if (pick.getPublicID() != null && !pick.getPublicID().isEmpty()) {
                arr.setPickID(pick.getPublicID());
            }
        } else if (line4Dto != null) {
            try {
                arr = NordicDtoToArrivalMapper.INSTANCE.mapLine4DtoToArrival(line4Dto, l1s.get(0));
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4Dto);
                errObj.setRowNumber(line4Dto.getRowNumber());
                return errObj;
            }

            if (pick.getPublicID() != null && !pick.getPublicID().isEmpty()) {
                arr.setPickID(pick.getPublicID());
            }
        }
        return arr;
    }

    @Override
    public Object mapAmplitude(Line4 line4, Line4Dto line4Dto, Pick pick, List<Line1> l1s) {
        Amplitude amp = null;
        if (line4 != null) {
            try {
                amp = NordicToAmplitudeMapper.INSTANCE.mapLine4ToAmplitude(line4, l1s.get(0), pick);
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4);
                errObj.setRowNumber(line4.getRowNumber());
                return errObj;
            }

            if (pick.getPublicID() != null && !pick.getPublicID().isEmpty()) {
                amp.setPickID(pick.getPublicID());
            }
        } else if (line4Dto != null) {
            try {
                amp = NordicDtoToAmplitudeMapper.INSTANCE.mapLine4ToAmplitude(line4Dto, l1s.get(0), pick);
            } catch (Exception ex) {
                IgnoredLineError errObj = new IgnoredLineError(ex.getMessage());
                errObj.setLine(line4Dto);
                errObj.setRowNumber(line4Dto.getRowNumber());
                return errObj;
            }

            if (pick.getPublicID() != null && !pick.getPublicID().isEmpty()) {
                amp.setPickID(pick.getPublicID());
            }
        }

        return amp;
    }
}
