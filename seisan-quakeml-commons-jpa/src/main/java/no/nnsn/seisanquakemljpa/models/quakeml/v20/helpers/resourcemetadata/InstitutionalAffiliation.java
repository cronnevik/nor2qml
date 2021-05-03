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
@Table(name = "qmlV20_rmInstAff")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionalAffiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer instAffID;

    @Embedded
    private Institution institution;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "institutionalAffiliation", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Comment comment;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(Comment cmt) {
        this.comment = cmt;
        // Hibernate relationship specific
        if (cmt != null) {
            cmt.setInstitutionalAffiliation(this);
        }

    }
}
