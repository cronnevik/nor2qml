package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for Comment Entity in QuakeML 1.2 {@literal <=>} QuakeMl 2.0 conversion
 *
 * @author Christian Rønnevik
 */
@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
public abstract class QmlToCommentMapper {
    public static QmlToCommentMapper INSTANCE = Mappers.getMapper(QmlToCommentMapper.class);

    /**
     * Mapping of properties for Comment Entity from QuakeML version 2.0 to QuakeML version 1.2.
     * The Comment object has a many-to-one relation with multiple objects (e.g. Amplitude, Arrival, Event, etc).
     * Hence, the relation is ignored in mapping to avoid recursion. The relation is set in the parent object.
     *
     * @param comment Comment object of QuakeML version 2.0
     * @return CommentDto object of QuakeML version 1.2.
     */
    @Mappings({
            @Mapping(target = "amplitude", ignore = true),
            @Mapping(target = "arrival", ignore = true),
            @Mapping(target = "event", ignore = true),
            @Mapping(target = "focalMechanism", ignore = true),
            @Mapping(target = "magnitude", ignore = true),
            @Mapping(target = "momentTensor", ignore = true),
            @Mapping(target = "origin", ignore = true),
            @Mapping(target = "pick", ignore = true),
            @Mapping(target = "stationMagnitude", ignore = true)
    })
    public abstract CommentDto mapV12Comment(Comment comment);

    /**
     * Inverse mapping of properties for Comment Entity.
     * The many-to-one relationship is set in the parent object, thus ignored in the mapping.
     *
     * @param commentDto CommentDto object of QuakeML version 1.2
     * @return Comment object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    @Mappings({
            @Mapping(target = "amplitude", ignore = true),
            @Mapping(target = "arrival", ignore = true),
            @Mapping(target = "event", ignore = true),
            @Mapping(target = "focalMechanism", ignore = true),
            @Mapping(target = "magnitude", ignore = true),
            @Mapping(target = "momentTensor", ignore = true),
            @Mapping(target = "origin", ignore = true),
            @Mapping(target = "pick", ignore = true),
            @Mapping(target = "stationMagnitude", ignore = true)
    })
    public abstract Comment mapV20Comment(CommentDto commentDto);
}
