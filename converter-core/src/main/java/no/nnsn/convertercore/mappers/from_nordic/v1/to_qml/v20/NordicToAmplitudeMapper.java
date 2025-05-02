package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers.*;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.PhaseIDSetter;
import no.nnsn.convertercore.mappers.utils.AmplitudeUnitConverter;
import no.nnsn.convertercore.mappers.utils.CharacterChecker;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeCategory;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeUnit;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line4;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to Amplitude Mapper - Mapping of the Nordic format to Amplitude entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = GeneralHelper.class)
public abstract class NordicToAmplitudeMapper {

    /** Mapper instance. */
    public static NordicToAmplitudeMapper INSTANCE = Mappers.getMapper(NordicToAmplitudeMapper.class);

    /**
     * Mapping of properties for the Amplitude entity from the Nordic format to QuakeML version 2.0.
     * The properties providing data for the Amplitude entity are mainly found within Line4 of the Nordic format,
     * but creation of publicID also requires information from Line1 of the Nordic format.
     *
     * @param line4 Line4 object
     * @param line1 Line1 object
     * @return Amplitude
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // RealQuantity (Object) - Converted to proper unit by AfterMapping
            @Mapping(target = "genericAmplitude.value", source="line4.amplitude",  qualifiedBy = StringToDouble.class),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // String
            @Mapping(target = "type", source="line4.phaseID"),
            // AmplitudeUnit (Enum) - Set by AfterMapping
            @Mapping(target = "unit", ignore = true),
            // AmplitudeCategory (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "category", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "period.value", source="line4.periodSeconds",  qualifiedBy = StringToDouble.class),
            // TimeWindow (Object) - NO MAPPING DETERMINED
            @Mapping(target = "timeWindow", ignore = true),
            // WaveformStreamID (Object)
            // - Full ChannelCode (instrumentType + "?" + component) is built in AfterMapping
            @Mapping(target = "waveformID.stationCode", source="line4.stationName"),
            @Mapping(target = "waveformID.channelCode", source="line4.instrumentType"),
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
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true)
    })
    public abstract Amplitude mapLine4ToAmplitude(Line4 line4, Line1 line1, Pick pick);


    /*
     * After Mappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the amplitude object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param amp The amplitude object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPublicID(@MappingTarget Amplitude amp, Line1 line1, Line4 line4) {
        amp.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4.getStationName() != null ? line4.getStationName() : line1.getHypoCenterRepAgency()),
                Amplitude.class));
    }

    @AfterMapping
    protected void setCodaValue(@MappingTarget Amplitude amp, Line4 line4, Pick pick) {

        // Check if CODA value exists
        if (StringUtils.isNotBlank(line4.getDuration())) {
            Double codaValue = Double.parseDouble(line4.getDuration());

            // Check if no amplitude is given in s-file
            if (StringUtils.isBlank(line4.getAmplitude())) {
                // Check if time exists in Pick entity
                if (pick.getTime() != null) {
                    TimeQuantity tq = pick.getTime();
                    if (tq.getValue() != null) {
                        // Calculate  value (phase time + coda/duration time)
                        //Long codaSecVal = DoubleMath.roundToLong(codaValue, RoundingMode.HALF_UP);
                        //LocalDateTime localDateTime = LocalDateTime.parse(tq.getValue());
                        //localDateTime.plusSeconds(codaSecVal);

                        // Set value
                        RealQuantity ampQuantity = new RealQuantity();
                        ampQuantity.setValue(codaValue);
                        amp.setGenericAmplitude(ampQuantity);
                        // Set Type
                        amp.setType("END");
                        // Set category
                        amp.setCategory(AmplitudeCategory.DURATION);
                        // Set unit
                        amp.setUnit(AmplitudeUnit.Seconds);
                    }
                }
            }
        }
    }

    /**
     * AfterMapping - Handle long phase names, which can be up to 8 characters in column 11-18 in the Nordic format.
     *
     * @param amp The amplitude object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void setExtendedPhaseIds(@MappingTarget Amplitude amp, Line4 line4) {
        String wInd = line4.getWeightingIndicator();
        if (wInd != null) {
            if (line4.getPhaseID() != null && wInd.length() > 0 && CharacterChecker.noNumbers(wInd)) {
                String newPhaseId = PhaseIDSetter.getExtentedPhaseID(line4);
                if (!StringUtils.isBlank(newPhaseId)) {
                    amp.setType(newPhaseId.trim());
                }
            }
        }
    }

    /**
     * AfterMapping - Building the channel code. The Nordic format does not have a channel code,
     * so it will be created based on the instrument type and the component. Since QuakeML channel code
     * normally have 3 characters, and the respective instrument type and component have 1 char each,
     * another character, ? in this case, will be placed in the middle.
     *
     * @param amp The amplitude object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildChannelCode(@MappingTarget Amplitude amp, Line4 line4) {
        if (amp.getWaveformID().getChannelCode() != null) {
            String chanCode =
                    line4.getInstrumentType()
                    + "?" +
                    line4.getComponent();
            amp.getWaveformID().setChannelCode(chanCode);
        }
    }

    /**
     * AfterMapping - Setting the amplitude unit and convert the value to respective unit.
     * The Nordic format hold different amplitude types, but normally uses nm and nm/s,
     * while QuakeML uses m and m/s for these two. QuakeML also have other amplitude types as given by {@link AmplitudeUnit}.
     * The amplitude types in the nordic format are identified by the phaseID, and conversion is done to match
     * the QuakeML units.
     *
     * @param amp The amplitude object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void setAmpUnitAndConvertVal(@MappingTarget Amplitude amp, Line4 line4) {
        if (line4.getPhaseID() != null && amp.getGenericAmplitude() != null) {
            String phaseID = line4.getPhaseID();
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

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link RealQuantity},
     * {@link WaveformStreamID}, etc.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param amp The amplitude object that were build in the initial mapping.
     */
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
