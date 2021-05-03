@XmlSchema(
        namespace = "http://quakeml.org/xmlns/quakeml",
        xmlns = {
                @XmlNs(namespaceURI = "http://quakeml.org/xmlns/quakeml", prefix = "qml"),
                @XmlNs(namespaceURI = "http://quakeml.org/xmlns/bed/1.3", prefix = "bed"),
                @XmlNs(namespaceURI = "http://quakeml.org/xmlns/common/1.0", prefix = "co"),
                // @XmlNs(namespaceURI = "http://quakeml.org/xmlns/macroseismic/0.9", prefix = "ms"),
                @XmlNs(namespaceURI = "http://quakeml.org/xmlns/resourcemetadata/1.0", prefix = "rm"),
                @XmlNs(namespaceURI = "http://www.w3.org/2001/XMLSchema-instance", prefix = "xsi"),
        }
)

package no.nnsn.seisanquakemljpa.models.quakeml.v20;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlSchema;