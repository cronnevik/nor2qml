package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum OriginUncertaintyDescription {
    @XmlEnumValue("horizontal uncertainty")
    HORIZONTAL,
    @XmlEnumValue("uncertainty ellipse")
    ELLIPSE,
    @XmlEnumValue("confidence ellipsoid")
    ELLIPSOID
}
