package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeQuantityDto {
    private String value;
    private Double uncertainty;
    private Double lowerUncertainty;
    private Double upperUncertainty;
    private Double confidenceLevel;
}