package no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.EnumsQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.helpers.EnumsHelper;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterTwoType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickPolarity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                GeneralHelper.class,
                EnumsHelper.class
        })
public abstract class NordicDtoToPickMapper {
    public static NordicDtoToPickMapper INSTANCE = Mappers.getMapper(NordicDtoToPickMapper.class);

    @Mappings({
            // String (ResourceReference) - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // TimeQuantity (Object) - Set by AfterMapping (String -> Object)
            @Mapping(target = "time.value", ignore = true),
            // WaveformStreamID (Object)
            @Mapping(target = "waveformID.stationCode", source="line4Dto.stationName"),
            @Mapping(target = "waveformID.channelCode", source="line4Dto.component"),
            @Mapping(target = "waveformID.networkCode", source="line4Dto.networkCode"),
            @Mapping(target = "waveformID.locationCode", source="line4Dto.location"),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "filterID", ignore = true),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // RealQuantity (Object) - Set in AfterMapping
            @Mapping(target = "backazimuth.value", ignore = true),
            // RealQuantity (Object) - Set in AfterMapping
            @Mapping(target = "horizontalSlowness.value", ignore = true),
            // PickOnset (Enum)
            @Mapping(target = "onset", source = "line4Dto.qualityIndicator", qualifiedBy = EnumsQualifiers.StringToPickOnset.class),
            // String (ResourceReference)
            @Mapping(target = "phaseHint", source="line4Dto.phaseID"),
            // PickPolarity (Enum) - Set in AfterMapping
            @Mapping(target = "polarity", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source = "line4Dto.agency"),
            @Mapping(target = "creationInfo.author", source = "line4Dto.operator"),
    })
    public abstract Pick mapLine4DtoToPick(Line4Dto line4Dto, Line1 line1);

    @AfterMapping
    protected void buildPickPublicID(@MappingTarget Pick pick, Line1 line1, Line4Dto line4Dto) {
        pick.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4Dto.getStationName() != null ? line4Dto.getStationName() : line1.getHypoCenterRepAgency()),
                Pick.class));
    }

    @AfterMapping
    protected void buildPhaseTimeIsoString(@MappingTarget Pick pick, Line1 line1, Line4Dto line4Dto) {
        int year = line1.getYear() != null && !line1.getYear().isEmpty() ? Integer.parseInt(line1.getYear()) : 0;
        int month = line1.getMonth() != null && !line1.getMonth().isEmpty() ? Integer.parseInt(line1.getMonth()) : 0;
        int day = line1.getDay() != null && !line1.getDay().isEmpty() ? Integer.parseInt(line1.getDay()) : 0;

        // Use Line4 for specific time of phase
        int hour = line4Dto.getHour() != null && !line4Dto.getHour().isEmpty() ? Integer.parseInt(line4Dto.getHour()) : 0;
        int minute = line4Dto.getMinutes() != null && !line4Dto.getMinutes().isEmpty() ? Integer.parseInt(line4Dto.getMinutes()) : 0;
        int second = 0;
        int nanoSec = 0;

        String secTemp = line4Dto.getSeconds();
        if (secTemp != null && !secTemp.isEmpty()) {
            Double dSec = Double.parseDouble(secTemp);
            second = dSec.intValue();

            String[] secSplit = String.valueOf(dSec).split("\\.");
            if (secSplit.length == 2) {

                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                DecimalFormat df = (DecimalFormat)nf;
                df.applyPattern("0.00");

                double[] secNums = new double[2];
                secNums[0] = Double.parseDouble(secSplit[0]);
                secNums[1] = Double.parseDouble("0." + secSplit[1]);

                double nanoSecTemp = (secNums[1]) * Math.pow(10,9);
                nanoSec = (int) nanoSecTemp;
            }
        }

        // Check if hour points to the next day (being 24 or 25 in s-file) and return proper Iso String
        if (hour >= 24) {
            LocalDateTime phaseTime = LocalDateTime.of(year, month, day, 23, minute, second, nanoSec);
            LocalDateTime newPhaseTime = phaseTime.plusHours((hour-23));
            TimeQuantity timeQuantity = new TimeQuantity();
            timeQuantity.setValue(newPhaseTime.format(DateTimeFormatter.ISO_DATE_TIME));
            pick.setTime(timeQuantity);
        } else {
            LocalDateTime phaseTime = LocalDateTime.of(year, month, day, hour, minute, second, nanoSec);
            TimeQuantity timeQuantity = new TimeQuantity();
            timeQuantity.setValue(phaseTime.format(DateTimeFormatter.ISO_DATE_TIME));
            pick.setTime(timeQuantity);
        }
    }

    @AfterMapping
    protected void setBackAzimuthValue(@MappingTarget Pick pick, Line4Dto line4Dto) {
        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(line4Dto.getParameterOne(), line4Dto.getPhaseID());

        if (pOneType == ParameterOneType.BACK_AZIMUTH) {
            String pOne = line4Dto.getParameterOne();
            Double backAzimuthValue = pOne != null && !pOne.isEmpty() ? Double.parseDouble(pOne) : null;
            if (backAzimuthValue != null) {
                if (pick.getBackazimuth() == null) {
                    RealQuantity realQuantity = new RealQuantity();
                    realQuantity.setValue(backAzimuthValue);
                    pick.setBackazimuth(realQuantity);
                } else {
                    pick.getBackazimuth().setValue(backAzimuthValue);
                }
            }
        }
    }

    @AfterMapping
    protected void setHorizontalSlownessValue(@MappingTarget Pick pick, Line4Dto line4Dto) {
        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(line4Dto.getParameterOne(), line4Dto.getPhaseID());
        ParameterTwoType pTwoType = PhaseParameters.identifyParameterTwoType(pOneType);

        if (pTwoType == ParameterTwoType.APPARENT_VELOCITY) {
            String pTwo = line4Dto.getParameterTwo();
            Double hzonSlowValue = pTwo != null && !pTwo.isEmpty() ? Double.parseDouble(pTwo) : null;
            if (hzonSlowValue != null) {

                // Convert from Km/sec to s · deg−1
                Double convHorizVal = null;
                if (hzonSlowValue == 1.0) {
                    convHorizVal = 111.2;
                } else {
                    Double newHorSlow = (111.2 / hzonSlowValue);
                    convHorizVal = Math.round(newHorSlow * 100.0) / 100.0;
                }

                // Sert value to pick entity
                if (pick.getHorizontalSlowness() == null) {
                    RealQuantity realQuantity = new RealQuantity();
                    realQuantity.setValue(convHorizVal);
                    pick.setHorizontalSlowness(realQuantity);
                } else {
                    pick.getHorizontalSlowness().setValue(convHorizVal);
                }
            }
        }

    }

    @AfterMapping
    protected void setPoliarity(@MappingTarget Pick pick, Line4Dto line4Dto) {
        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(line4Dto.getParameterOne(), line4Dto.getPhaseID());
        if (pOneType == ParameterOneType.POLARITY) {
            String pOne = line4Dto.getParameterOne().trim();

            switch(pOne) {
                case "C":
                    pick.setPolarity(PickPolarity.POSITIVE);
                    break;
                case "D":
                    pick.setPolarity(PickPolarity.NEGATIVE);
                    break;
                default:
                    pick.setPolarity(PickPolarity.UNDECIDABLE);
                    break;
            }
        }
    }
}
