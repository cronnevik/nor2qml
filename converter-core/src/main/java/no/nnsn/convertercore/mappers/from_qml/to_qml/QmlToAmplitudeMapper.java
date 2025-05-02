package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.AmplitudeDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Amplitude;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Amplitude Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToCommentMapper.class
        }
)
public abstract class QmlToAmplitudeMapper {

    public static QmlToAmplitudeMapper INSTANCE = Mappers.getMapper(QmlToAmplitudeMapper.class);

    /**
     * Mapping of properties for Amplitude Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Amplitude object has a many-to-one relationship with the Event (parent) object.
     * Relation is set in the Event object. Hence, the relation it is ignored within this mapping.
     *
     * @param amplitude Amplitude object of QuakeML version 2.0
     * @return AmplitudeDto object used by EventDto object of QuakeML version 1.2.
     */
    public abstract AmplitudeDto mapV12Amplitude(Amplitude amplitude);


    /**
     * Inverse mapping of properties for Amplitude Entity.
     * Transform the Amplitude Entity of QuakeML version 1.2 to QuakeML version 2.0.
     *
     * @param amplitudeDto AmplitudeDto object of QuakeML version 1.2
     * @return Amplitude object used by Event object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Amplitude mapV20Amplitude(AmplitudeDto amplitudeDto);

}
