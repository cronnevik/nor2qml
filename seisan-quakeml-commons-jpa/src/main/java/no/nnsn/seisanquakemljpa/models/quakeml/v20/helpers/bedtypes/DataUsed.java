package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.MomentTensor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.DataUsedWaveType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_evDataUsed")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer dataUsedID;

    private Integer componentCount;
    private Double longestPeriod;
    private Double shortestPeriod;
    private Integer stationCount;
    @Enumerated(EnumType.STRING)
    private DataUsedWaveType waveType;


    /*
     *   Relations
     */

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private MomentTensor momentTensor;

}
