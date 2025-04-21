package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.CountryCodeURI;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.OpenEpoch;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.enums.MDPPlaceType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.PlaceName;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.sitecharacterization.SiteMorphology;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private PlaceName name;
    private PlaceName preferredName;
    private OpenEpoch epoch;
    private RealQuantity referenceLatitude;
    private RealQuantity referenceLongitude;

    private Double horizontalUncertainty;
    private String geometry;
    private String externalGazetter; // RegisteredIdentifier

    @Enumerated(EnumType.STRING)
    private MDPPlaceType type;
    private String zipCode;
    private CountryCodeURI isoCountryCode;
    private RealQuantity altitude;

    private SiteMorphology siteMorphology;
    private CreationInfo creationInfo;

    private LiteratureSource literatureSource;
}
