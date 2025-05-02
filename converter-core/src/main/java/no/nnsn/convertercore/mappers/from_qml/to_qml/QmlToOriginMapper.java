package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.OriginDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Origin Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToArrivalMapper.class,
                QmlToCommentMapper.class,
                QmlToCompositeTimeMapper.class,
                QmlToOriginUncertaintyMapper.class
        }
)
public abstract class QmlToOriginMapper {

    public static QmlToOriginMapper INSTANCE = Mappers.getMapper(QmlToOriginMapper.class);

    /**
     * Mapping of properties for Origin Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Origin object has a many-to-one relation the Event object.
     * The relation is set in the parent (Event) object and thus the mapping is ignored.
     *
     * @param origin Origin object of QuakeML version 2.0
     * @return OriginDto object of QuakeML version 1.2.
     */
    public abstract OriginDto mapV12Origin(Origin origin);

    /**
     * Inverse mapping of properties for Origin Entity.
     * Relation set in the parent object (Event) is ignored in the mapping.
     *
     * @param originDto OriginDto object of QuakeML version 1.2
     * @return Origin object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Origin mapV20Origin(OriginDto originDto);

}
