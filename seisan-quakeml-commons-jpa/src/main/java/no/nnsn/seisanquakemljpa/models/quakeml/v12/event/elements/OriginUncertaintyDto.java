package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.OriginUncertaintyDescriptionDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.ConfidenceEllipsoidDto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evOriginUnc")
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginUncertaintyDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer orgUncertaintyID;

    private Double horizontalUncertainty;
    private Double minHorizontalUncertainty;
    private Double maxHorizontalUncertainty;
    private Double azimuthMaxHorizontalUncertainty;

    @Embedded
    private ConfidenceEllipsoidDto confidenceEllipsoid;
    @Enumerated(EnumType.STRING)
    private OriginUncertaintyDescriptionDto preferredDescription;
    private Double confidenceLevel;


    /*
     *   Relations
     */

    @OneToOne
    @XmlTransient
    private OriginDto origin;

}