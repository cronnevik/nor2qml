package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicIntensity {
    private String macroseismicScale; // ResourceIdentifier
    @Embedded
    private IntensityValueType expectedIntensity;
    @Embedded
    private IntensityValueType maximalCredibleIntensity;
    @Embedded
    private IntensityValueType minimalCredibleIntensity;
}
