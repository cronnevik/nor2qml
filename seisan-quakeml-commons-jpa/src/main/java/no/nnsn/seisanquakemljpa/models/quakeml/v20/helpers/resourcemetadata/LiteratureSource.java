package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.DCMITypeURI;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.LanguageCodeURI;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.enums.BibtexEntryType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_rmLitSrc")
@XmlAccessorType(XmlAccessType.FIELD)
public class LiteratureSource {

    @Id
    private String identifier; // ResourceReference
    @Embedded
    private DCMITypeURI type;
    @Enumerated(EnumType.STRING)
    private BibtexEntryType bibtexType;
    @Embedded
    private LanguageCodeURI language;
    private String title;

    private String author;
    private String editor;
    private String bibliographicCitation;
    private String date;
    private String booktitle;
    private String volume;
    private String number;
    private String series;
    private String issue;
    private String year;
    private String edition;
    private String startPage;
    private String endPage;
    private String publisher;
    private String address;
    private String rights;
    private String rightsHolder;
    private String accessRights;
    private String license;
    private String publicationStatus;


    /*
     *   Relations
     */

    @OneToOne(mappedBy = "literatureSource", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Author creator;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private AnnotatedLiteratureSource annotatedLiteratureSource;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    EventConsequences eventConsequences;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    MacroseismicEvent macroseismicEvent;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    MacroseismicOrigin macroseismicOrigin;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    MDPSet mdpSet;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDP mdp;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Place place;

    /*
     *   Custom setter methods for relations
     */

    public void setCreator(Author creator) {
        this.creator = creator;
        // Hibernate relationship specific
        if (creator != null) {
            creator.setLiteratureSource(this);
        }
    }
}
