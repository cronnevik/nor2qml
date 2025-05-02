package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.CountryCodeURI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class PostalAddress {
    private String streetAddress;
    private String locality;
    private String postalCode;
    private CountryCodeURI country;
}
