package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evStationMagCont")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeContributionDto {
    @Id
    @XmlTransient
    private String stationMagnitudeID;

    private Double residual;
    private Double weight;

    /*
     *   Relations
     */

    @ManyToOne
    @XmlTransient
    private MagnitudeDto magnitude;

}