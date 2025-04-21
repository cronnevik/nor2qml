package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicIntensity {
    private String macroseismicScale; // ResourceIdentifier
    private IntensityValueType expectedIntensity;
    private IntensityValueType maximalCredibleIntensity;
    private IntensityValueType minimalCredibleIntensity;
}
