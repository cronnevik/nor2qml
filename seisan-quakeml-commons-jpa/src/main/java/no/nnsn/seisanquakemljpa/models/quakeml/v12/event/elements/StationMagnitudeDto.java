package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.RealQuantityDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.WaveformStreamIDDto;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evStationMag")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitudeDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String originID;
    @Embedded
    private RealQuantityDto mag;
    private String type;
    private String amplitudeID;
    private String methodID;
    @Embedded
    private WaveformStreamIDDto waveformID;

    @Embedded
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "stationMagnitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @ManyToOne
    @XmlTransient
    private EventDto event;

    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setStationMagnitude(this);
            }
        }
    }

}
