package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.EventTypeCertaintyDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.EventTypeDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.EventDescriptionDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDto {
    @XmlAttribute
    private String publicID;

    private String preferredOriginID;
    private String preferredMagnitudeID;
    private String preferredFocalMechanismID;

    private EventTypeDto type;
    private EventTypeCertaintyDto typeCertainty;

    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    private List<EventDescriptionDto> description;
    private List<CommentDto> comment;
    @JsonMerge(OptBoolean.TRUE)
    public List<OriginDto> origin;
    private List<MagnitudeDto> magnitude;
    private List<AmplitudeDto> amplitude;
    private List<PickDto> pick;
    private List<StationMagnitudeDto> stationMagnitude;
    private List<FocalMechanismDto> focalMechanism;


}
