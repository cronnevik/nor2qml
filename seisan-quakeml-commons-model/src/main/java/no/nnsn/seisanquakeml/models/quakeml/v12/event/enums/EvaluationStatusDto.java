package no.nnsn.seisanquakeml.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum EvaluationStatusDto {
    @XmlEnumValue("preliminary")
    PRELIMINARY,
    @XmlEnumValue("confirmed")
    CONFIRMED,
    @XmlEnumValue("reviewed")
    REVIEWED,
    @XmlEnumValue("final")
    FINAL,
    @XmlEnumValue("rejected")
    REJECTED
}