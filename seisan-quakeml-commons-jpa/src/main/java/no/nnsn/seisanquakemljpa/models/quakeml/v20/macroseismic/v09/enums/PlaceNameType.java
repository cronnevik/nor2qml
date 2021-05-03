package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum PlaceNameType {
    @XmlEnumValue("official")
    OFFICIAL,
    @XmlEnumValue("endonym")
    ENDONYM,
    @XmlEnumValue("exonym")
    EXONYM,
    @XmlEnumValue("historical")
    HISTORICAL,
    @XmlEnumValue("reported")
    REPORTED,
    @XmlEnumValue("shortened")
    SHORTENED
}
