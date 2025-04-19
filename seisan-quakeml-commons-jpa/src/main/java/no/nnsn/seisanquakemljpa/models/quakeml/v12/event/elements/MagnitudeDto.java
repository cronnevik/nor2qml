package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.RealQuantityDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class MagnitudeDto {

    @XmlAttribute
    private String publicID;

    private RealQuantityDto mag;
    private String type;
    private String originID;
    private String methodID;
    private Integer stationCount;
    private Double azimuthalGap;

    private EvaluationModeDto evaluationMode;
    private EvaluationStatusDto evaluationStatus;

    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    private List<CommentDto> comment;

    private List<StationMagnitudeContributionDto> stationMagnitudeContribution;

}