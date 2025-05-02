package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.RelatedResource;
import no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MDPSet {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private RelatedResource relatedVDPSet;
    private String methodID; // ResourceIdentifier
    private Integer mdpCount;
    private MacroseismicIntensity maximumIntensity;
    private CreationInfo creationInfo;

    private List<Comment> comment;
    private LiteratureSource literatureSource;
    private List<MDPReference> mdpReference;
}
