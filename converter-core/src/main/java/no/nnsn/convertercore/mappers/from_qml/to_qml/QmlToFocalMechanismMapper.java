package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.FocalMechanismDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.FocalMechanism;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for FocalMechanism Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {QmlToMomentTensorMapper.class})
public abstract class QmlToFocalMechanismMapper {

    public static QmlToFocalMechanismMapper INSTANCE = Mappers.getMapper(QmlToFocalMechanismMapper.class);

    /**
     * Mapping of properties for FocalMechanism Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The FocalMechanism object has a many-to-one relation the Event object.
     * The relation is set in the parent (Event) object and thus the mapping is ignored.
     *
     * @param focalMechanism FocalMechanism object of QuakeML version 2.0
     * @return FocalMechanismDto object of QuakeML version 1.2.
     */
    public abstract FocalMechanismDto mapV12FocalMechanism(FocalMechanism focalMechanism);

    /**
     * Inverse mapping of properties for FocalMechanism Entity.
     * Relation set in the parent object (Event) is ignored in the mapping.
     *
     * @param focalMechanismDto FocalMechanismDto object of QuakeML version 1.2
     * @return FocalMechanism object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract FocalMechanism mapV20FocalMechanisn(FocalMechanismDto focalMechanismDto);

}
