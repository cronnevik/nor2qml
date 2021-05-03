package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class Index {
    @Embedded
    private RealQuantity index;
    private String indexingScheme;
    @Embedded
    private LiteratureSource indexSource;
}
