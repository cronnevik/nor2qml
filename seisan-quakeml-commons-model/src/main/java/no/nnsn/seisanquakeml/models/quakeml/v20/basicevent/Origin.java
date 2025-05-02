package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.OriginQuality;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.OriginType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.TimeQuantity;
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
public class Origin {

    @XmlAttribute
    private String publicID; // ResourceReference

    private TimeQuantity time;
    private RealQuantity latitude;
    private RealQuantity longitude;
    private RealQuantity depth;

    @Enumerated(EnumType.STRING)
    private OriginType type;

    @Enumerated(EnumType.STRING)
    private OriginDepthType depthType;

    private Boolean epicenterFixed;
    private Boolean timeFixed;
    private String methodID; // ResourceReference
    private String earthModelID; // ResourceReference
    private String referenceSystemID; // ResourceReference

    private OriginQuality quality;
    private String region;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    private CreationInfo creationInfo;

    private List<Comment> comment;
    private List<CompositeTime> compositeTime;
    private OriginUncertainty originUncertainty;
    private List<Arrival> arrival;


    /*
     *   Custom setter
     */

    public void setEpicenterFixed(Boolean value) {
        this.epicenterFixed = value;
    }

    public void setEpicenterFixed(String str) {

        // Fix error in format when deserialize from isc.ac.uk (uses 0 and 1, when it is suppose to be true or false)
        if (str != null) {
            if ("0".equals(str)) {
                this.epicenterFixed = false;
            } else if("1".equals(str)) {
                this.epicenterFixed = true;
            } else if("true".equals(str)) {
                this.epicenterFixed = true;
            } else if("false".equals(str)) {
                this.epicenterFixed = false;
            }
        }

    }
}
