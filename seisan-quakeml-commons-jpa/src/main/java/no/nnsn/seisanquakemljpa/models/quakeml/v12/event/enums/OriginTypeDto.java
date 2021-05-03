package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum OriginTypeDto {
    @XmlEnumValue("hypocenter")
    HYPOCENTER,
    @XmlEnumValue("centroid")
    CENTROID,
    @XmlEnumValue("amplitude")
    AMPLITUDE,
    @XmlEnumValue("macroseismic")
    MACROSEISMIC,
    @XmlEnumValue("rupture start")
    RUPTURE_START,
    @XmlEnumValue("rupture end")
    RUPTURE_END;
}