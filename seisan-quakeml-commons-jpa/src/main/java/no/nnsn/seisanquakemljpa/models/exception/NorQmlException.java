package no.nnsn.seisanquakemljpa.models.exception;

public class NorQmlException extends RuntimeException {

    public NorQmlException(String message) {
        super(message);
    }

    public NorQmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
