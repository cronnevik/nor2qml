package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class RealQuantity {
    private Double value;
    private Double uncertainty;
    private Double lowerUncertainty;
    private Double upperUncertainty;
    private Double confidenceLevel;
    private PDF1D pdf;

    public RealQuantity(Double value) {
        this.value = value;
    }

    public RealQuantity(Double value, Double uncertainty) {
        this.value = value;
        this.uncertainty = uncertainty;
    }
}
