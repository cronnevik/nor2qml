package no.nnsn.convertercore.errors;

import lombok.Data;

@Data
public class IgnoredQmlError {
    private String message;
    private String entity;
    private String eventPublicID;

    public IgnoredQmlError() {}

    public IgnoredQmlError(String message) {
        this.message = message;
    }

    public IgnoredQmlError(String message, String entity) {
        this.message = message;
        this.entity = entity;
    }
}
