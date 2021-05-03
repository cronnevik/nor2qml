package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeCategory;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeUnit;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeWindow;
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
@NoArgsConstructor
@Entity
@Table(name = "qmlV20_evAmplitude")
@XmlAccessorType(XmlAccessType.FIELD)
public class Amplitude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    @Embedded
    private RealQuantity genericAmplitude;

    private String type;

    @Enumerated(EnumType.STRING)
    private AmplitudeUnit unit;
    @Enumerated(EnumType.STRING)
    AmplitudeCategory category;

    @Embedded
    private RealQuantity period;
    @Embedded
    private TimeWindow timeWindow;
    @Embedded
    private WaveformStreamID waveformID;

    private String pickID; // ResourceReference
    private String filterID; // ResourceReference
    private String methodID; // ResourceReference
    private String magnitudeHint;

    @Embedded
    private TimeQuantity scalingTime;

    private String snr;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "amplitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
                cmt.setAmplitude(this);
            }
        }
    }
}
