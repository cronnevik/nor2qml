package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {
    private String name;
    private String givenName;
    private String familyName;
    private String title;
    private String personID; // co:ResourceIdentifier
    private String alternatePersonID; // co:ResourceIdentifier
    private String mbox; // co:ResourceIdentifier
    private String phone; // co:ResourceIdentifier
    private String homepage; // co:ResourceLocator
    private String workplaceHomepage; // co:ResourceLocator
}
