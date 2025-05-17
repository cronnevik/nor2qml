package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterSfileErrorLogging;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.LineSQuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineS;

import java.util.ArrayList;
import java.util.List;

public class LineSConverter {
    private final List<LineS> lineSs;
    private final SfileInfo sfileInfo;

    public LineSConverter(List<LineS> lineSs, SfileInfo sfileInfo) {
        this.lineSs = lineSs;
        this.sfileInfo = sfileInfo;
    }

    public LineSQuakemlEntities convert(QmlMapper mapper) {
        List<Comment> comments = new ArrayList<>();

        if (lineSs != null) {
            for (LineS lineS : lineSs) {
                try {
                    comments.add(mapper.mapLSComment(lineS));
                } catch (Exception ex) {
                    IgnoredLineError error = new IgnoredLineError().generate(lineS, ex, sfileInfo);
                    ConverterSfileErrorLogging.addError(error);
                }
            }
            return new LineSQuakemlEntities(comments);
        }
        return new LineSQuakemlEntities();
    }
}
