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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msPlace")
@XmlAccessorType(XmlAccessType.FIELD)
public class Place {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    @Embedded
    private PlaceName name;
    @Embedded
    private PlaceName preferredName;
    @Embedded
    private OpenEpoch epoch;
    @Embedded
    private RealQuantity referenceLatitude;
    @Embedded
    private RealQuantity referenceLongitude;

    private Double horizontalUncertainty;
    private String geometry;
    private String externalGazetter; // RegisteredIdentifier

    @Enumerated(EnumType.STRING)
    private MDPPlaceType type;
    private String zipCode;
    @Embedded
    private CountryCodeURI isoCountryCode;
    @Embedded
    private RealQuantity altitude;

    @Embedded
    private SiteMorphology siteMorphology;
    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToOne(mappedBy = "place", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private LiteratureSource literatureSource;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicParameters macroseismicParameters;

    /*
     *   Custom setter methods for relations
     */

    public void setLiteratureSource(LiteratureSource literatureSource) {
        this.literatureSource = literatureSource;
        // Hibernate relationship specific
        if (literatureSource != null) {
            literatureSource.setPlace(this);
        }
    }
}
