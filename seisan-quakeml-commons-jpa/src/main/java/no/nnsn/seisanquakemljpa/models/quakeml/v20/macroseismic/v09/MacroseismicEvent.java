package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@Entity
@Table(name = "qmlV20_msEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicEvent {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String eventReference; // ResourceIdentifier
    private String preferredVDPSetID; // ResourceIdentifier
    private String preferredMacroseismicOriginID; // ResourceIdentifier
    private String preferredEventConsequenceID; // ResourceIdentifier

    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToOne(mappedBy = "macroseismicEvent", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource literatureSource;

    @OneToMany(mappedBy = "macroseismicEvent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EventConsequences> eventConsequences;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicParameters macroseismicParameters;

    @OneToMany(mappedBy = "macroseismicEvent", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<MDPSetReference> mdpSetReferences;


    /*
     *   Custom setter methods for relations
     */

    public void setEventConsequences(List<EventConsequences> eventConsequences) {
        this.eventConsequences = eventConsequences;
        // Hibernate relationship specific
        if (eventConsequences != null) {
            for (EventConsequences evCons: eventConsequences) {
                evCons.setMacroseismicEvent(this);
            }
        }
    }

    public void setLiteratureSource(LiteratureSource literatureSource) {
        this.literatureSource = literatureSource;
        // Hibernate relationship specific
        if (literatureSource != null) {
            literatureSource.setMacroseismicEvent(this);
        }
    }

    public void setMdpSetReferences(List<MDPSetReference> mdpSetReferences) {
        this.mdpSetReferences = mdpSetReferences;
        // Hibernate relationship specific
        if (mdpSetReferences != null) {
            for (MDPSetReference mdpSetRef: mdpSetReferences) {
                mdpSetRef.setMacroseismicEvent(this);
            }
        }
    }
}
