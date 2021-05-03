package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PickOnset {
    @XmlEnumValue("emergent")
    EMERGENT,
    @XmlEnumValue("impulsive")
    IMPULSIVE,
    @XmlEnumValue("questionable")
    QUESTIONABLE;
}
