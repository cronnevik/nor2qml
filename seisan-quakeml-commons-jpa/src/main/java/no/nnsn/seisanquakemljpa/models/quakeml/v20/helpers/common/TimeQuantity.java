package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeQuantity {
    private String value;
    private Double uncertainty;
    private Double lowerUncertainty;
    private Double upperUncertainty;
    private Double confidenceLevel;

}
