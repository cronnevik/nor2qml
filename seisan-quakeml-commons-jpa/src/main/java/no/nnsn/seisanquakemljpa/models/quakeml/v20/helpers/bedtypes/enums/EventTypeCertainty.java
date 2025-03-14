package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum EventTypeCertainty {
    @XmlEnumValue("known")
    KNOWN("known"),
    @XmlEnumValue("suspected")
    SUSPECTED("suspected");

    private final String value;

    EventTypeCertainty(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return this.value();
    }
    public static EventTypeCertainty fromValue(String v) {
        for (EventTypeCertainty c: EventTypeCertainty.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }
}