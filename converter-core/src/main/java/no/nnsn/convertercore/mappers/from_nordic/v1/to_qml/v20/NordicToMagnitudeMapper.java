package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers.*;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to Magnitude Mapper - Mapping of the Nordic format to Magnitude entity in QuakeML 2.0.
 * Line 1 type of the Nordic format may hold up to 3 magnitudes adn hence mapping needs to be done accordingly for each.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = GeneralHelper.class)
public abstract class NordicToMagnitudeMapper {

    /** Mapper instance. */
    public static NordicToMagnitudeMapper INSTANCE = Mappers.getMapper(NordicToMagnitudeMapper.class);

    /**
     * Mapping of properties for the Magnitude entity (Mag 1) from the type 1 within Nordic format to QuakeML version 2.0.
     * The origin object from QuakeML v2.0 itself is needed to link the originID to each magnitude.
     *
     * @param line1 Line1 object
     * @param org Origin object
     * @return FocalMechanism
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "mag.value", source="line1.magOneNo",  qualifiedBy = StringToDouble.class),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // String
            @Mapping(target = "type", source="line1.magOneType"),
            // String - Pointer to relating Origin Object
            @Mapping(target = "originID", source = "org.publicID"),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // Integer - NO MAPPING DETERMINED
            @Mapping(target = "stationCount", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "azimuthalGap", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source="line1.magOneRepAgency")
    })
    public abstract Magnitude mapLine1toMagnitude1(Line1 line1, Origin org);

    /**
     * Mapping of properties for the Magnitude entity (Mag 2) from the type 1 within Nordic format to QuakeML version 2.0.
     * The origin object from QuakeML v2.0 itself is needed to link the originID to each magnitude.
     *
     * @param line1 Line1 object
     * @param org Origin object
     * @return FocalMechanism
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "mag.value", source="line1.magTwoNo",  qualifiedBy = StringToDouble.class),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // String
            @Mapping(target = "type", source="line1.magTwoType"),
            // String - Pointer to relating Origin Object
            @Mapping(target = "originID", source = "org.publicID"),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // Integer - NO MAPPING DETERMINED
            @Mapping(target = "stationCount", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "azimuthalGap", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source="line1.magTwoRepAgency"),
    })
    public abstract Magnitude mapLine1toMagnitude2(Line1 line1, Origin org);

    /**
     * Mapping of properties for the Magnitude entity (Mag 3) from the type 1 within Nordic format to QuakeML version 2.0.
     * The origin object from QuakeML v2.0 itself is needed to link the originID to each magnitude.
     *
     * @param line1 Line1 object
     * @param org Origin object
     * @return FocalMechanism
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "mag.value", source="line1.magThreeNo",  qualifiedBy = StringToDouble.class),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // String
            @Mapping(target = "type", source="line1.magThreeType"),
            // String - Pointer to relating Origin Object
            @Mapping(target = "originID", source = "org.publicID"),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // Integer - NO MAPPING DETERMINED
            @Mapping(target = "stationCount", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "azimuthalGap", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source="line1.magThreeRepAgency"),
    })
    public abstract Magnitude mapLine1toMagnitude3(Line1 line1, Origin org);

    /*
     * After Mappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the magnitude object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param mag The Magnitude object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void setMagPublicID(@MappingTarget Magnitude mag, Line1 line1) {
        mag.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                line1.getHypoCenterRepAgency(),
                Magnitude.class));
    }

    @AfterMapping
    protected void convertMagnitudeType(@MappingTarget Magnitude mag) {
        if (mag.getType() != null) {
            String typeValue = "";
            switch (mag.getType()) {
                case "L":
                    typeValue = "ML";
                    break;
                case "b":
                    typeValue = "mb";
                    break;
                case "B":
                    typeValue = "mB";
                    break;
                case "s":
                    typeValue = "Ms";
                    break;
                case "S":
                    typeValue = "MS";
                    break;
                case "W":
                    typeValue = "MW";
                    break;
                case "G":
                    typeValue = "MbLg";
                    break;
                case "C":
                    typeValue = "Mc";
                    break;
                case "N":
                    typeValue = "MN";
                    break;
                case "w":
                    typeValue = "Mw";
                    break;
            }
            mag.setType(typeValue);
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link RealQuantity}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param mag The Magnitude object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget Magnitude mag) {
        // mag (RealQuantity)
        if (ChildChecker.isRealQuantityNull(mag.getMag())) {
            mag.setMag(null);
        }

        if (ChildChecker.isCreationInfoNull(mag.getCreationInfo())) {
            mag.setCreationInfo(null);
        }


    }
}
