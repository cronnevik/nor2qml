package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicEvent {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String eventReference; // ResourceIdentifier
    private String preferredVDPSetID; // ResourceIdentifier
    private String preferredMacroseismicOriginID; // ResourceIdentifier
    private String preferredEventConsequenceID; // ResourceIdentifier

    private CreationInfo creationInfo;

    private LiteratureSource literatureSource;
    private List<EventConsequences> eventConsequences;
    private List<MDPSetReference> mdpSetReferences;

}
