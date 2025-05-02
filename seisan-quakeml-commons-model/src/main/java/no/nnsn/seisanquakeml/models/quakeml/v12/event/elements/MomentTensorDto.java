package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.MTInversionTypeDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.MomentTensorCategoryDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class MomentTensorDto {

    @XmlAttribute
    private String publicID;

    private String derivedOriginID;
    private String momentMagnitudeID;
    private RealQuantityDto scalarMoment;
    private TensorDto tensor;
    private Double variance;
    private Double varianceReduction;
    private Double doubleCouple;
    private Double clvd;
    private Double iso;
    private String greensFunctionID;
    private String filterID;
    private SourceTimeFunctionDto sourceTimeFunction;
    private String methodID;
    private MomentTensorCategoryDto category;
    private MTInversionTypeDto inversionType;
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */
    private List<CommentDto> comment;
    private List<DataUsedDto> dataUsed;

}