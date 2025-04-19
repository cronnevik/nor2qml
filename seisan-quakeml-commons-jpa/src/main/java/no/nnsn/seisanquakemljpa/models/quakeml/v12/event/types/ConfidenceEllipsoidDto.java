package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfidenceEllipsoidDto {
    private Double semiMajorAxisLength;
    private Double semiMinorAxisLength;
    private Double semiIntermediateAxisLength;
    private Double majorAxisPlunge;
    private Double majorAxisAzimuth;
    private Double majorAxisRotation;

}
