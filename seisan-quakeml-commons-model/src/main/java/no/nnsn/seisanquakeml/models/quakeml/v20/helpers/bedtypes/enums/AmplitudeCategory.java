package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AmplitudeCategory {
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
