package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.ConfidenceEllipsoid;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginUncertaintyDescription;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginUncertainty {

    @Enumerated(EnumType.STRING)
    private OriginUncertaintyDescription preferredDescription;

    private Double horizontalUncertainty;
    private Double maxHorizontalUncertainty;
    private Double minHorizontalUncertainty;
    private Double azimuthMaxHorizontalUncertainty;

    private ConfidenceEllipsoid confidenceEllipsoid;
    private Double confidenceLevel;
}
