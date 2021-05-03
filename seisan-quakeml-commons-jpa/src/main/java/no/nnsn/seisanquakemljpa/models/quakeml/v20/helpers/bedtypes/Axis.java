package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Axis {
    @Embedded
    private RealQuantity azimuth;
    @Embedded
    private RealQuantity plunge;
    @Embedded
    private RealQuantity length;
}
