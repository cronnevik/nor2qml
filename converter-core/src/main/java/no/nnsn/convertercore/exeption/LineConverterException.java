package no.nnsn.convertercore.exeption;

import no.nnsn.seisanquakemljpa.models.exception.NorQmlException;

public class LineConverterException extends NorQmlException {
    public LineConverterException(String message) {
        super(message);
    }

    public LineConverterException(String message, Throwable cause) {
        super(message, cause);
    }
}
