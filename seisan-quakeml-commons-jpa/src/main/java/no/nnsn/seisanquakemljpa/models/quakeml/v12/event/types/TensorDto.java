package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@NoArgsConstructor
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class TensorDto {

    @Embedded
    @XmlElement(name = "Mrr")
    private RealQuantityDto mrr;
    @Embedded
    @XmlElement(name = "Mtt")
    private RealQuantityDto mtt;
    @Embedded
    @XmlElement(name = "Mpp")
    private RealQuantityDto mpp;
    @Embedded
    @XmlElement(name = "Mrt")
    private RealQuantityDto mrt;
    @Embedded
    @XmlElement(name = "Mrp")
    private RealQuantityDto mrp;
    @Embedded
    @XmlElement(name = "Mtp")
    private RealQuantityDto mtp;

}