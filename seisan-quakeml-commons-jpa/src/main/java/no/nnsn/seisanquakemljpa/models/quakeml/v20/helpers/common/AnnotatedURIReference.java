package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AnnotatedURIReference {
    private String reference;
    private Comment annotation;
}
