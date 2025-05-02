package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
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
public class Magnitude {

    @XmlAttribute
    private String publicID; // ResourceReference

    private RealQuantity mag;
    private String type;
    private String originID; // ResourceReference
    private String methodID; // ResourceReference
    private Integer stationCount;
    private Double azimuthalGap;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    private CreationInfo creationInfo;

    private List<Comment> comment;
    private List<StationMagnitudeContribution> stationMagnitudeContribution;
}
