package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msMdpRef")
@XmlAccessorType(XmlAccessType.FIELD)
public class MDPReference {

    @Id
    private String mdpID; // ResourceIdentifier
    private Boolean isValid;

    /*
     *   Relations
     */

    @OneToOne(mappedBy = "mdpReference", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Comment validityComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDPSet mdpSet;

    /*
     *   Custom setter methods for relations
     */

    public void setValidityComment(Comment validityComment) {
        this.validityComment = validityComment;
        if (validityComment != null) {
            validityComment.setMdpReference(this);
        }
    }
}
