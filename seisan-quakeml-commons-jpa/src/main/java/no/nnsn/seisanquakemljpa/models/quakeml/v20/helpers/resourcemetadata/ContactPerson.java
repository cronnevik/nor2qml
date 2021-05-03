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
@Table(name = "qmlV20_rmContPers")
@XmlAccessorType(XmlAccessType.FIELD)
public class ContactPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer contPersID;

    @Embedded
    private Person person;

    private String mbox;
    private String phone;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "contactPerson", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private PersonalAffiliation affiliation;

    /*
     *   Custom setter methods for relations
     */

    public void setAffiliation(PersonalAffiliation affiliation) {
        this.affiliation = affiliation;
        // Hibernate relationship specific
        if (affiliation != null) {
            affiliation.setContactPerson(this);
        }
    }
}
