@XmlSchema(
        namespace = "http://quakeml.org/xmlns/quakeml/1.2",
        xmlns = {
                @XmlNs(prefix = "q", namespaceURI = "http://quakeml.org/xmlns/quakeml/1.2"),
                // schema without prefix set as atribute in the QuakeMLDto class
        }
)

package no.nnsn.seisanquakemljpa.models.quakeml.v12;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;