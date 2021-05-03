package no.nnsn.convertercore.helpers;

import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredQmlError;

import java.util.List;

@Data
public class SfileOverview {
    String sfiletext;
    List<IgnoredQmlError> errors;

    public SfileOverview(String sfiletext, List<IgnoredQmlError> errors) {
        this.sfiletext = sfiletext;
        this.errors = errors;
    }
}
