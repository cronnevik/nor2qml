package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventTypeCertainty;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class Event {

    @XmlAttribute
    private String publicID; // ResourceReference

    private String preferredOriginID; // ResourceReference
    private String preferredMagnitudeID; // ResourceReference
    private String preferredFocalMechanismID; // ResourceReference

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Enumerated(EnumType.STRING)
    private EventTypeCertainty typeCertainty;

    private CreationInfo creationInfo;


    private List<Comment> comment;
    private List<EventDescription> description;
    private List<Amplitude> amplitude;
    private List<FocalMechanism> focalMechanism;
    private List<Magnitude> magnitude;
    private List<Origin> origin;
    private List<Pick> pick;
    private List<StationMagnitude> stationMagnitude;

    // Link to catalog
    @XmlTransient
    private String catalogID;
    @XmlTransient
    private String eventID;
}
