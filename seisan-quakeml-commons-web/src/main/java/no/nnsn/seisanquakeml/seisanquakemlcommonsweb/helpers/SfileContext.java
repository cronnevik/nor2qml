package no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class SfileContext {
    private SfileOptions options;
    private List<Sfile> sfiles;

    public SfileOptions getOptions() {
        return options;
    }

    public void setOptions(SfileOptions options) {
        this.options = options;
    }

    public List<Sfile> getSfiles() {
        return sfiles;
    }

    public void setSfiles(List<Sfile> sfiles) {
        this.sfiles = sfiles;
    }
}
