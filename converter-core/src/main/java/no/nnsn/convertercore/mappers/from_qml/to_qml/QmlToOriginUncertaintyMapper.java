package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.OriginUncertaintyDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.OriginUncertainty;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for OriginUncertainty Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class QmlToOriginUncertaintyMapper {

    public static QmlToOriginUncertaintyMapper INSTANCE = Mappers.getMapper(QmlToOriginUncertaintyMapper.class);

    /**
     * Mapping of properties for OriginUncertainty Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The OriginUncertainty object has a one-to-one relation the Origin object.
     * The relation is set in the parent (Origin) object and thus the mapping is ignored.
     *
     * @param originUncertainty OriginUncertainty object of QuakeML version 2.0
     * @return OriginUncertaintyDto object of QuakeML version 1.2.
     */
    public abstract OriginUncertaintyDto mapV12OriginUncertainty(OriginUncertainty originUncertainty);

    /**
     * Inverse mapping of properties for OriginUncertainty Entity.
     * Relation set in the parent object (Origin) is ignored in the mapping.
     *
     * @param originUncertaintyDto OriginUncertaintyDto object of QuakeML version 1.2
     * @return OriginUncertainty object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract OriginUncertainty mapV20OriginUncertainty(OriginUncertaintyDto originUncertaintyDto);
}
