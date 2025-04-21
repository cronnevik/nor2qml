package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class Arrival {

    @XmlAttribute
    private String publicID; // ResourceReference
    @XmlElement
    private String pickID; // ResourceReference
    // QuakeML has Phase(with "code" property) obj instead of string, while most use just string
    @XmlElement
    private String phase;

    @XmlElement
    private Double timeCorrection;
    @XmlElement
    private Double azimuth;
    @XmlElement
    private Double distance;
    @XmlElement
    private RealQuantity takeoffAngle;

    @XmlElement
    private Double timeResidual;
    @XmlElement
    private Double horizontalSlownessResidual;
    @XmlElement
    private Double backazimuthResidual;
    @XmlElement
    private Double timeWeight;
    @XmlElement
    private Double horizontalSlownessWeight;
    @XmlElement
    private Double backazimuthWeight;
    @XmlElement
    private String earthModelID; // ResourceReference

    @XmlElement
    private CreationInfo creationInfo;
    @XmlElement
    private List<Comment> comment;

}
