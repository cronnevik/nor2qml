package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationCloudPDF2D {
    private String longitude;
    private String latitude;
    private String probability;
}
