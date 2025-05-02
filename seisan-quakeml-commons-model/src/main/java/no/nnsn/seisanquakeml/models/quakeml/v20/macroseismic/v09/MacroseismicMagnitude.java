package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.RelatedResource;
import no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicMagnitude {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String magnitudeReference; // ResourceIdentifier
    private RelatedResource relatedMacroseismicMagnitude;

    private String definingVDPSet; // ResourceIdentifier
    private Integer contributingVDPCount;

    private MacroseismicIntensity epicentralIntensity;
    private MacroseismicIntensity literatureSource;
    private CreationInfo creationInfo;
}
