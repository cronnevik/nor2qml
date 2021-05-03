package no.nnsn.convertercore.mappers.from_qml.to_qml.helpers;

import no.nnsn.convertercore.mappers.from_qml.to_qml.QmlToCommentMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;

import java.util.ArrayList;
import java.util.List;

public class SetterHelper {

    public static List<CommentDto> commentSetter(List<Comment> cmts) {
        if (cmts != null) {
            List<CommentDto> comments = new ArrayList<>(cmts.size());
            for (Comment child: cmts) {
                comments.add(QmlToCommentMapper.INSTANCE.mapV12Comment(child));
            }
            return comments;
        } else {
            return null;
        }
    }

}
