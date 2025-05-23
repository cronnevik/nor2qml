package no.nnsn.convertercore.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredQmlError;
import no.nnsn.seisanquakeml.models.sfile.Sfile;

import java.util.List;

@Data
@AllArgsConstructor
public class SfileOverview {
    String sfiletext;
    List<IgnoredQmlError> errors;
    List<Sfile> sfiles;

    public SfileOverview(String sfiletext, List<IgnoredQmlError> errors) {
        this.sfiletext = sfiletext;
        this.errors = errors;
    }
}
