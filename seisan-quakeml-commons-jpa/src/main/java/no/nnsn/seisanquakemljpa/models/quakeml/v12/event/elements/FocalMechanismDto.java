package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class FocalMechanismDto {
    @XmlAttribute
    private String publicID;

    private String triggeringOriginID;
    private NodalPlanesDto nodalPlanes;
    private PrincipalAxesDto principalAxes;
    private Double azimuthalGap;
    private Integer stationPolarityCount;
    private Double misfit;
    private Double stationDistributionRatio;
    private String methodID;
    private WaveformStreamIDDto waveformID;

    private EvaluationModeDto evaluationMode;
    private EvaluationStatusDto evaluationStatus;

    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    private List<CommentDto> comment;

    MomentTensorDto momentTensor;

}
