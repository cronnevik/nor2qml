package no.nnsn.seisanquakeml.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.types.CreationInfoDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EventParametersDto {

    @XmlAttribute
    private String publicID;

    private String description;
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    private List<CommentDto> comment;
    private List<EventDto> event;

}