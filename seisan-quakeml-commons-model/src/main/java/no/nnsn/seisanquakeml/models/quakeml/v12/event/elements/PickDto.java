package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.PickOnsetDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.PickPolarityDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class PickDto {

    @XmlAttribute
    private String publicID;

    private TimeQuantityDto time;
    private WaveformStreamIDDto waveformID;
    private String filterID;
    private String methodID;
    private RealQuantityDto horizontalSlowness;
    private RealQuantityDto backazimuth;
    private String slownessMethodID;
    private PickOnsetDto onset;
    private String phaseHint;
    private PickPolarityDto polarity;
    private EvaluationModeDto evaluationMode;
    private EvaluationStatusDto evaluationStatus;

    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    private List<CommentDto> comment;

}