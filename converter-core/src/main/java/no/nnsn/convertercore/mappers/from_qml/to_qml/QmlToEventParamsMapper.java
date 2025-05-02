package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.EventParametersDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.EventParameters;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for EventParameters Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
            QmlToEventMapper.class,
            QmlToCommentMapper.class
        }
)
public abstract class QmlToEventParamsMapper {

    public static QmlToEventParamsMapper INSTANCE = Mappers.getMapper(QmlToEventParamsMapper.class);

    /**
     * Mapping of properties for EventParameters Entity from QuakeML version 2.0 to QuakeML version 1.2.

     * @param eventParameters EventParameters object of QuakeML version 2.0
     * @return EventParametersDto object of QuakeML version 1.2.
     */
    public abstract EventParametersDto mapV12EventParams(EventParameters eventParameters);

    /**
     * Inverse mapping of properties for EventParameters Entity.

     * @param eventParametersDto EventParametersDto object of QuakeML version 1.2
     * @return EventParameters object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract EventParameters mapV20EventParams(EventParametersDto eventParametersDto);

}
