package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.helpers.JaxbBooleanSerializer;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.OriginDepthTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.OriginTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginDto {

    @XmlAttribute
    private String publicID;

    private TimeQuantityDto time;
    private RealQuantityDto longitude;
    private RealQuantityDto latitude;
    private RealQuantityDto depth;

    private OriginDepthTypeDto depthType;

    @XmlJavaTypeAdapter(JaxbBooleanSerializer.class)
    private Boolean timeFixed;
    @XmlJavaTypeAdapter(JaxbBooleanSerializer.class)
    private Boolean epicenterFixed;
    private String referenceSystemID;
    private String methodID;
    private String earthModelID;

    private OriginQualityDto quality;
    private OriginTypeDto type;
    private String region;

    private EvaluationModeDto evaluationMode;
    private EvaluationStatusDto evaluationStatus;

    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */
    private List<CompositeTimeDto> compositeTime;
    private List<ArrivalDto> arrival;
    private List<CommentDto> comment;

    private OriginUncertaintyDto originUncertainty;
}


