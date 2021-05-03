package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CommentDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.CreationInfoDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.RealQuantityDto;
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
@Table(name = "qmlV12_evMagnitude")
@XmlAccessorType(XmlAccessType.FIELD)
public class MagnitudeDto {
    @Id
    @XmlAttribute
    private String publicID;

    @Embedded
    private RealQuantityDto mag;

    private String type;
    private String originID;
    private String methodID;
    private Integer stationCount;
    private Double azimuthalGap;

    @Enumerated(EnumType.STRING)
    private EvaluationModeDto evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatusDto evaluationStatus;

    @Embedded
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "magnitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @ManyToOne
    @XmlTransient
    private EventDto event;

    @OneToMany(mappedBy = "magnitude", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<StationMagnitudeContributionDto> stationMagnitudeContribution;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setMagnitude(this);
            }
        }
    }

    public void setStationMagnitudeContributions(List<StationMagnitudeContributionDto> stationMagnitudeContributions) {
        this.stationMagnitudeContribution = stationMagnitudeContributions;
        // Hibernate relationship specific
        if (stationMagnitudeContribution != null) {
            for (StationMagnitudeContributionDto statMagCont: stationMagnitudeContributions) {
                statMagCont.setMagnitude(this);
            }
        }
    }
}