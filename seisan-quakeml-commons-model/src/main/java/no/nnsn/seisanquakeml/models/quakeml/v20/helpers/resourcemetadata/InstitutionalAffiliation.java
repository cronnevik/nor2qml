package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionalAffiliation {
    private Institution institution;
    private Comment comment;
}
