package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.EventDescriptionDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for EventDescription Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class QmlToEventDescriptionMapper {
    public static QmlToEventDescriptionMapper INSTANCE = Mappers.getMapper(QmlToEventDescriptionMapper.class);

    /**
     * Mapping of properties for EventDescription Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The EventDescription object has a many-to-one relation with the Event object.
     * The relation is set in the parent (Event) object, thus ignored in the current mapping.
     *
     * @param eventDescription EventDescription object of QuakeML version 2.0
     * @return EventDescriptionDto object of QuakeML version 1.2.
     */
    @Mappings({
            @Mapping(target = "event", ignore = true)
    })
    public abstract EventDescriptionDto mapV12EventDescription(EventDescription eventDescription);

    /**
     * Inverse mapping of properties for EventDescription Entity.
     * The many-to-one relationship is set in the parent (Event) object, thus ignored in the mapping.
     *
     * @param eventDescriptionDto EventDescriptionDto object of QuakeML version 1.2
     * @return EventDescription object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "event", ignore = true)
    })
    public abstract EventDescription mapV20EventDescription(EventDescriptionDto eventDescriptionDto);
}
