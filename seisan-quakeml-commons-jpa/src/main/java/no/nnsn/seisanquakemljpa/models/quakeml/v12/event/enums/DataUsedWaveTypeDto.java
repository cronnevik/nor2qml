package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum DataUsedWaveTypeDto {
    @XmlEnumValue("P waves")
    P_WAVES,
    @XmlEnumValue("body waves")
    BODY_WAVES,
    @XmlEnumValue("surface waves")
    SURFACE_WAVES,
    @XmlEnumValue("mantle waves")
    MANTLE_WAVES,
    @XmlEnumValue("combined")
    COMBINED,
    @XmlEnumValue("unknown")
    UNKNOWN;

}
