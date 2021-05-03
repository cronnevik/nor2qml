package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum SourceTimeFunctionTypeDto {
    @XmlEnumValue("box car")
    BOX_CAR,
    @XmlEnumValue("triangle")
    TRIANGLE,
    @XmlEnumValue("trapezoid")
    TRAPEZOID,
    @XmlEnumValue("unknown")
    UNKNOWN;
}