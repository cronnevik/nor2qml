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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class Pick {

    @XmlAttribute
    private String publicID; // ResourceReference

    private TimeQuantity time;
    private WaveformStreamID waveformID;
    private String methodID; // ResourceReference
    private String filterID; // ResourceReference
    private RealQuantity backazimuth;
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

    private CreationInfo creationInfo;
    private List<Comment> comment;

    @XmlTransient
    private Boolean timeOverMidnight = false;
}
