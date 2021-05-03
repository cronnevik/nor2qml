package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.RelatedResource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_msMagnitude")
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicMagnitude {

    @Id
    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String magnitudeReference; // ResourceIdentifier

    @Embedded
    private RelatedResource relatedMacroseismicMagnitude;

    private String definingVDPSet; // ResourceIdentifier
    private Integer contributingVDPCount;

    @Embedded
    private MacroseismicIntensity epicentralIntensity;
    @Embedded
    private MacroseismicIntensity literatureSource;
    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MacroseismicParameters macroseismicParameters;

}
