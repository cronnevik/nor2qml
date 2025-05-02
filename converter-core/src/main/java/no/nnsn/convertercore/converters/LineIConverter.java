package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterErrorLogging;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.LineIQuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineI;

import java.util.ArrayList;
import java.util.List;

public class LineIConverter {
    private final List<LineI> lineIs;
    private final SfileInfo sfileInfo;

    public LineIConverter(List<LineI> lineIs, SfileInfo sfileInfo) {
        this.lineIs = lineIs;
        this.sfileInfo = sfileInfo;
    }

    public LineIQuakemlEntities convert(QmlMapper mapper) {
        List<Comment> comments = new ArrayList<>();

        if (lineIs != null) {
            for (LineI lineI : lineIs) {
                try {
                    comments.add(mapper.mapLIComment(lineI));
                } catch (Exception ex) {
                    IgnoredLineError error = new IgnoredLineError().generate(lineI, ex, sfileInfo);
                    ConverterErrorLogging.addError(error);
                }
            }
            return new LineIQuakemlEntities(comments);
        }
        return new LineIQuakemlEntities();
    }
}
