package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeContributionDto {

    @XmlTransient
    private String stationMagnitudeID;

    private Double residual;
    private Double weight;

}