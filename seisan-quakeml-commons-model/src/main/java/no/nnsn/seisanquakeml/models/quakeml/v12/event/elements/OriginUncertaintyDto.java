package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.OriginUncertaintyDescriptionDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.ConfidenceEllipsoidDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginUncertaintyDto {

    @XmlTransient
    private Integer orgUncertaintyID;

    private Double horizontalUncertainty;
    private Double minHorizontalUncertainty;
    private Double maxHorizontalUncertainty;
    private Double azimuthMaxHorizontalUncertainty;

    private ConfidenceEllipsoidDto confidenceEllipsoid;
    private OriginUncertaintyDescriptionDto preferredDescription;
    private Double confidenceLevel;

}