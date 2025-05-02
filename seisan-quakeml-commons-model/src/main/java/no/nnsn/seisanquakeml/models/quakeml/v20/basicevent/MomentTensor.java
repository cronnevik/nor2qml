package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.DataUsed;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.SourceTimeFunction;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.Tensor;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.MTInversionType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.MomentTensorCategory;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
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
public class MomentTensor {

    @XmlAttribute
    private String publicID; // ResourceReference

    private String derivedOriginID; // ResourceReference
    private String momentMagnitudeID; // ResourceReference
    private Tensor tensor;
    private RealQuantity scalarMoment;
    private RealQuantity variance;
    private RealQuantity varianceReduction;

    private String filterID; // ResourceReference
    private String greensFunctionID; // ResourceReference
    private String methodID; // ResourceReference

    @Enumerated(EnumType.STRING)
    private MomentTensorCategory category;

    private RealQuantity clvd;
    private RealQuantity doubleCouple;

    @Enumerated(EnumType.STRING)
    private MTInversionType inversionType;

    private RealQuantity iso;
    private SourceTimeFunction sourceTimeFunction;
    private CreationInfo creationInfo;

    private List<Comment> comment;
    private List<DataUsed> dataUsed;

}
