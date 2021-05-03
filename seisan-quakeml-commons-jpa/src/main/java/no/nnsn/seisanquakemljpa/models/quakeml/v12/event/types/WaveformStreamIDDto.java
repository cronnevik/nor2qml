package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Data
@NoArgsConstructor
@Embeddable
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