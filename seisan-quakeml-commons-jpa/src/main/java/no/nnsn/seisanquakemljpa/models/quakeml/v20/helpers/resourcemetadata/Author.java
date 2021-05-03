package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_rmAuthor")
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer authorID;

    @Embedded
    private Person person;

    private String mbox;
    private Integer positionInAuthorList;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "author", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private PersonalAffiliation affiliation;

    @OneToOne(mappedBy = "altAuthor", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private PersonalAffiliation alternateAffiliation;

    @OneToOne(mappedBy = "author", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private LiteratureSource literatureSource;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(Comment cmt) {
        this.comment = cmt;
        // Hibernate relationship specific
        if (cmt != null) {
            cmt.setAuthor(this);
        }

    }

    public void setAffiliation(PersonalAffiliation affiliation) {
        this.affiliation = affiliation;
        // Hibernate relationship specific
        if (affiliation != null) {
            affiliation.setAuthor(this);
        }
    }

    public void setAlternateAffiliation(PersonalAffiliation alternateAffiliation) {
        this.alternateAffiliation = alternateAffiliation;
        // Hibernate relationship specific
        if (alternateAffiliation != null) {
            alternateAffiliation.setAltAuthor(this);
        }
    }
}
