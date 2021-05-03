package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.MomentTensorDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.DataUsedWaveTypeDto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qml12_evDataUsed")
@XmlAccessorType(XmlAccessType.FIELD)
public class DataUsedDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer dataUsedID;

    @Enumerated(EnumType.STRING)
    private DataUsedWaveTypeDto waveType;
    private Integer stationCount;
    private Integer componentCount;
    private Double shortestPeriod;
    private Double longestPeriod;

    /*
     *   Relations
     */

    @ManyToOne
    @XmlTransient
    private MomentTensorDto momentTensor;

}