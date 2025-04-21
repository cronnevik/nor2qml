package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicOrigin {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String originReference; // ResourceIdentifier
    private String relatedMacroseismicOrigin; // RelatedResource
    private String definingMDPSet; // ResourceIdentifier
    private Integer contributingMDPCount;

    private MacroseismicIntensity epicentralIntensity;
    private MacroseismicIntensity maximumIntensity;
    private CreationInfoDto creationInfo;

    private LiteratureSource literatureSource;
}
