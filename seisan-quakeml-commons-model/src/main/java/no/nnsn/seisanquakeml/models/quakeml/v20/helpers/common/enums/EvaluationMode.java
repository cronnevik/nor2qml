package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.enums;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@NoArgsConstructor
@XmlEnum
public enum EvaluationMode {
    @XmlEnumValue("manual")
    MANUAL,
    @XmlEnumValue("automatic")
    AUTOMATIC
}
