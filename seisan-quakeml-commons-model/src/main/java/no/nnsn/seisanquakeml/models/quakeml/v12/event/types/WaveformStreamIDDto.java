package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class WaveformStreamIDDto {
    @XmlAttribute
    private String networkCode;
    @XmlAttribute
    private String stationCode;
    @XmlAttribute
    private String channelCode;
    @XmlAttribute
    private String locationCode;
    @XmlAttribute
    private String resourceURI;

}