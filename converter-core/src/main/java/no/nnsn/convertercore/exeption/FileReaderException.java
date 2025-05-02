package no.nnsn.convertercore.exeption;

import no.nnsn.seisanquakeml.models.exception.NorQmlException;

public class FileReaderException extends NorQmlException {

    public FileReaderException(String message) {
        super(message);
    }

    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}