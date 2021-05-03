package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickOnset;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickPolarity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
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
@Table(name = "qmlV20_evPick")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pick implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    @Embedded
    private TimeQuantity time;

    @Embedded
    private WaveformStreamID waveformID;

    private String methodID; // ResourceReference
    private String filterID; // ResourceReference

    @Embedded
    private RealQuantity backazimuth;

    @Embedded
    private RealQuantity horizontalSlowness;

    private String slownessMethodID; // ResourceReference

    @Enumerated(EnumType.STRING)
    private PickOnset onset;

    private String phaseHint;

    @Enumerated(EnumType.STRING)
    private PickPolarity polarity;
    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;

    @XmlTransient
    private Boolean timeOverMidnight = false;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "pick", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setPick(this);
            }
        }
    }
}
