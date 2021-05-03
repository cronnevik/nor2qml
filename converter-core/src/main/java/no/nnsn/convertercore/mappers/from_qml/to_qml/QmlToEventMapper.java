package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.EventDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Event Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToAmplitudeMapper.class,
                QmlToCommentMapper.class,
                QmlToEventDescriptionMapper.class,
                QmlToMagnitudeMapper.class,
                QmlToOriginMapper.class,
                QmlToFocalMechanismMapper.class,
                QmlToMomentTensorMapper.class,
                QmlToPickMapper.class
        }
)public abstract class QmlToEventMapper {

    public static QmlToEventMapper INSTANCE = Mappers.getMapper(QmlToEventMapper.class);

    /**
     * Mapping of properties for Event Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Event object has a one-to-one relation with the EventParameters object.
     * The relation is set in the parent (EventParameters) object, thus ignored in the current mapping.
     *
     * @param event Event object of QuakeML version 2.0
     * @return EventDto object of QuakeML version 1.2.
     */

    public abstract EventDto mapV12Event(Event event);

    /**
     * Inverse mapping of properties for Event Entity.
     * The one-to-one relationship is set in the parent (EventParameters) object, thus ignored in the mapping.
     *
     * @param eventDto EventDto object of QuakeML version 1.2
     * @return Event object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Event mapV20Event(EventDto eventDto);

}
