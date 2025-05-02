package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Index {
    private RealQuantity index;
    private String indexingScheme;
    private LiteratureSource indexSource;
}
