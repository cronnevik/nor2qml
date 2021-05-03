package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evStationMag")
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlTransient
    private String publicID; // ResourceReference

    private String originID; // ResourceReference

    @Embedded
    private RealQuantity mag;

    private String type;

    private String amplitudeID; // ResourceReference
    private String methodID; // ResourceReference

    @Embedded
    private WaveformStreamID waveformID;
    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "stationMagnitude", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setStationMagnitude(this);
            }
        }
    }
}
