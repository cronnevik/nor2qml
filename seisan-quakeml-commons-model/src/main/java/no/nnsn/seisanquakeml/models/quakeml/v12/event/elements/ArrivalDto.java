package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.RealQuantityDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrivalDto {

    @XmlAttribute
    private String publicID;

    private String pickID;
    // QuakeML 1.2 has Phase(with "code" property) obj instead of string, while most use just string
    private String phase;
    private Double timeCorrection;
    private Double azimuth;
    private Double distance;

    private RealQuantityDto takeoffAngle;

    private Double timeResidual;
    private Double horizontalSlownessResidual;
    private Double backazimuthResidual;
    private Double timeWeight;
    private Double horizontalSlownessWeight;
    private Double backazimuthWeight;
    private String earthModelID;

    private CreationInfoDto creationInfo;

    private Integer timeUsed;


    /*
     *   Relations
     */

    private List<CommentDto> comment;

}
