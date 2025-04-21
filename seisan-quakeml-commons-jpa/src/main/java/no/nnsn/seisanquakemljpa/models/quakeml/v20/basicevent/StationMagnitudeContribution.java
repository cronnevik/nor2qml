package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeContribution {
    private String stationMagnitudeID; // ResourceReference
    private Double residual;
    private Double weight;
}
