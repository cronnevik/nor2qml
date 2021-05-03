package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventTypeCertainty;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the qmlV20_evEvent database table.
 *
 */

@Data
@EqualsAndHashCode(exclude = "sfileEvents")
@Entity
@Table(name = "qmlV20_evEvent")
@Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
@NamedNativeQuery(name = "Event.findEventById", query = "SELECT * FROM qmlV20_evEvent WHERE eventID = ?", resultClass = Event.class)
@XmlAccessorType(XmlAccessType.FIELD)
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "eventID", insertable=false, updatable=false, nullable=false, length=128)
    private String eventID;

    @XmlAttribute
    @Column(name = "publicID", columnDefinition = "VARCHAR(255)", length = 255 )
    private String publicID; // ResourceReference

    private String preferredOriginID; // ResourceReference
    private String preferredMagnitudeID; // ResourceReference
    private String preferredFocalMechanismID; // ResourceReference

    @Enumerated(EnumType.STRING)
    private EventType type;

    @Enumerated(EnumType.STRING)
    private EventTypeCertainty typeCertainty;

    @Embedded
    private CreationInfo creationInfo;

    // Link to catalog
    @XmlTransient
    @Column(name = "columnID", columnDefinition = "VARCHAR(255)", length = 255 )
    private String catalogID;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<EventDescription> description;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Amplitude> amplitude;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<FocalMechanism> focalMechanism;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Magnitude> magnitude;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Origin> origin;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Pick> pick;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<StationMagnitude> stationMagnitude;

    public Event() {}

    /*
     *   Custom setter methods for relations
     */
    public void setAmplitude(List<Amplitude> amplitudes) {
        this.amplitude = amplitudes;
        // Hibernate relationship specific
        if (amplitudes != null) {
            for (Amplitude amp: amplitudes) {
                amp.setEvent(this);
            }
        }
    }


    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setEvent(this);
            }
        }
    }

    public void setDescription(List<EventDescription> descriptions) {
        this.description = descriptions;
        // Hibernate relationship specific
        if (descriptions != null) {
            for (EventDescription evDesc: descriptions) {
                evDesc.setEvent(this);
            }
        }
    }

    public void setFocalMechanism(List<FocalMechanism> focalMechanism) {
        this.focalMechanism = focalMechanism;
        // Hibernate relationship specific
        if (focalMechanism != null) {
            for (FocalMechanism fmech: focalMechanism) {
                fmech.setEvent(this);
            }
        }
    }

    public void setMagnitude(List<Magnitude> magnitudes) {
        this.magnitude = magnitudes;
        // Hibernate relationship specific
        if (magnitudes != null) {
            for (Magnitude mag: magnitudes) {
                mag.setEvent(this);
            }
        }
    }

    public void setOrigin(List<Origin> origins) {
        this.origin = origins;
        if (origin != null) {
           for (Origin origin: origins) {
               origin.setEvent(this);
           }
        }
    }

    public void setPick(List<Pick> picks) {
        this.pick = picks;
        // Hibernate relationship specific
        if (picks != null) {
            for (Pick p: picks) {
                p.setEvent(this);
            }
        }
    }

    public void setStationMagnitude(List<StationMagnitude> stationMagnitudes) {
        this.stationMagnitude = stationMagnitudes;
        // Hibernate relationship specific
        if (stationMagnitudes != null) {
            for (StationMagnitude statMag: stationMagnitudes) {
                statMag.setEvent(this);
            }
        }
    }

}
