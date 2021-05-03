package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class PeakMotion {

    @Embedded
    private RealQuantity motion;
    private String type;
    @Embedded
    private TimeQuantity atTime;
    private Double frequency;
    private String method; // ResourceReference
}
