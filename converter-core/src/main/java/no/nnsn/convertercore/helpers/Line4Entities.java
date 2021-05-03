package no.nnsn.convertercore.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;

@Data
@AllArgsConstructor
public class Line4Entities {
    Pick pick;
    Amplitude amplitude;
    Arrival arrival;

    Boolean extendDay = false;

    public Line4Entities(Pick pick) {
        this.pick = pick;
    }

}
