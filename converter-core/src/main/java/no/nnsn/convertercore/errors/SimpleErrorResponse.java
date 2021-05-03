package no.nnsn.convertercore.errors;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SimpleErrorResponse {
    String message;
}
