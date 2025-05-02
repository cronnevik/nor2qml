package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.PickDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Pick;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Pick Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToCommentMapper.class,
                QmlToEventMapper.class
        }
)
public abstract class QmlToPickMapper {

    public static QmlToPickMapper INSTANCE = Mappers.getMapper(QmlToPickMapper.class);

    /**
     * Mapping of properties for Pick Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Pick object has a many-to-one relation the Event object.
     * The relation is set in the parent (Event) object and thus the mapping is ignored.
     *
     * @param pick Pick object of QuakeML version 2.0
     * @return PickDto object of QuakeML version 1.2.
     */
    public abstract PickDto mapV12Pick(Pick pick);

    /**
     * Inverse mapping of properties for Pick Entity.
     * Relation set in the parent object (Event) is ignored in the mapping.
     *
     * @param pickDto PickDto object of QuakeML version 1.2
     * @return Pick object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Pick mapV20Pick(PickDto pickDto);
}
