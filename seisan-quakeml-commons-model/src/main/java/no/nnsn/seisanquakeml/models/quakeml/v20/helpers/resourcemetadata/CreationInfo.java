package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class CreationInfo {
    private String author;
    private String authorURI;
    private String agencyID;
    private String agencyURI;
    private String creationTime;
    private String version;
    private String copyrightOwner;
    private String copyrightOwnerURI;
    private String license;
}
