package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ConfidenceEllipsoid {
    private Double semiMajorAxisLength;
    private Double semiMinorAxisLength;
    private Double semiIntermediateAxisLength;
    private Double majorAxisPlunge;
    private Double majorAxisAzimuth;
    private Double majorAxisRotation;
}
