package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EventTypeCertaintyDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EventTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.EventDescriptionDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evEvent")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String preferredOriginID;
    private String preferredMagnitudeID;
    private String preferredFocalMechanismID;

    @Enumerated(EnumType.STRING)
    private EventTypeDto type;
    @Enumerated(EnumType.STRING)
    private EventTypeCertaintyDto typeCertainty;

    @Embedded
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EventDescriptionDto> description;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonMerge(OptBoolean.TRUE)
    public List<OriginDto> origin;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MagnitudeDto> magnitude;
    @OneToMany(mappedBy= "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<AmplitudeDto> amplitude;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<PickDto> pick;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<StationMagnitudeDto> stationMagnitude;
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<FocalMechanismDto> focalMechanism;

    @ManyToOne
    @XmlTransient
    private EventParametersDto eventparams;


    /*
     *   Custom setter methods for relations
     */

    public void setDescription(List<EventDescriptionDto> descriptions) {
        this.description = descriptions;
        // Hibernate relationship specific
        if (descriptions != null) {
            for (EventDescriptionDto evDesc: descriptions) {
                evDesc.setEvent(this);
            }
        }
    }

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setEvent(this);
            }
        }
    }

    public void setOrigin(List<OriginDto> origins) {
        if (origin == null) {
            origin = new ArrayList<>(origins.size());
        }
        origin.addAll(origins);
    }
    public void setMagnitude(List<MagnitudeDto> magnitudes) {
        this.magnitude = magnitudes;
        // Hibernate relationship specific
        if (magnitudes != null) {
            for (MagnitudeDto mag: magnitudes) {
                mag.setEvent(this);
            }
        }
    }

    public void setAmplitude(List<AmplitudeDto> amplitudes) {
        this.amplitude = amplitudes;
        // Hibernate relationship specific
        if (amplitudes != null) {
            for (AmplitudeDto amp: amplitudes) {
                amp.setEvent(this);
            }
        }
    }

    public void setPick(List<PickDto> picks) {
        this.pick = picks;
        // Hibernate relationship specific
        if (picks != null) {
            for (PickDto pick: picks) {
                pick.setEvent(this);
            }
        }
    }

    public void setStationMagnitude(List<StationMagnitudeDto> stationMagnitudes) {
        this.stationMagnitude = stationMagnitudes;
        // Hibernate relationship specific
        if (stationMagnitudes != null) {
            for (StationMagnitudeDto smag: stationMagnitudes) {
                smag.setEvent(this);
            }
        }
    }

    public void setFocalMechanism(List<FocalMechanismDto> focalMechanism) {
        this.focalMechanism = focalMechanism;
        // Hibernate relationship specific
        if (focalMechanism != null) {
            for (FocalMechanismDto fmech: focalMechanism) {
                fmech.setEvent(this);
            }
        }
    }

}
