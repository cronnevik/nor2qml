package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class IntegerQuantity {
    private Integer value;
    private Integer uncertainty;
    private Integer lowerUncertainty;
    private Integer upperUncertainty;
    private Double confidenceLevel;
}
