package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers.*;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.EarthModelIdSetter;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.PhaseIDSetter;
import no.nnsn.convertercore.mappers.utils.CharacterChecker;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to Arrival Mapper - Mapping of the Nordic format to Arrival entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = GeneralHelper.class)
public abstract class NordicToArrivalMapper {

    /** Mapper instance. */
    public static NordicToArrivalMapper INSTANCE = Mappers.getMapper(NordicToArrivalMapper.class);

    /**
     * Mapping of properties for the Arrival entity from the Nordic format to QuakeML version 2.0.
     * The properties providing data for the Arrival entity are mainly found within Line4 of the Nordic format,
     * but creation of publicID and mapping of earthModelID also requires information from Line1 of the Nordic format.
     *
     * @param line4 Line4 object
     * @param line1 Line1 object
     * @return Arrival
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // String - Pointer to related Pick object - Set by ...########### TODO
            @Mapping(target = "pickID", ignore = true),
            // String
            @Mapping(target = "phase", source = "line4.phaseID"),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "timeCorrection", ignore = true),
            // Double
            @Mapping(target = "azimuth", source="line4.azimuthAtSource",  qualifiedBy = StringToDouble.class),
            // Double - Unit conversion set in AfterMapping
            @Mapping(target = "distance", source="line4.epicentralDistance",  qualifiedBy = StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "takeoffAngle.value", source="line4.angleOfIncidence",  qualifiedBy = StringToDouble.class),
            // Double
            @Mapping(target = "timeResidual", source="line4.travelTimeResidual",  qualifiedBy = StringToDouble.class),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "horizontalSlownessResidual", ignore = true),
            // Double
            @Mapping(target = "backazimuthResidual", source="line4.backAzimuthResidual",  qualifiedBy = StringToDouble.class),
            // Double - Set by AfterMapping
            @Mapping(target = "timeWeight", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "horizontalSlownessWeight", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "backazimuthWeight", ignore = true),
            // String - Set by AfterMapping
            @Mapping(target = "earthModelID", source="line1.distanceIndicator"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Arrival mapLine4ToArrival(Line4 line4, Line1 line1);

    /*
     * After Mappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the arrival object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param arrival The arrival object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPublicID(@MappingTarget Arrival arrival, Line1 line1, Line4 line4) {
        arrival.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4.getStationName() != null ? line4.getStationName() : line1.getHypoCenterRepAgency()),
                Arrival.class));
    }

    /**
     * AfterMapping - Distance in the Nordic format is expressed in kilometre (km), while distance in QuakeML is
     * expressed by degrees. Conversion of the already mapped property is thus needed for km to deg unit.
     *
     * @param arrival The arrival object that were build in the initial mapping.
     */
    @AfterMapping
    protected void distanceToKm(@MappingTarget Arrival arrival) {
        if (arrival.getDistance() != null) {
            Double dist = arrival.getDistance();
            // Convert from km to degrees
            arrival.setDistance(dist / 111.2);
        }
    }

    /**
     * AfterMapping - Handle long phase names, which can be up to 8 characters in column 11-18 in the Nordic format.
     *
     * @param arrival The arrival object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void setExtendedPhaseIds(@MappingTarget Arrival arrival, Line4 line4) {
        String wInd = line4.getWeightingIndicator();
        if(wInd != null) {
            if (line4.getPhaseID() != null && wInd.length() > 0 && CharacterChecker.noNumbers(wInd)) {
                String newPhaseId = PhaseIDSetter.getExtentedPhaseID(line4);
                if (!StringUtils.isBlank(newPhaseId)) {
                    arrival.setPhase(newPhaseId.trim());
                }
            }
        }
    }

    /**
     * AfterMapping - The Nordic format typically store weighting in numbers (integer) of 1-4 representing percentage (0-100%),
     * while QuakeML has a floating value of 0.0 up to 1.0 for this purpose.
     *
     * @param arrival The arrival object that were build in the initial mapping.
     * @param line4 Line4 object passed to the mapper.
     */
    @AfterMapping
    protected void setWeighIndicator(@MappingTarget Arrival arrival, Line4 line4) {
        String weight = line4.getWeightingIndicator();
        String altWeight = line4.getFreeOrWeight();

        String value = null;

        if (weight != null && (StringUtils.isNumeric(weight) || weight.trim().isEmpty())) {
            value = weight;
        } else if (altWeight != null && !altWeight.isEmpty()) {
            value = altWeight;
        }

        if (value != null) {
            switch (value) {
                case "0":
                    arrival.setTimeWeight(1.0);
                    break;
                case "1":
                    arrival.setTimeWeight(0.75);
                    break;
                case "2":
                    arrival.setTimeWeight(0.5);
                    break;
                case "3":
                    arrival.setTimeWeight(0.25);
                    break;
                case "4":
                    arrival.setTimeWeight(0.0);
                    break;
                case "":
                    arrival.setTimeWeight(1.0);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * AfterMapping - Reference to the model used and marker for model is found within Line1 of the Nordic format.
     * Uses {@link EarthModelIdSetter} class to set the model ID.
     *
     * @param arrival The arrival object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void setEarthModelID(@MappingTarget Arrival arrival, Line1 line1) {
        if (line1.getDistanceIndicator() != null) {
            EarthModelIdSetter<Arrival> earthModelIdSetter= new EarthModelIdSetter<>();
            earthModelIdSetter.setID(line1, arrival);
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param arrival The amplitude object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget Arrival arrival) {
        // takeoffAngle (RealQuantity)
        if (ChildChecker.isRealQuantityNull(arrival.getTakeoffAngle())) {
            arrival.setTakeoffAngle(null);
        }
    }
}
