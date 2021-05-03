package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.DataUsed;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.SourceTimeFunction;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.Tensor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.MTInversionType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.MomentTensorCategory;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evMomentTensor")
@XmlAccessorType(XmlAccessType.FIELD)
public class MomentTensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    private String derivedOriginID; // ResourceReference
    private String momentMagnitudeID; // ResourceReference

    @Embedded
    private Tensor tensor;
    @Embedded
    private RealQuantity scalarMoment;
    @Embedded
    private RealQuantity variance;
    @Embedded
    private RealQuantity varianceReduction;

    private String filterID; // ResourceReference
    private String greensFunctionID; // ResourceReference
    private String methodID; // ResourceReference

    @Enumerated(EnumType.STRING)
    private MomentTensorCategory category;

    @Embedded
    private RealQuantity clvd;

    @Embedded
    private RealQuantity doubleCouple;

    @Enumerated(EnumType.STRING)
    private MTInversionType inversionType;

    @Embedded
    private RealQuantity iso;

    @Embedded
    private SourceTimeFunction sourceTimeFunction;

    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "momentTensor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToMany(mappedBy = "momentTensor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<DataUsed> dataUsed;

    @XmlTransient
    @OneToOne(fetch = FetchType.LAZY)
    FocalMechanism focalMechanism;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setMomentTensor(this);
            }
        }
    }

    public void setDataUsed(List<DataUsed> dataUsed) {
        this.dataUsed = dataUsed;
        // Hibernate relationship specific
        if (dataUsed != null) {
            for (DataUsed dUsed: dataUsed) {
                dUsed.setMomentTensor(this);
            }
        }
    }
}
