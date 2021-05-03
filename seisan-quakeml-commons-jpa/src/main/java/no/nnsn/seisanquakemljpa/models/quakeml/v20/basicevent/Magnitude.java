package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evMagnitude")
@XmlAccessorType(XmlAccessType.FIELD)
public class Magnitude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    @Embedded
    private RealQuantity mag;

    private String type;
    private String originID; // ResourceReference
    private String methodID; // ResourceReference
    private Integer stationCount;
    private Double azimuthalGap;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "magnitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToMany(mappedBy = "magnitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<StationMagnitudeContribution> stationMagnitudeContribution;

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
                cmt.setMagnitude(this);
            }
        }
    }

    public void setStationMagnitudeContribution(List<StationMagnitudeContribution> stationMagnitudeContributions) {
        this.stationMagnitudeContribution = stationMagnitudeContributions;
        // Hibernate relationship specific
        if (stationMagnitudeContributions != null) {
            for (StationMagnitudeContribution statMagCon: stationMagnitudeContributions) {
                statMagCon.setMagnitude(this);
            }
        }
    }
}
