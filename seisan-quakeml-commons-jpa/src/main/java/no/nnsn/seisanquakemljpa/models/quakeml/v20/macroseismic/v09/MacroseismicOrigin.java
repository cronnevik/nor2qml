package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msOrigin")
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicOrigin {
    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String originReference; // ResourceIdentifier
    private String relatedMacroseismicOrigin; // RelatedResource
    private String definingMDPSet; // ResourceIdentifier
    private Integer contributingMDPCount;

    @Embedded
    private MacroseismicIntensity epicentralIntensity;
    @Embedded
    private MacroseismicIntensity maximumIntensity;

    @Embedded
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    @OneToOne(mappedBy = "macroseismicOrigin", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
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
            literatureSource.setMacroseismicOrigin(this);
        }
    }
}
