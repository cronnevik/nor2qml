package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
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
@Table(name = "qmlV20_msMdp")
@XmlAccessorType(XmlAccessType.FIELD)
public class MDP {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String placeReference; // ResourceIdentifier
    @Embedded
    private MacroseismicIntensity intensity;
    @Embedded
    private RelatedResource relatedVDP;

    private Integer reportCount;
    @Embedded
    private TimeQuantity reportedTime;
    private String eventReference; // ResourceIdentifier
    private String methodID; // ResourceIdentifier
    private String quality;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "mdp", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToOne(mappedBy = "mdp", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource literatureSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicParameters macroseismicParameters;

    @OneToMany(mappedBy = "mdp", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<ReportReference> reportReference;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setMdp(this);
            }
        }
    }

    public void setLiteratureSource(LiteratureSource literatureSource) {
        this.literatureSource = literatureSource;
        // Hibernate relationship specific
        if (literatureSource != null) {
            literatureSource.setMdp(this);
        }
    }

    public void setReportReference(List<ReportReference> reportReferences) {
        this.reportReference = reportReferences;
        // Hibernate relationship specific
        if (reportReferences != null) {
            for (ReportReference repRef: reportReferences) {
                repRef.setMdp(this);
            }
        }
    }
}
