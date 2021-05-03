package no.nnsn.convertercore.errors;

import lombok.Data;

import java.util.List;

@Data
public class SfileErrorResponse {
    List<IgnoredLineError> errors;

    public SfileErrorResponse(List<IgnoredLineError> errors) {
        this.errors = errors;
    }
}
