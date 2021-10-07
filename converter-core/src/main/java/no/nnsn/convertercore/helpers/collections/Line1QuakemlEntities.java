package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;

import java.util.List;

@Data
@AllArgsConstructor
public class Line1QuakemlEntities {
    String preferredOriginID;
    String preferredMagnitudeID;

    List<Origin> origins;
    List<Magnitude> magnitudes;
    List<IgnoredLineError> errors;

    Boolean errorInFirstLine1;

    public Line1QuakemlEntities(Boolean errorInFirstLine1) {
        this.errorInFirstLine1 = errorInFirstLine1;
    }

    public Boolean hasErrorInFirstLine1() {
        return errorInFirstLine1;
    }
}
