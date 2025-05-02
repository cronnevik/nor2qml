package no.nnsn.seisanquakeml.models.quakeml.helpers;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbBooleanSerializer extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String v) {
       return Boolean.valueOf(v);
    }

    @Override
    public String marshal(Boolean v) {
        return (v) ? "true" : "false";
    }

}
