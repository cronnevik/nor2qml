package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line6;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for QuakeML {@literal=>} Seisan Line type 6
 *
 * @author Christian Rønnevik
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class Line6Mapper {
    public static Line6Mapper INSTANCE = Mappers.getMapper(Line6Mapper.class);

    @Mappings({
            @Mapping(target = "lineText", source = "comment.text")
    })
    public abstract Line6 mapLine6(Comment comment);
}
