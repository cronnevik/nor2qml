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
@Table(name = "qmlV20_rmPersAff")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonalAffiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer persAffID;

    @Embedded
    private Institution institution;
    private String department;
    private String function;

    /*
     *   Relations
     */

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Author author;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Author altAuthor;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private ContactPerson contactPerson;

    @OneToOne(mappedBy = "personalAffiliation", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Comment comment;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(Comment cmt) {
        this.comment = cmt;
        // Hibernate relationship specific
        if (cmt != null) {
            cmt.setPersonalAffiliation(this);
        }

    }
}
