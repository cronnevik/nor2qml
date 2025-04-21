package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class GeographicLocation {
    private Double longitude;
    private Double latitude;
    private Double elevation;
    private Double horizontalUncertainty;
    private Double verticalUncertainty;
}
