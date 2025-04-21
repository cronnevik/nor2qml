package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Author {

    private Person person;
    private String mbox;
    private Integer positionInAuthorList;

    private PersonalAffiliation affiliation;
    private PersonalAffiliation alternateAffiliation;
    private Comment comment;
}
