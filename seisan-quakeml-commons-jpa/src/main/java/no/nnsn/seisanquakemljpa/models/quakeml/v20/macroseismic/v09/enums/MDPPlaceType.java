package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MDPPlaceType {
    @XmlEnumValue("isolated building")
    ISOLATED_BUILDING,
    @XmlEnumValue("settlement")
    SETTLEMENT,
    @XmlEnumValue("deserted settlement")
    DESERTED_SETTLEMENT,
    @XmlEnumValue("multiple settlement")
    MULTIPLE_SETTLEMENT,
    @XmlEnumValue("neighbourhood")
    NEIGHBOURHOOD,
    @XmlEnumValue("hamlet")
    HAMLET,
    @XmlEnumValue("municipality")
    MUNICIPALITY,
    @XmlEnumValue("district")
    DISTRICT,
    @XmlEnumValue("region")
    REGION,
    @XmlEnumValue("parish")
    PARISH,
    @XmlEnumValue("diocese")
    DIOCESE,
    @XmlEnumValue("mountain")
    MOUNTAIN,
    @XmlEnumValue("valley")
    VALLEY,
    @XmlEnumValue("lake")
    LAKE,
    @XmlEnumValue("island")
    ISLAND,
    @XmlEnumValue("absorbed")
    ABSORBED,
    @XmlEnumValue("not identified")
    NOT_IDENTIFIED
}
