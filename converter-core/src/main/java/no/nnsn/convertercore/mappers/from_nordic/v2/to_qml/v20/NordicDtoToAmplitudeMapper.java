package no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterTwoType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.utils.AmplitudeUnitConverter;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeCategory;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeUnit;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = GeneralHelper.class)
public abstract class NordicDtoToAmplitudeMapper {

    public static NordicDtoToAmplitudeMapper INSTANCE = Mappers.getMapper(NordicDtoToAmplitudeMapper.class);

    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // RealQuantity (Object) - Convert in AfterMapping as paramterOne can contain multiple types
            @Mapping(target = "genericAmplitude.value", ignore = true),
            // String (changed in AfterMapping for Amplitude values)
            @Mapping(target = "type", source="line4Dto.phaseID"),
            // AmplitudeUnit (Enum) - Set by AfterMapping
            @Mapping(target = "unit", ignore = true),
            // AmplitudeCategory (Enum) - Set in AfterMapping for Duration value
            @Mapping(target = "category", ignore = true),
            // RealQuantity (Object) - Set in AfterMapping (ParameterTwo)
            @Mapping(target = "period.value", ignore = true),
            // TimeWindow (Object) - NO MAPPING DETERMINED
            @Mapping(target = "timeWindow", ignore = true),
            // WaveformStreamID (Object)
            @Mapping(target = "waveformID.stationCode", source="line4Dto.stationName"),
            @Mapping(target = "waveformID.channelCode", source="line4Dto.component"),
            @Mapping(target = "waveformID.networkCode", source="line4Dto.networkCode"),
            @Mapping(target = "waveformID.locationCode", source="line4Dto.location"),
            // String - Pointer to related Pick object - Set by class calling the mapper
            @Mapping(target = "pickID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "filterID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "magnitudeHint", ignore = true),
            // TimeQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "scalingTime", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "snr", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source = "line4Dto.agency"),
            @Mapping(target = "creationInfo.author", source = "line4Dto.operator"),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
    })
    public abstract Amplitude mapLine4ToAmplitude(Line4Dto line4Dto, Line1 line1, Pick pick);

    @AfterMapping
    protected void buildAmplitudePublicID(@MappingTarget Amplitude amp, Line1 line1, Line4Dto line4Dto) {
        amp.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4Dto.getStationName() != null ? line4Dto.getStationName() : line1.getHypoCenterRepAgency()),
                Amplitude.class));
    }

    @AfterMapping
    protected void convertParameterOne(@MappingTarget Amplitude amp, Line4Dto line4Dto) {
        String paramOne = line4Dto.getParameterOne();
        String phaseID = line4Dto.getPhaseID();
        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(paramOne, phaseID);

        // Parameters: Amplitude and Duration (Backazimuth and Polarity belongs to Pick entity)
        if (pOneType == ParameterOneType.AMPLITUDE) {
            Double ampDoubleValue = (paramOne != null && !paramOne.isEmpty()) ? Double.parseDouble(paramOne) : null;
            if (amp.getGenericAmplitude() == null) {
                RealQuantity realQuantity = new RealQuantity();
                realQuantity.setValue(ampDoubleValue);
                amp.setGenericAmplitude(realQuantity);
            } else {
                amp.getGenericAmplitude().setValue(ampDoubleValue);
            }
            amp.setType(line4Dto.getPhaseID());
        } else if (pOneType == ParameterOneType.DURATION) {
            // Set value
            Double codaValue = Double.parseDouble(paramOne);
            if (amp.getGenericAmplitude() == null) {
                RealQuantity ampQuantity = new RealQuantity();
                ampQuantity.setValue(codaValue);
                amp.setGenericAmplitude(ampQuantity);
            } else {
                amp.getGenericAmplitude().setValue(codaValue);
            }

            // Set Type
            amp.setType("END");
            // Set category
            amp.setCategory(AmplitudeCategory.DURATION);
            // Set unit
            amp.setUnit(AmplitudeUnit.Seconds);
        }
    }

    @AfterMapping
    protected void setAmpUnitAndConvertVal(@MappingTarget Amplitude amp, Line4Dto line4Dto) {
        if (line4Dto.getPhaseID() != null && amp.getGenericAmplitude() != null) {
            String phaseID = line4Dto.getPhaseID();
            Double ampValue = amp.getGenericAmplitude().getValue();
            switch (phaseID) {
                case "AML":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "AMB":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "AMS":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "IAML":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "IAmb":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "IVmB_BB":
                    // nm/s to m/s
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmsToMs(ampValue));
                    amp.setUnit(AmplitudeUnit.METER_SECONDS);
                    break;
                case "IAMs_20":
                    // nm to m
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmToM(ampValue));
                    amp.setUnit(AmplitudeUnit.Meter);
                    break;
                case "IVMs_BB":
                    // nm/s to m/s
                    amp.getGenericAmplitude().setValue(AmplitudeUnitConverter.fromNmsToMs(ampValue));
                    amp.setUnit(AmplitudeUnit.METER_SECONDS);
                    break;
                default:
                    amp.setUnit(AmplitudeUnit.DIMENSIONLESS);
                    break;
            }
        }
    }

    @AfterMapping
    protected void convertParameterTwo(@MappingTarget Amplitude amp, Line4Dto line4Dto) {
        String paramOne = line4Dto.getParameterOne();
        String paramTwo = line4Dto.getParameterTwo();
        String phaseID = line4Dto.getPhaseID();

        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(paramOne, phaseID);
        ParameterTwoType pTwoType = PhaseParameters.identifyParameterTwoType(pOneType);

        // Parameters: Period (Apparent velocity is in Pick entity)
        if (pTwoType == ParameterTwoType.PERIOD) {
            Double periodValue = (paramTwo != null && !paramTwo.isEmpty()) ? Double.parseDouble(paramTwo) : null;
            if (amp.getPeriod() == null) {
                RealQuantity periodObject = new RealQuantity();
                periodObject.setValue(periodValue);
                amp.setPeriod(periodObject);
            } else {
                amp.getPeriod().setValue(periodValue);
            }
        }

    }

    @AfterMapping
    protected void setChildToNull(@MappingTarget Amplitude amp) {
        // genericAmplitude (RealQuantity)
        if (ChildChecker.isRealQuantityNull(amp.getGenericAmplitude())) {
            amp.setGenericAmplitude(null);
        }

        // period (RealQuantity)
        if (ChildChecker.isRealQuantityNull(amp.getPeriod())) {
            amp.setPeriod(null);
        }

        // waveformID (WaveformStreamID)
        if (ChildChecker.isWaveformStreamIdNull(amp.getWaveformID())) {
            amp.setWaveformID(null);
        }

        /* scalingTime (TimeQuantity)
        if(ChildChecker.isTimeQuantityNull(amp.getScalingTime())) {
            amp.setScalingTime(null);
        } */
    }
}
