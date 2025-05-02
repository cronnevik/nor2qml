package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum EventDescriptionType {
    @XmlEnumValue("felt report")
    FELT_REPORT,
    @XmlEnumValue("Flinn-Engdahl region")
    FLINN_ENGDAHL_REGION,
    @XmlEnumValue("local time")
    LOCAL_TIME,
    @XmlEnumValue("tectonic summary")
    TECTONIC_SUMMARY,
    @XmlEnumValue("nearest cities")
    NEAREST_CITIES,
    @XmlEnumValue("earthquake name")
    EARTHQUAKE_NAME,
    @XmlEnumValue("region name")
    REGION_NAME
}
