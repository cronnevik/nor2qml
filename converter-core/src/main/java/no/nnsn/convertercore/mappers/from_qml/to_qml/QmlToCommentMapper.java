package no.nnsn.convertercore.mappers.from_qml.to_qml;

import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
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
    public abstract CommentDto mapV12Comment(Comment comment);

    /**
     * Inverse mapping of properties for Comment Entity.
     * The many-to-one relationship is set in the parent object, thus ignored in the mapping.
     *
     * @param commentDto CommentDto object of QuakeML version 1.2
     * @return Comment object of QuakeML version 2.0.
     */
    @InheritInverseConfiguration
    public abstract Comment mapV20Comment(CommentDto commentDto);
}
