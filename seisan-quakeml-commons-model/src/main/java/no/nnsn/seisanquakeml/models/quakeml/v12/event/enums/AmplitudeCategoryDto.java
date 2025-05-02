package no.nnsn.seisanquakeml.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AmplitudeCategoryDto {
    @XmlEnumValue("point")
    POINT,
    @XmlEnumValue("mean")
    MEAN,
    @XmlEnumValue("duration")
    DURATION,
    @XmlEnumValue("period")
    PERIOD,
    @XmlEnumValue("integral")
    INTEGRAL,
    @XmlEnumValue("other")
    OTHER
}
