package no.nnsn.convertercore.errors;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line;

@Data
public class IgnoredLineError {
    private int eventNumber;
    private boolean eventRemoved;
    private int rowNumber;
    private int lineNumber;
    private Line line;
    private String message;
    private String filename;

    public IgnoredLineError() {}

    public IgnoredLineError(String message) {
        this.message = message;
    }

    public IgnoredLineError(int eventNumber, String message) {
        this.eventNumber = eventNumber;
        this.message = message;
    }

}
