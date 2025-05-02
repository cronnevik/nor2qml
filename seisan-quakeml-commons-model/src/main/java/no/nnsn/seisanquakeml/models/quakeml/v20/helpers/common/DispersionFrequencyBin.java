package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DispersionFrequencyBin {
    private RealQuantity frequency;
    private RealQuantityPDF velocity;
    private String filterID;
}
