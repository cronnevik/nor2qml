package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.MagnitudeDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Magnitude;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Magnitude Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                QmlToCommentMapper.class,
        }
)
public abstract class QmlToMagnitudeMapper {

    public static QmlToMagnitudeMapper INSTANCE = Mappers.getMapper(QmlToMagnitudeMapper.class);

    /**
     * Mapping of properties for Magnitude Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Magnitude object has a many-to-one relation the Event object.
     * The relation is set in the parent (Event) object and thus the mapping is ignored.
     *
     * @param magnitude Magnitude object of QuakeML version 2.0
     * @return MagnitudeDto object of QuakeML version 1.2.
     */
    public abstract MagnitudeDto mapV12Magnitude(Magnitude magnitude);

    /**
     * Inverse mapping of properties for Magnitude Entity.
     * Relation set in the parent object (Event) is ignored in the mapping.
     *
     * @param magnitudeDto MagnitudeDto object of QuakeML version 1.2
     * @return Magnitude object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Magnitude mapV20Magnitude(MagnitudeDto magnitudeDto);

}
