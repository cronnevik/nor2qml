package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.OriginQuality;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
@Table(name = "qmlV20_evOrigin")
@XmlAccessorType(XmlAccessType.FIELD)
public class Origin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference

    @Embedded
    private TimeQuantity time;
    @Embedded
    private RealQuantity latitude;
    @Embedded
    private RealQuantity longitude;

    @Embedded
    private RealQuantity depth;

    @Enumerated(EnumType.STRING)
    private OriginType type;

    @Enumerated(EnumType.STRING)
    private OriginDepthType depthType;

    private Boolean epicenterFixed;
    private Boolean timeFixed;
    private String methodID; // ResourceReference
    private String earthModelID; // ResourceReference
    private String referenceSystemID; // ResourceReference

    @Embedded
    private OriginQuality quality;

    private String region;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    @Embedded
    private CreationInfo creationInfo;

    /*
     *   Relations
     */

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<CompositeTime> compositeTime;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @OneToOne(mappedBy = "origin",cascade =  CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private OriginUncertainty originUncertainty;

    @OneToMany(mappedBy = "origin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Arrival> arrival;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setOrigin(this);
            }
        }
    }

    public void setCompositeTime(List<CompositeTime> compositeTimes) {
        this.compositeTime = compositeTimes;
        // Hibernate relationship specific
        if (compositeTimes != null) {
            for (CompositeTime ctime: compositeTimes) {
                ctime.setOrigin(this);
            }
        }
    }

    public void setOriginUncertainty(OriginUncertainty originUncertainty) {
        this.originUncertainty = originUncertainty;
        // Hibernate relationship specific
        if (originUncertainty != null) {
            originUncertainty.setOrigin(this);
        }
    }

    public void setArrival(List<Arrival> arrivals) {
        this.arrival = arrivals;
        // Hibernate relationship specific
        if (arrivals != null) {
            for (Arrival arr: arrivals) {
                arr.setOrigin(this);
            }
        }
    }

    /*
     *   Custom setter
     */

    public void setEpicenterFixed(Boolean value) {
        this.epicenterFixed = value;
    }

    public void setEpicenterFixed(String str) {

        // Fix error in format when deserialize from isc.ac.uk (uses 0 and 1, when it is suppose to be true or false)
        if (str != null) {
            if ("0".equals(str)) {
                this.epicenterFixed = false;
            } else if("1".equals(str)) {
                this.epicenterFixed = true;
            } else if("true".equals(str)) {
                this.epicenterFixed = true;
            } else if("false".equals(str)) {
                this.epicenterFixed = false;
            }
        }

    }
}
