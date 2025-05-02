package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.ArrivalDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Arrival;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Arrival Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToCommentMapper.class,
        }
)
public abstract class QmlToArrivalMapper {

    public static QmlToArrivalMapper INSTANCE = Mappers.getMapper(QmlToArrivalMapper.class);

    /**
     * Mapping of properties for Arrival Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Arrival object has a many-to-one relationship with the Origin (parent) object.
     * Relation is set in the Origin object. Hence, the relation it is ignored within this mapping.
     *
     * @param arrival Arrival object of QuakeML version 2.0
     * @return ArrivalDto object used by OriginDto object of QuakeML version 1.2.
     */
    public abstract ArrivalDto mapV12Arrival(Arrival arrival);

    /**
     * Inverse mapping of properties for Arrival Entity.
     * Transform the Arrival Entity of QuakeML version 1.2 to QuakeML version 2.0.
     *
     * @param arrivalDto ArrivalDto object of QuakeML version 1.2
     * @return Arrival object used by Origin object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Arrival mapV20Arrival(ArrivalDto arrivalDto);
}
