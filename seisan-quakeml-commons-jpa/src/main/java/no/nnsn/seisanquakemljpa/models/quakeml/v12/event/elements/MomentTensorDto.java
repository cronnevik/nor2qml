package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.MTInversionTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.MomentTensorCategoryDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evMomentTensor")
@XmlAccessorType(XmlAccessType.FIELD)
public class MomentTensorDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String derivedOriginID;
    private String momentMagnitudeID;

    @Embedded
    private RealQuantityDto scalarMoment;
    @Embedded
    private TensorDto tensor;

    private Double variance;
    private Double varianceReduction;
    private Double doubleCouple;
    private Double clvd;
    private Double iso;

    private String greensFunctionID;
    private String filterID;

    @Embedded
    private SourceTimeFunctionDto sourceTimeFunction;

    private String methodID;

    @Enumerated(EnumType.STRING)
    private MomentTensorCategoryDto category;
    @Enumerated(EnumType.STRING)
    private MTInversionTypeDto inversionType;

    @Embedded
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */
    @OneToMany(mappedBy = "momentTensor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @OneToMany(mappedBy = "momentTensor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<DataUsedDto> dataUsed;

    @XmlTransient
    @OneToOne(fetch = FetchType.LAZY)
    FocalMechanismDto focalMechanism;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setMomentTensor(this);
            }
        }
    }

    public void setDataUsed(List<DataUsedDto> dataUsed) {
        this.dataUsed = dataUsed;
        // Hibernate relationship specific
        if (dataUsed != null) {
            for (DataUsedDto dUsed: dataUsed) {
                dUsed.setMomentTensor(this);
            }
        }
    }

}