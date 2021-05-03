package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PickPolarity {
    @XmlEnumValue("positive") // Removed in QML model, but should be present?
    POSITIVE,
    @XmlEnumValue("negative")
    NEGATIVE,
    @XmlEnumValue("undecidable")
    UNDECIDABLE;
}
