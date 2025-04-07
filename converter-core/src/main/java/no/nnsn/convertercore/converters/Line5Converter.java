package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line5QuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line5;

import java.util.ArrayList;
import java.util.List;

public class Line5Converter {
    private final List<Line5> line5s;
    private final SfileInfo sfileInfo;

    public Line5Converter(List<Line5> line5s, SfileInfo sfileInfo) {
        this.line5s = line5s;
        this.sfileInfo = sfileInfo;
    }

    public Line5QuakemlEntities convert(QmlMapper mapper) {
        List<Comment> comments = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (line5s != null) {
            for (Line5 line5 : line5s) {
                try {
                    comments.add(mapper.mapL5Comment(line5));
                } catch (Exception ex) {
                    IgnoredLineError error = new IgnoredLineError().generate(line5, ex, sfileInfo);
                    errors.add(error);
                }
            }
            return new Line5QuakemlEntities(comments, errors);
        }
        return new Line5QuakemlEntities();
    }

}
