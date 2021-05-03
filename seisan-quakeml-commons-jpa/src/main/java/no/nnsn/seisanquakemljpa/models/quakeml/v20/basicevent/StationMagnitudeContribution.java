package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evStationMagCont")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeContribution implements Serializable {

    private static final long serialVersionUID = 1L;

    // ID property for entity (not defined in QuakeML, but required for data persistence)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer contributionID;

    private String stationMagnitudeID; // ResourceReference
    private Double residual;
    private Double weight;

    /*
     *   Relations
     */

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Magnitude magnitude;

}
