package no.nnsn.quakemlwebservice.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "Catalogs")
@XmlAccessorType(XmlAccessType.FIELD)
public class CatalogWrapper {
    @XmlElement(name = "Catalog")
    List<String> catalogNames;
}
