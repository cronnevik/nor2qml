package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AmplitudeUnit {
    @XmlEnumValue("m")
    Meter,
    @XmlEnumValue("s")
    Seconds,
    @XmlEnumValue("m/s")
    METERS_PER_SECOND,
    @XmlEnumValue("m/(s*s)")
    METERS_PER_SECOND_SQUARED,
    @XmlEnumValue("m*s")
    METER_SECONDS,
    @XmlEnumValue("dimensionless")
    DIMENSIONLESS,
    @XmlEnumValue("other")
    OTHER
}
