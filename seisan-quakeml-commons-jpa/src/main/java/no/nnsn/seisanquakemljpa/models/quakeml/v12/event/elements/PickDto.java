package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.PickOnsetDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.PickPolarityDto;
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
@Table(name = "qmlV12_evPick")
@XmlAccessorType(XmlAccessType.FIELD)
public class PickDto {
    @Id
    @XmlAttribute
    private String publicID;

    @Embedded
    private TimeQuantityDto time;
    @Embedded
    private WaveformStreamIDDto waveformID;

    private String filterID;
    private String methodID;

    @Embedded
    private RealQuantityDto horizontalSlowness;
    @Embedded
    private RealQuantityDto backazimuth;

    private String slownessMethodID;

    @Enumerated(EnumType.STRING)
    private PickOnsetDto onset;

    private String phaseHint;

    @Enumerated(EnumType.STRING)
    private PickPolarityDto polarity;
    @Enumerated(EnumType.STRING)
    private EvaluationModeDto evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatusDto evaluationStatus;

    private CreationInfoDto creationInfo;

    @ManyToOne
    @XmlTransient
    private EventDto event;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "pick", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setPick(this);
            }
        }
    }

}