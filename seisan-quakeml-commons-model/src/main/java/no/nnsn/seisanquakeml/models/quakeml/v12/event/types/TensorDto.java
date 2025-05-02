package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TensorDto {
    @XmlElement(name = "Mrr")
    private RealQuantityDto mrr;
    @XmlElement(name = "Mtt")
    private RealQuantityDto mtt;
    @XmlElement(name = "Mpp")
    private RealQuantityDto mpp;
    @XmlElement(name = "Mrt")
    private RealQuantityDto mrt;
    @XmlElement(name = "Mrp")
    private RealQuantityDto mrp;
    @XmlElement(name = "Mtp")
    private RealQuantityDto mtp;
}