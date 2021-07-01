package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.NodalPlanes;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.PrincipalAxes;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
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
@Table(name = "qmlV20_evFocalMech")
@XmlAccessorType(XmlAccessType.FIELD)
public class FocalMechanism implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    @Embedded
    private WaveformStreamID waveformID;

    private String triggeringOriginID; // ResourceReference

    @Embedded
    private NodalPlanes nodalPlanes;

    @Embedded
    private PrincipalAxes principalAxes;

    private String methodID; // ResourceReference
    private Double azimuthalGap;
    private Double misfit;
    private Double stationDistributionRatio;
    private Integer stationPolarityCount;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;

    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "focalMechanism", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @OneToOne(mappedBy = "focalMechanism", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private MomentTensor momentTensor;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setFocalMechanism(this);
            }
        }
    }

    public void setMomentTensor(MomentTensor momentTensor) {
        this.momentTensor = momentTensor;
        // Hibernate relationship specific
        if (momentTensor != null) {
            momentTensor.setFocalMechanism(this);
        }
    }
}
