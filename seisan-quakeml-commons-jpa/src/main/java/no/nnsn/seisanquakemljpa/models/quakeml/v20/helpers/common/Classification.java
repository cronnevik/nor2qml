package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Classification {
    @XmlElement(name = "class")
    private String classification; // cannot use "class" as property name as it is reserved java word
    private String classificationScheme;
    @Embedded
    private LiteratureSource classificationSource;
}
