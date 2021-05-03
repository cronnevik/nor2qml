package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AmplitudeUnitDto {
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
    OTHER;
}