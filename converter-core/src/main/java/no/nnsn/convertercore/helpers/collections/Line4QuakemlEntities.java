package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line4QuakemlEntities {
    List<Pick> picks;
    List<Amplitude> amplitudes;
    List<Arrival> arrivals;
}
