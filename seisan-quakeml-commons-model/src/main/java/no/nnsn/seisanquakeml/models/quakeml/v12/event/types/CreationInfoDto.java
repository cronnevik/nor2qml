package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CreationInfoDto {
    private String agencyID;
    private String agencyURI;
    private String author;
    private String authorURI;
    private String creationTime;
    private String version;
}
