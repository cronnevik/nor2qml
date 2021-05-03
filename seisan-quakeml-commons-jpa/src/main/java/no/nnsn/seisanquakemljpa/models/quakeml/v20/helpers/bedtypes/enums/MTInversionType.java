package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MTInversionType {
    @XmlEnumValue("general")
    GENERAL,
    @XmlEnumValue("zero trace")
    ZERO_TRACE,
    @XmlEnumValue("double couple")
    DOUBLE_COUPLE;
}