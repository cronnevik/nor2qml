package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line1QuakemlEntities {
    String preferredOriginID;
    String preferredMagnitudeID;

    List<Origin> origins;
    List<Magnitude> magnitudes;

    Boolean errorInFirstLine1;

    public Line1QuakemlEntities(Boolean errorInFirstLine1) {
        this.errorInFirstLine1 = errorInFirstLine1;
    }

    public Boolean hasErrorInFirstLine1() {
        return errorInFirstLine1;
    }
}
