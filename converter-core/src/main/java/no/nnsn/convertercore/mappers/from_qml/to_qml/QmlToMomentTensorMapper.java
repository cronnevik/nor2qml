package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.convertercore.mappers.from_qml.to_qml.annotations.QuakeMLQualifiers;
import no.nnsn.convertercore.mappers.from_qml.to_qml.helpers.QuakeMLHelper;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.MomentTensorDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.MomentTensor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for MomentTensor Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QuakeMLHelper.class,
                QmlToCommentMapper.class
        })
public abstract class QmlToMomentTensorMapper {

    public static QmlToMomentTensorMapper INSTANCE = Mappers.getMapper(QmlToMomentTensorMapper.class);

    /**
     * Mapping of properties for MomentTensor Entity from QuakeML version 2.0 to QuakeML version 1.2.
     *
     * @param momentTensor MomentTensor object of QuakeML version 2.0
     * @return MomentTensorDto object of QuakeML version 1.2.
     */
    @Mappings({
            @Mapping(target = "variance", source = "variance", qualifiedBy = QuakeMLQualifiers.RealQuantityToDouble.class),
            @Mapping(target = "varianceReduction", source = "varianceReduction", qualifiedBy = QuakeMLQualifiers.RealQuantityToDouble.class),
            @Mapping(target = "doubleCouple", source = "doubleCouple", qualifiedBy = QuakeMLQualifiers.RealQuantityToDouble.class),
            @Mapping(target = "clvd", source = "clvd", qualifiedBy = QuakeMLQualifiers.RealQuantityToDouble.class),
            @Mapping(target = "iso", source = "iso", qualifiedBy = QuakeMLQualifiers.RealQuantityToDouble.class),
            @Mapping(target = "focalMechanism", ignore = true)
    })
    public abstract MomentTensorDto mapV12MomentTensor(MomentTensor momentTensor);

    /**
     * Inverse mapping of properties for MomentTensor Entity.
     *
     * @param momentTensorDto MomentTensorDto object of QuakeML version 1.2
     * @return MomentTensor object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "variance", source = "variance", qualifiedBy = QuakeMLQualifiers.DoubleToRealQuantity.class),
            @Mapping(target = "varianceReduction", source = "varianceReduction", qualifiedBy = QuakeMLQualifiers.DoubleToRealQuantity.class),
            @Mapping(target = "doubleCouple", source = "doubleCouple", qualifiedBy = QuakeMLQualifiers.DoubleToRealQuantity.class),
            @Mapping(target = "clvd", source = "clvd", qualifiedBy = QuakeMLQualifiers.DoubleToRealQuantity.class),
            @Mapping(target = "iso", source = "iso", qualifiedBy = QuakeMLQualifiers.DoubleToRealQuantity.class),
            @Mapping(target = "focalMechanism", ignore = true)
    })
    public abstract MomentTensor mapV20MomentTensor(MomentTensorDto momentTensorDto);

}
