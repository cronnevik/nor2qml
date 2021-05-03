package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PickPolarityDto {
    @XmlEnumValue("positive")
    POSITIVE,
    @XmlEnumValue("negative")
    NEGATIVE,
    @XmlEnumValue("undecidable")
    UNDECIDABLE;
}
