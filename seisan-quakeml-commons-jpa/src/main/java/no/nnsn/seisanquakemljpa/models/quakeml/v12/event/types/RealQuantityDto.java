package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class RealQuantityDto {
    private Double value;
    private Double uncertainty;
    private Double lowerUncertainty;
    private Double upperUncertainty;
    private Double confidenceLevel;

    public RealQuantityDto(Double value) {
        this.value = value;
    }

    public RealQuantityDto(Double value, Double uncertainty) {
        this.value = value;
        this.uncertainty = uncertainty;
    }

}
