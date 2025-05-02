package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09.utils;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class IntensityValueType {
    @XmlElement(name = "class")
    private String intensityClass; //cannot use "class" as property name;
    private Double numeric;
    private String text;
}
