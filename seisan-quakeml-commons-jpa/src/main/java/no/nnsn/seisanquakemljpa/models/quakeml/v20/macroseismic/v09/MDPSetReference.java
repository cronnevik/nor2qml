package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msMdpSetRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class MDPSetReference {

    @Id
    private String mdpSetID; // ResourceIdentifier

    /*
     *   Relations
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    MacroseismicEvent macroseismicEvent;
}
