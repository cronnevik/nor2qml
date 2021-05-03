package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Tensor {
    @XmlElement(name = "Mrr")
    @Embedded
    private RealQuantity mrr;
    @XmlElement(name = "Mtt")
    @Embedded
    private RealQuantity mtt;
    @XmlElement(name = "Mpp")
    @Embedded
    private RealQuantity mpp;
    @XmlElement(name = "Mrt")
    @Embedded
    private RealQuantity mrt;
    @XmlElement(name = "Mrp")
    @Embedded
    private RealQuantity mrp;
    @XmlElement(name = "Mtp")
    @Embedded
    private RealQuantity mtp;

}
