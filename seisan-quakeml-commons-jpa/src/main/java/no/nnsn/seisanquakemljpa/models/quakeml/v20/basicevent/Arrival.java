package no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "qmlV20_evArrival")
@XmlAccessorType(XmlAccessType.FIELD)
public class Arrival implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @XmlAttribute
    private String publicID; // ResourceReference
    @XmlElement
    private String pickID; // ResourceReference
    // QuakeML has Phase(with "code" property) obj instead of string, while most use just string
    @XmlElement
    private String phase;

    @XmlElement
    private Double timeCorrection;
    @XmlElement
    private Double azimuth;
    @XmlElement
    private Double distance;

    @Embedded
    @XmlElement
    private RealQuantity takeoffAngle;

    @XmlElement
    private Double timeResidual;
    @XmlElement
    private Double horizontalSlownessResidual;
    @XmlElement
    private Double backazimuthResidual;
    @XmlElement
    private Double timeWeight;
    @XmlElement
    private Double horizontalSlownessWeight;
    @XmlElement
    private Double backazimuthWeight;
    @XmlElement
    private String earthModelID; // ResourceReference

    @Embedded
    @XmlElement
    private CreationInfo creationInfo;

    /*
     *   Relations
     */

    @XmlElement
    @OneToMany(mappedBy = "arrival", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Comment> comment;

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    Origin origin;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<Comment> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (Comment cmt: comments) {
                cmt.setArrival(this);
            }
        }
    }

}
