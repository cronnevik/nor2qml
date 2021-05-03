package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.RelatedResource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;
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
@Table(name = "qmlV20_msMdpSet")
@XmlAccessorType(XmlAccessType.FIELD)
public class MDPSet {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    @Embedded
    private RelatedResource relatedVDPSet;

    private String methodID; // ResourceIdentifier
    private Integer mdpCount;
    @Embedded
    private MacroseismicIntensity maximumIntensity;

    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "mdpSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToOne(mappedBy = "mdpSet", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource literatureSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicParameters macroseismicParameters;

    @OneToMany(mappedBy = "mdpSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MDPReference> mdpReference;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setMdpSet(this);
            }
        }
    }

    public void setLiteratureSource(LiteratureSource literatureSource) {
        this.literatureSource = literatureSource;
        // Hibernate relationship specific
        if (literatureSource != null) {
            literatureSource.setMdpSet(this);
        }
    }

    public void setMdpReference(List<MDPReference> mdpReference) {
        this.mdpReference = mdpReference;
        // Hibernate relationship specific
        if (mdpReference != null) {
            for (MDPReference mdpRef: mdpReference) {
                mdpRef.setMdpSet(this);
            }
        }
    }
}
