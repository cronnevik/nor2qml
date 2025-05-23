package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.Waveform;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class WaveformStreamID {
    @XmlAttribute
    private String networkCode;
    @XmlAttribute
    private String stationCode;
    @XmlAttribute
    private String channelCode;
    @XmlAttribute
    private String locationCode;
    @XmlAttribute
    private String resourceURI; // ResourceReference
}
