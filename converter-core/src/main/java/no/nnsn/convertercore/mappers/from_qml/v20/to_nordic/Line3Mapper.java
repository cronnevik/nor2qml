package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line3;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for QuakeML {@literal=>} Seisan Line type 3
 *
 * @author Christian Rønnevik
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class Line3Mapper {
    public static Line3Mapper INSTANCE = Mappers.getMapper(Line3Mapper.class);

    @Mappings({
            @Mapping(target = "commentText", source = "eventDescription.text"),
            @Mapping(target = "descriptionType",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.DescriptionType.EVENT_LOCALITY)")
    })
    public abstract Line3 mapEventDescriptionText(EventDescription eventDescription);

    @Mappings({
            @Mapping(target = "commentText", source = "comment.text"),
            @Mapping(target = "descriptionType",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.DescriptionType.EVENT_COMMENT)")
    })
    public abstract Line3 mapEventComment(Comment comment);

    @Mappings({
            @Mapping(target = "lineText", source = "comment.text"),
            @Mapping(target = "descriptionType",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.DescriptionType.EVENT_COMMENT)")
    })
    public abstract Line3 mapEventFullComment(Comment comment);

}
