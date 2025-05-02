package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterErrorLogging;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line6QuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line6;

import java.util.ArrayList;
import java.util.List;

public class Line6Converter {

    private final List<Line6> line6s;
    private final SfileInfo sfileInfo;

    public Line6Converter(List<Line6> line6s, SfileInfo sfileInfo) {
        this.line6s = line6s;
        this.sfileInfo = sfileInfo;
    }

    public Line6QuakemlEntities convert(QmlMapper mapper) {
        List<Comment> comments = new ArrayList<>();

        if (line6s != null) {
            for (Line6 line6 : line6s) {
                try {
                    comments.add(mapper.mapL6Comment(line6));
                } catch (Exception ex) {
                    IgnoredLineError error = new IgnoredLineError().generate(line6, ex, sfileInfo);
                    ConverterErrorLogging.addError(error);
                }
            }
            return new Line6QuakemlEntities(comments);
        }
        return new Line6QuakemlEntities();
    }
}
