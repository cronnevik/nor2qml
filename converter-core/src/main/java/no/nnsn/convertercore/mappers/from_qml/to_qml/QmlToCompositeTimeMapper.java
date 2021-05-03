package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CompositeTimeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for CompositeTime Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class QmlToCompositeTimeMapper {

    public static QmlToCompositeTimeMapper INSTANCE = Mappers.getMapper(QmlToCompositeTimeMapper.class);

    /**
     * Mapping of properties for CompositeTime Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The CompositeTime object has a many-to-one relation with the Origin object.
     * The relation is set in the parent (Origin) object, thus ignored in the current mapping.
     *
     * @param compositeTime CompositeTime object of QuakeML version 2.0
     * @return CompositeTimeDto object of QuakeML version 1.2.
     */
    @Mappings({
            @Mapping(target = "origin", ignore = true)
    })
    public abstract CompositeTimeDto mapV12CompositeTime(CompositeTime compositeTime);

    /**
     * Inverse mapping of properties for CompositeTime Entity.
     * The many-to-one relationship is set in the parent (Origin) object, thus ignored in the mapping.
     *
     * @param compositeTimeDto CompositeTimeDto object of QuakeML version 1.2
     * @return CompositeTime object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "origin", ignore = true)
    })
    public abstract CompositeTime mapV20CompositeTime(CompositeTimeDto compositeTimeDto);
}
