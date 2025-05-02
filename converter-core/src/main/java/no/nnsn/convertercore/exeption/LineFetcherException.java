package no.nnsn.convertercore.exeption;

import no.nnsn.seisanquakeml.models.exception.NorQmlException;

public class LineFetcherException extends NorQmlException {
    public LineFetcherException(String message) {
        super(message);
    }

    public LineFetcherException(String message, Throwable cause) {
        super(message, cause);
    }
}
