package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.helpers.JaxbBooleanSerializer;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.OriginDepthTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.OriginTypeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evOrigin")
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginDto {
    @Id
    @XmlAttribute
    private String publicID;

    @Embedded
    private TimeQuantityDto time;
    @Embedded
    private RealQuantityDto longitude;
    @Embedded
    private RealQuantityDto latitude;
    @Embedded
    private RealQuantityDto depth;

    @Enumerated(EnumType.STRING)
    private OriginDepthTypeDto depthType;

    @XmlJavaTypeAdapter(JaxbBooleanSerializer.class)
    private Boolean timeFixed;
    @XmlJavaTypeAdapter(JaxbBooleanSerializer.class)
    private Boolean epicenterFixed;
    private String referenceSystemID;
    private String methodID;
    private String earthModelID;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CompositeTimeDto> compositeTime;

    @Embedded
    private OriginQualityDto quality;
    @Enumerated(EnumType.STRING)
    private OriginTypeDto type;
    private String region;

    @Enumerated(EnumType.STRING)
    private EvaluationModeDto evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatusDto evaluationStatus;

    @Embedded
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ArrivalDto> arrival;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @ManyToOne
    @XmlTransient
    private EventDto event;

    @OneToOne(mappedBy = "origin", cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    private OriginUncertaintyDto originUncertainty;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setOrigin(this);
            }
        }
    }

    public void setArrival(List<ArrivalDto> arrivals) {
        this.arrival = arrivals;
        // Hibernate relationship specific
        if (arrivals != null) {
            for (ArrivalDto arr: arrivals) {
                arr.setOrigin(this);
            }
        }
    }

    public void setOriginUncertainty(OriginUncertaintyDto originUncertainty) {
        this.originUncertainty = originUncertainty;
        // Hibernate relationship specific
        if (originUncertainty != null) {
            originUncertainty.setOrigin(this);
        }
    }
}


