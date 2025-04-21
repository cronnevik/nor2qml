package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Institution {

    private String name;
    private String identifier;
    private String mbox;
    private String phone;
    private String homepage;
    private PostalAddress postalAddress;
}
