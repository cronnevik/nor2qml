package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evParameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventParametersDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String description;
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "eventParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @OneToMany(mappedBy = "eventparams", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<EventDto> event;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setEventParameters(this);
            }
        }
    }

    public void setEvent(List<EventDto> events) {
        this.event = events;
        // Hibernate relationship specific
        if (events != null) {
            for (EventDto event: events) {
                event.setEventparams(this);
            }
        }
    }

}