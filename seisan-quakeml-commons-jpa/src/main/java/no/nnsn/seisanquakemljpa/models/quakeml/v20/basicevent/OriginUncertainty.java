package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.ConfidenceEllipsoid;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginUncertaintyDescription;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evOriginUnc")
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginUncertainty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer orgUncertaintyID;

    @Enumerated(EnumType.STRING)
    private OriginUncertaintyDescription preferredDescription;

    private Double horizontalUncertainty;
    private Double maxHorizontalUncertainty;
    private Double minHorizontalUncertainty;
    private Double azimuthMaxHorizontalUncertainty;

    @Embedded
    private ConfidenceEllipsoid confidenceEllipsoid;

    private Double confidenceLevel;

    /*
     *   Relations
     */

    @XmlTransient
    @OneToOne(fetch = FetchType.LAZY)
    private Origin origin;
}
