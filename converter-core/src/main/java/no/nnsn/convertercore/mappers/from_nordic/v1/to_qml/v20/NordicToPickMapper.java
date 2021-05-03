package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.EnumsQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.helpers.EnumsHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.PhaseIDSetter;
import no.nnsn.convertercore.mappers.utils.CharacterChecker;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Nordic format to Pick Mapper - Mapping of the Nordic format to Pick entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                GeneralHelper.class,
                EnumsHelper.class
        })
public abstract class NordicToPickMapper {

    /** Mapper instance. */
    public static NordicToPickMapper INSTANCE = Mappers.getMapper(NordicToPickMapper.class);

    private static DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Mapping of properties for the Pick entity from the Nordic format to QuakeML version 2.0.
     * The properties providing data for the Pick entity are mainly found within Line4 of the Nordic format,
     * but creation of publicID and time also requires information from Line1 of the Nordic format.
     *
     * @param line4 Line4 object
     * @param line1 Line1 object
     * @return Pick
     */
    @Mappings({
            // String (ResourceReference) - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // TimeQuantity (Object) - Set by AfterMapping (String -> Object)
            @Mapping(target = "time", ignore = true),
            // WaveformStreamID (Object) - Full ChannelCode (instrumentType + "?" + component) is built in AfterMapping
            @Mapping(target = "waveformID.stationCode", source="line4.stationName"),
            @Mapping(target = "waveformID.channelCode", source="line4.instrumentType"),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "filterID", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "backazimuth.value", source = "line4.directionDegrees", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object) - Unit set in AfterMapping
            @Mapping(target = "horizontalSlowness.value", source = "line4.phaseVelocity", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // PickOnset (Enum)
            @Mapping(target = "onset", source = "line4.qualityIndicator", qualifiedBy = EnumsQualifiers.StringToPickOnset.class),
            // String (ResourceReference)
            @Mapping(target = "phaseHint", source="line4.phaseID"),
            // PickPolarity (Enum)
            @Mapping(target = "polarity", source = "line4.firstMotion", qualifiedBy = EnumsQualifiers.StringToPickPolarity.class),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true)
    })
    public abstract Pick mapLine4ToPick(Line4 line4, Line1 line1);


    /*
     * AfterMappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the pick object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param pick The pick object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPublicID(@MappingTarget Pick pick, Line1 line1, Line4 line4) {
        pick.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4.getStationName() != null ? line4.getStationName() : line1.getHypoCenterRepAgency()),
                Pick.class));
    }

    /**
     * AfterMapping - Creation of the phase time represented by an ISO string value.
     *
     * @param pick The pick object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPhaseTimeIsoString(@MappingTarget Pick pick, Line1 line1, Line4 line4) {
        int year = line1.getYear() != null && !line1.getYear().isEmpty() ? Integer.parseInt(line1.getYear()) : 0;
        int month = line1.getMonth() != null && !line1.getMonth().isEmpty() ? Integer.parseInt(line1.getMonth()) : 0;
        int day = line1.getDay() != null && !line1.getDay().isEmpty() ? Integer.parseInt(line1.getDay()) : 0;

        // Use Line4 for specific time of phase
        int hour = line4.getHour() != null && !line4.getHour().isEmpty() ? Integer.parseInt(line4.getHour()) : 0;
        int minute = line4.getMinutes() != null && !line4.getMinutes().isEmpty() ? Integer.parseInt(line4.getMinutes()) : 0;
        int second = 0;
        int nanoSec = 0;

        String secTemp = line4.getSeconds();
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

    /**
     * AfterMapping - Building the channel code. The Nordic format does not have a channel code,
     * so it will be created based on the instrument type and the component. Since QuakeML channel code
     * normally have 3 characters, and the respective instrument type and component have 1 char each,
     * another character, ? in this case, will be placed in the middle.
     *
     * @param pick The pick object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildChannelCode(@MappingTarget Pick pick, Line4 line4) {
        if (pick.getWaveformID().getChannelCode() != null) {
            String chanCode =
                    line4.getInstrumentType()
                            + "?" +
                            line4.getComponent();
            pick.getWaveformID().setChannelCode(chanCode);
        }
    }

    /**
     * AfterMapping - Handle long phase names, which can be up to 8 characters in column 11-18 in the Nordic format.
     *
     * @param pick The pick object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void setExtendedPhaseIds(@MappingTarget Pick pick, Line4 line4) {
        String wInd = line4.getWeightingIndicator();
        if(wInd != null) {
            if (line4.getPhaseID() != null && wInd.length() > 0 && CharacterChecker.noNumbers(wInd)) {
                String newPhaseId = PhaseIDSetter.getExtentedPhaseID(line4);
                if (!StringUtils.isBlank(newPhaseId)) {
                    pick.setPhaseHint(newPhaseId.trim());
                }
            }
        }
    }

    @AfterMapping
    protected void convertHorizontalSlownessUnit(@MappingTarget Pick pick) {
        if (pick.getHorizontalSlowness() != null) {
            RealQuantity horSlowRq = pick.getHorizontalSlowness();
            if (horSlowRq.getValue() != null && horSlowRq.getValue() != 0.0) {
                if (horSlowRq.getValue() == 1.0) {
                    pick.getHorizontalSlowness().setValue(111.2);
                } else {
                    Double horSlow = horSlowRq.getValue();
                    Double newHorSlow = (horSlow * 111.2);
                    final double roundedVal = Math.round(newHorSlow * 100.0) / 100.0;
                    pick.getHorizontalSlowness().setValue(roundedVal);
                }
            }
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity},
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID} and
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param pick The Pick object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget Pick pick, Line4 line4) {
        // time (TimeQuantity)
        if (ChildChecker.isTimeQuantityNull(pick.getTime())) {
            pick.setTime(null);
        }

        // waveformID (WaveformStreamID)
        if (ChildChecker.isWaveformStreamIdNull(pick.getWaveformID())) {
            pick.setWaveformID(null);
        }

        // backazimuth (RealQuantity)
        if (ChildChecker.isRealQuantityNull(pick.getBackazimuth())) {
            pick.setBackazimuth(null);
        }

        /* horizontalSlowness (RealQuantity)
        if (ChildChecker.isRealQuantityNull(pick.getHorizontalSlowness())) {
            pick.setHorizontalSlowness(null);
        } */
    }
}
