package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.EnumsQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.TimeQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.TimeHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.helpers.EnumsHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.EarthModelIdSetter;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.OriginType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineE;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

/**
 * Nordic format to Origin Mapper - Mapping of the Nordic format to Origin entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                GeneralHelper.class,
                EnumsHelper.class,
                TimeHelper.class
        })
public abstract class NordicToOriginMapper {

    /** Mapper instance. */
    public static NordicToOriginMapper INSTANCE = Mappers.getMapper(NordicToOriginMapper.class);

    /**
     * Mapping of all properties for the Origin entity from the type 1 and E lines within Nordic format to QuakeML version 2.0.
     *
     * @param line1 Line1 object
     * @param lineE LineE object
     * @return Origin
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),

            // TimeQuantity (Object)
            @Mapping(target = "time.value", source = "line1", qualifiedBy = TimeQualifiers.Line1ToIsoTime.class),
            @Mapping(target = "time.uncertainty", source="lineE.originTimeError", qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // RealQuantity (Object)
            @Mapping(target = "latitude.value", source="line1.latitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "latitude.uncertainty", source="lineE.latitudeError",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "longitude.value", source="line1.longitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "longitude.uncertainty", source="lineE.longitudeError",  qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // CompositeTime (List) - Set in AfterMapping
            @Mapping(target = "compositeTime", ignore = true),

            // RealQuantity (Object)
            @Mapping(target = "depth.value", source="line1.depth",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "depth.uncertainty", source="lineE.depthError",  qualifiedBy = GeneralQualifiers.StringToDouble.class ),

            // OriginType (Enum) - Set om AfterMapping
            @Mapping(target = "type", ignore = true),

            // OriginDepthType (Enum)
            @Mapping(target = "depthType", source = "line1.depthIndicator", qualifiedBy = EnumsQualifiers.StringToOriginDepthType.class),

            // Boolean
            @Mapping(target = "epicenterFixed", source = "line1.locIndicator", qualifiedBy = GeneralQualifiers.StringToBoolean.class),
            // Boolean
            @Mapping(target = "timeFixed", source = "line1.fixOfTime", qualifiedBy = GeneralQualifiers.StringToBoolean.class),

            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "earthModelID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "referenceSystemID", ignore = true),

            // Quality (Object)
            @Mapping(target = "quality.associatedStationCount", source = "line1.numStationsUsed", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            @Mapping(target = "quality.azimuthalGap", source = "lineE.gap", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "quality.standardError", source = "line1.rmsTimeResiduals", qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // String - NO MAPPING DETERMINED
            @Mapping(target = "region", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),

            // CreationInfo (Object) - Set in AfterMapping
            @Mapping(target = "creationInfo", ignore = true)
    })
    public abstract Origin mapFullOrigin(Line1 line1, LineE lineE);

    /**
     * Mapping of properties for the Origin entity from only type 1 lines (type E line is missing) within Nordic format to QuakeML version 2.0.
     *
     * @param line1 Line1 object
     * @return Origin
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // TimeQuantity (Object)
            @Mapping(target = "time.value", source = "line1", qualifiedBy = TimeQualifiers.Line1ToIsoTime.class),
            // RealQuantity (Object)
            @Mapping(target = "latitude.value", source="line1.latitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "longitude.value", source="line1.longitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // CompositeTime (List)
            @Mapping(target = "compositeTime", ignore = true),

            // RealQuantity (Object)
            @Mapping(target = "depth.value", source="line1.depth",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // OriginType (Enum) - Set om AfterMapping
            @Mapping(target = "type", ignore = true),
            // OriginDepthType (Enum)
            @Mapping(target = "depthType", source = "line1.depthIndicator", ignore = true),

            // Boolean - Set in AfterMapping
            @Mapping(target = "epicenterFixed", source = "line1.locIndicator", ignore = true),
            // Boolean - Set in AfterMapping
            @Mapping(target = "timeFixed", source = "line1.fixOfTime", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "methodID", ignore = true),
            // String - Set in AfterMapping
            @Mapping(target = "earthModelID", ignore = true),
            // String - NO MAPPING DETERMINED
            @Mapping(target = "referenceSystemID", ignore = true),

            // Quality (Object)
            @Mapping(target = "quality.associatedStationCount", source = "line1.numStationsUsed", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            @Mapping(target = "quality.standardError", source = "line1.rmsTimeResiduals", qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // String - NO MAPPING DETERMINED
            @Mapping(target = "region", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum)- NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),

            // CreationInfo (Object) - Set in AfterMapping
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Origin mapL1Origin(Line1 line1);

    /*
     * After Mappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the origin object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param org The Origin object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPublicID(@MappingTarget Origin org, Line1 line1) {
        org.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                line1.getHypoCenterRepAgency(),
                Origin.class));
    }

    /**
     * AfterMapping - Creation of the CompositeTime object and its relation as a list to the Origin object.
     *
     * @param org The Origin object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void setCompositeTime(@MappingTarget Origin org, Line1 line1) {
        List<CompositeTime> times = new ArrayList<>();
        CompositeTime compositeTime = NordicToCompositeTimeMapper.INSTANCE.mapOriginCompositeTime(line1);
        if (compositeTime != null) {
            // Check if the mapped object has a year attribute, and only add the object to Origin if true
            if (compositeTime.getYear() != null) {
                times.add(compositeTime);
                org.setCompositeTime(times);
            }
        }
    }

    // Convert Nordic Depth unit of km to Quakeml m unit
    @AfterMapping
    protected void fixDepthUnit(@MappingTarget Origin org) {
        if (org.getDepth() != null) {
            if (org.getDepth().getValue() != null) {
                Double valKm = org.getDepth().getValue();
                Double valInMeter = valKm * 1000;
                org.getDepth().setValue(valInMeter);
            }
        }
    }

    /**
     * AfterMapping - Set enum type to hypocenter. Should always be the case in Nordic to QuakeML mapping.
     *
     * @param org The Origin object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setOriginType(@MappingTarget Origin org) {
        org.setType(OriginType.HYPOCENTER);
    }

    @AfterMapping
    protected void setDepthType(@MappingTarget Origin org, Line1 line1) {
        if (line1.getDepthIndicator() != null) {
            switch (line1.getDepthIndicator()) {
                case "F":
                    org.setDepthType(OriginDepthType.OPERATOR_ASSIGNED);
                    break;
                case "S":
                    // do nothing
                    break;
                default:
                    org.setDepthType(OriginDepthType.FROM_LOCATION);
                    break;
            }
        }
    }

    @AfterMapping
    protected void setEpicenterFixed(@MappingTarget Origin org, Line1 line1) {
        if (line1.getLocIndicator() != null) {
            switch (line1.getLocIndicator()) {
                case "F":
                    org.setEpicenterFixed(true);
                    break;
                case "S":
                    // Do nothing
                    break;
                case "*":
                    org.setDepthType(OriginDepthType.OPERATOR_ASSIGNED);
                    org.setTimeFixed(true);
                    org.setEpicenterFixed(true);
                    break;
                default:
                    org.setEpicenterFixed(false);
                    break;
            }
        }
    }

    @AfterMapping
    protected void setTimeFixed(@MappingTarget Origin org, Line1 line1) {
        if (line1.getFixOfTime() != null) {
            switch (line1.getFixOfTime()) {
                case "F":
                    org.setTimeFixed(true);
                    break;
                default:
                    org.setTimeFixed(false);
                    break;
            }
        }
    }

    /**
     * AfterMapping - Reference to the model used and marker for model is found within Line1 of the Nordic format.
     * Uses {@link EarthModelIdSetter} class to set the model ID.
     *
     * @param org The Origin object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void setEarthModelId(@MappingTarget Origin org, Line1 line1) {
        if (line1.getDistanceIndicator() != null) {
            EarthModelIdSetter<Origin> earthModelIdSetter= new EarthModelIdSetter<>();
            earthModelIdSetter.setID(line1, org);
        }
    }

    /**
     * AfterMapping - Set the creation information object.
     * Uses {@link CreationInfo} class.
     *
     * @param org The Origin object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void setCreationInfo(@MappingTarget Origin org, Line1 line1) {
        if (line1.getHypoCenterRepAgency() != null) {
            CreationInfo creationInfo = new CreationInfo();
            creationInfo.setAgencyID(line1.getHypoCenterRepAgency());
            org.setCreationInfo(creationInfo);
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link TimeQuantity} and
     * {@link RealQuantity}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param org The Origin object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget Origin org) {
        // time (TimeQuantity)
        if (ChildChecker.isTimeQuantityNull(org.getTime())) {
            org.setTime(null);
        }
        // latitude (RealQuantity)
        if (ChildChecker.isRealQuantityNull(org.getLatitude())) {
            org.setLatitude(null);
        }
        // longitude (RealQuantity)
        if (ChildChecker.isRealQuantityNull(org.getLongitude())) {
            org.setLongitude(null);
        }
        // depth (RealQuantity)
        if (ChildChecker.isRealQuantityNull(org.getDepth())) {
            org.setDepth(null);
        }
    }

}
