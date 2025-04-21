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

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Amplitude {

    @XmlAttribute
    private String publicID; // ResourceReference

    private RealQuantity genericAmplitude;
    private String type;

    @Enumerated(EnumType.STRING)
    private AmplitudeUnit unit;
    @Enumerated(EnumType.STRING)
    AmplitudeCategory category;

    private RealQuantity period;
    private TimeWindow timeWindow;
    private WaveformStreamID waveformID;
    private String pickID; // ResourceReference
    private String filterID; // ResourceReference
    private String methodID; // ResourceReference
    private String magnitudeHint;
    private TimeQuantity scalingTime;
    private String snr;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    private CreationInfo creationInfo;
    private List<Comment> comment;

}
