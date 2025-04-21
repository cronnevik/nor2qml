package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.IntegerQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class EventConsequences {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private IntegerQuantity fatalities;
    private String fatalityDescription;
    private IntegerQuantity injured;
    private String injuredDescription;
    private IntegerQuantity homeless;

    private CreationInfo creationInfo;
    private LiteratureSource literatureSource;
}
