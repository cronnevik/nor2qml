package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.IntegerQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msEventCons")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventConsequences {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    @Embedded
    private IntegerQuantity fatalities;
    private String fatalityDescription;
    @Embedded
    private IntegerQuantity injured;
    private String injuredDescription;
    @Embedded
    private IntegerQuantity homeless;

    @Embedded
    private CreationInfo creationInfo;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "eventConsequences", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource literatureSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicEvent macroseismicEvent;

    /*
     *   Custom setter methods for relations
     */

    public void setLiteratureSource(LiteratureSource literatureSource) {
        this.literatureSource = literatureSource;
        // Hibernate relationship specific
        if (literatureSource != null) {
            literatureSource.setEventConsequences(this);
        }
    }
}
