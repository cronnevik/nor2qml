package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.RealQuantityDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.WaveformStreamIDDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeDto {

    @XmlAttribute
    private String publicID;

    private String originID;
    private RealQuantityDto mag;
    private String type;
    private String amplitudeID;
    private String methodID;
    private WaveformStreamIDDto waveformID;
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    private List<CommentDto> comment;

}
