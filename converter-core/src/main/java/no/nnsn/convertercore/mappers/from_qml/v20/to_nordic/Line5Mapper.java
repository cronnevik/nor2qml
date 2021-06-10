package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line5;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line6;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public abstract class Line5Mapper {
    public static Line5Mapper INSTANCE = Mappers.getMapper(Line5Mapper.class);

    @Mappings({
            @Mapping(target = "lineText", source = "comment.text")
    })
    public abstract Line5 mapLine5(Comment comment);
}
