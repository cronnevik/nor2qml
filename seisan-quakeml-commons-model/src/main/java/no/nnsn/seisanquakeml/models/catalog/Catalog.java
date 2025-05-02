package no.nnsn.seisanquakeml.models.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catalog_info")
@XmlAccessorType(XmlAccessType.FIELD)
public class Catalog {
    @Id
    @XmlAttribute
    private String publicID;
    private String catalogName;
    private String catalogDescription;
    private String prefix;
    private String authorityID;
    private CreationInfo creationInfo;

}
