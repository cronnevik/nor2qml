package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Tensor {
    @XmlElement(name = "Mrr")
    private RealQuantity mrr;
    @XmlElement(name = "Mtt")
    private RealQuantity mtt;
    @XmlElement(name = "Mpp")
    private RealQuantity mpp;
    @XmlElement(name = "Mrt")
    private RealQuantity mrt;
    @XmlElement(name = "Mrp")
    private RealQuantity mrp;
    @XmlElement(name = "Mtp")
    private RealQuantity mtp;
}
