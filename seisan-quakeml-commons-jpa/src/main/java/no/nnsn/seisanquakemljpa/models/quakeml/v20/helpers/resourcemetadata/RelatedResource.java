package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.enums.RelationType;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class RelatedResource {
    private String reference;
    @Enumerated(EnumType.STRING)
    private RelationType relationType;
}
