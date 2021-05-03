package no.nnsn.seisanquakemljpa.models.quakeml.helpers;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxbBooleanSerializer extends XmlAdapter<String, Boolean> {

    @Override
    public Boolean unmarshal(String v) throws Exception {
       return Boolean.valueOf(v);
    }

    @Override
    public String marshal(Boolean v) throws Exception {
        return (v == true) ? "true" : "false";
    }

}
