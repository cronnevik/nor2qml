package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.NodalPlanes;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.PrincipalAxes;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class FocalMechanism {

    @XmlAttribute
    private String publicID; // ResourceReference

    private WaveformStreamID waveformID;
    private String triggeringOriginID; // ResourceReference
    private NodalPlanes nodalPlanes;
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

    private CreationInfo creationInfo;


    private List<Comment> comment;
    private MomentTensor momentTensor;

}
