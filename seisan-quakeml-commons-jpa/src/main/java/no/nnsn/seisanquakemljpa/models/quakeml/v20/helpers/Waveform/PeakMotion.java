package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PeakMotion {
    private RealQuantity motion;
    private String type;
    private TimeQuantity atTime;
    private Double frequency;
    private String method; // ResourceReference
}
