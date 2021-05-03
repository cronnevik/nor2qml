package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.MediaTypeURI;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class FileResource {

    private String url;
    @XmlElement(name = "class")
    private String classname; // cannot use "class" as property name
    private String description;
    @Embedded
    private MediaTypeURI mediaType;
    @Embedded
    private CreationInfo creationInfo;
    private String format;
}
