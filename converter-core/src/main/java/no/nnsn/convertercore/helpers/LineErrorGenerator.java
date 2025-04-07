package no.nnsn.convertercore.helpers;

import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line;

public class LineErrorGenerator {

 public static IgnoredLineError generateError(Line line, Exception ex, SfileInfo sfileInfo) {
    if (sfileInfo.getErrorHandling().equals("ignore")) {
        no.nnsn.convertercore.errors.IgnoredLineError e = new no.nnsn.convertercore.errors.IgnoredLineError(sfileInfo.getEventCount(), ex.getMessage());
        e.setLine(line);
        e.setFilename(sfileInfo.getFilename());
        return e;
    } else {
        throw new CustomException(
                "Error found in s-file number: " + sfileInfo.getEventCount() + ". "
                        + ex.getMessage()
        );
    }
    }
}
