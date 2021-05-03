package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
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
@Table(name = "qmlV12_evFocalMech")
@XmlAccessorType(XmlAccessType.FIELD)
public class FocalMechanismDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String triggeringOriginID;

    @Embedded
    private NodalPlanesDto nodalPlanes;
    @Embedded
    private PrincipalAxesDto principalAxes;

    private Double azimuthalGap;
    private Integer stationPolarityCount;
    private Double misfit;
    private Double stationDistributionRatio;

    private String methodID;

    @Embedded
    private WaveformStreamIDDto waveformID;

    @Enumerated(EnumType.STRING)
    private EvaluationModeDto evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatusDto evaluationStatus;

    @Embedded
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "focalMechanism", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private EventDto event;

    @OneToOne(mappedBy = "focalMechanism", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    MomentTensorDto momentTensor;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setFocalMechanism(this);
            }
        }
    }

    public void setMomentTensor(MomentTensorDto momentTensor) {
        this.momentTensor = momentTensor;
        // Hibernate relationship specific
        if (momentTensor != null) {
            momentTensor.setFocalMechanism(this);
        }
    }

}
