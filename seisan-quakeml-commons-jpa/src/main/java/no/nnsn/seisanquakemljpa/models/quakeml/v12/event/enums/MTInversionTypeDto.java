package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MTInversionTypeDto {
    @XmlEnumValue("general")
    GENERAL,
    @XmlEnumValue("zero trace")
    ZERO_TRACE,
    @XmlEnumValue("double couple")
    DOUBLE_COUPLE;
}
