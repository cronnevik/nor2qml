package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineFQuakemlEntities {
    String preferredFocalMechanismID;
    List<Origin> lm1Origins;
    List<FocalMechanism> focalMechanisms;
    List<Magnitude> lm1Magnitudes;
}
