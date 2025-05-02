package no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.EarthModelIdSetter;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyIdType;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v2.lines.Line4Dto;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = GeneralHelper.class)
public abstract class NordicDtoToArrivalMapper {

    public static NordicDtoToArrivalMapper INSTANCE = Mappers.getMapper(NordicDtoToArrivalMapper.class);

    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // String - Pointer to related Pick object - Set by class calling the mapper
            @Mapping(target = "pickID", ignore = true),
            // String
            @Mapping(target = "phase", source = "line4Dto.phaseID"),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "timeCorrection", ignore = true),
            // Double
            @Mapping(target = "azimuth", source="line4Dto.azimuthAtSource",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // Double - Unit conversion set in AfterMapping
            @Mapping(target = "distance", source="line4Dto.epicentralDistance",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "takeoffAngle.value", source="line4Dto.angleOfIncidence",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // Double - Can be either travel time, back azimuth or magnitude - Set in AfterMapping
            @Mapping(target = "timeResidual", source="line4Dto.residual",  ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "horizontalSlownessResidual", ignore = true),
            // Double - Set in AfterMapping
            @Mapping(target = "backazimuthResidual", ignore = true),
            // Double - Set by AfterMapping
            @Mapping(target = "timeWeight", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "horizontalSlownessWeight", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "backazimuthWeight", ignore = true),
            // String - Set by AfterMapping
            @Mapping(target = "earthModelID", source="line1.distanceIndicator", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source = "line4Dto.agency"),
            @Mapping(target = "creationInfo.author", source = "line4Dto.operator"),
    })
    public abstract Arrival mapLine4DtoToArrival(Line4Dto line4Dto, Line1 line1);

    @AfterMapping
    protected void buildPublicID(@MappingTarget Arrival arrival, Line1 line1, Line4Dto line4Dto) {
        arrival.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                (line4Dto.getStationName() != null ? line4Dto.getStationName() : line1.getHypoCenterRepAgency()),
                Arrival.class));
    }

    @AfterMapping
    protected void epicentralDistanceToKm(@MappingTarget Arrival arrival) {
        if (arrival.getDistance() != null) {
            Double dist = arrival.getDistance();
            // Convert from km to degrees
            arrival.setDistance(dist / 111.2);
        }
    }

    @AfterMapping
    protected void setResidual(@MappingTarget Arrival arrival, Line4Dto line4Dto) {
        String residual = line4Dto.getResidual();
        Double residualValue = residual != null && !residual.isEmpty() ? Double.parseDouble(residual) : null;

        // Can be either travel time, back azimuth or magnitude
        ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(line4Dto.getParameterOne(), line4Dto.getPhaseID());

        // Set back azimuth residual
        if (pOneType == ParameterOneType.BACK_AZIMUTH) {
            if (residualValue != null) {
                arrival.setBackazimuthResidual(residualValue);
            }
        }

        // Magnitude - END phases might have a magnitude residual
        if (pOneType == ParameterOneType.DURATION) {
            // TODO
        }

        // Set travel time (Phases - not amplitude, BAZ or END)
        if (pOneType != ParameterOneType.AMPLITUDE || pOneType != ParameterOneType.BACK_AZIMUTH || pOneType != ParameterOneType.DURATION) {
            if (residualValue != null) {
                arrival.setTimeResidual(residualValue);
            }
        }
    }

    @AfterMapping
    protected void setWeight(@MappingTarget Arrival arrival, Line4Dto line4Dto) {
        if (line4Dto.getWeight() != null) {
            String[] weightSplit = line4Dto.getWeight().split("");
            String weight = "";
            if (weightSplit.length > 1) {
                weight = weightSplit[0] + "." + weightSplit[1];
            } else {
                weight = "0." + line4Dto.getWeight().trim();
            }

            Comment comment = new Comment();
            comment.setId(PropertyIdType.PROPERTY_WEIGHT.getPropertyIdtype());
            comment.setText(weight);

            if (arrival.getComment() != null) {
                arrival.getComment().add(comment);
            } else {
                List<Comment> commentList = new ArrayList<>();
                commentList.add(comment);
                arrival.setComment(commentList);
            }
        }
    }

    @AfterMapping
    protected void setWeighIndicator(@MappingTarget Arrival arrival, Line4Dto line4Dto) {
        String weight = line4Dto.getWeightingIndicator();

        if (weight != null && (StringUtils.isNumeric(weight) || weight.trim().isEmpty())) {
            Double dNordicWeight = Double.parseDouble(weight);
            // Nordic (05) => QuakeML (0.5)
            Double dQmlWeight = dNordicWeight / 10;
            arrival.setTimeWeight(dQmlWeight);
        }

    }

    @AfterMapping
    protected void setEarthModelID(@MappingTarget Arrival arrival, Line1 line1) {
        if (line1.getDistanceIndicator() != null) {
            EarthModelIdSetter<Arrival> earthModelIdSetter= new EarthModelIdSetter<>();
            earthModelIdSetter.setID(line1, arrival);
        }
    }

}
