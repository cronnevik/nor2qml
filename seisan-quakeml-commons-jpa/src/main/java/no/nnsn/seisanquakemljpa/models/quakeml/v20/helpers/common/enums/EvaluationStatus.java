package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@NoArgsConstructor
@XmlEnum
public enum EvaluationStatus {
    @XmlEnumValue("preliminary")
    PRELIMINARY,
    @XmlEnumValue("confirmed")
    CONFIRMED,
    @XmlEnumValue("reviewed")
    REVIEWED,
    @XmlEnumValue("final")
    FINAL,
    @XmlEnumValue("rejected")
    REJECTED;

}
