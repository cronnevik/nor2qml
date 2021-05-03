package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msReportRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportReference {

    @Id
    private String eqReportID; // ResourceIdentifier

    /*
     *   Relations
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDP mdp;
}
