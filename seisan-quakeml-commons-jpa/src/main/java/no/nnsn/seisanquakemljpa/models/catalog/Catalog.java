package no.nnsn.seisanquakemljpa.models.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode (exclude = "events")
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
