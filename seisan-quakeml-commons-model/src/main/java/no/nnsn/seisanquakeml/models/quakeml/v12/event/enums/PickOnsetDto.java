package no.nnsn.seisanquakeml.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PickOnsetDto {
    @XmlEnumValue("emergent")
    EMERGENT,
    @XmlEnumValue("impulsive")
    IMPULSIVE,
    @XmlEnumValue("questionable")
    QUESTIONABLE
}
