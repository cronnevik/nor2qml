package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum SourceTimeFunctionType {
    @XmlEnumValue("box car")
    BOX_CAR,
    @XmlEnumValue("triangle")
    TRIANGLE,
    @XmlEnumValue("trapezoid")
    TRAPEZOID,
    @XmlEnumValue("unknown")
    UNKNOWN
}
