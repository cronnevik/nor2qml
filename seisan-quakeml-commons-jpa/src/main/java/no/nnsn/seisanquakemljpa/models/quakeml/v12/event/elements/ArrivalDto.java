package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
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
@Table(name = "qmlV12_evArrival")
@XmlAccessorType(XmlAccessType.FIELD)
public class ArrivalDto {
    @Id
    @XmlAttribute
    private String publicID;

    private String pickID;
    // QuakeML 1.2 has Phase(with "code" property) obj instead of string, while most use just string
    private String phase;
    private Double timeCorrection;
    private Double azimuth;
    private Double distance;

    @Embedded
    private RealQuantityDto takeoffAngle;

    private Double timeResidual;
    private Double horizontalSlownessResidual;
    private Double backazimuthResidual;
    private Double timeWeight;
    private Double horizontalSlownessWeight;
    private Double backazimuthWeight;
    private String earthModelID;

    @Embedded
    private CreationInfoDto creationInfo;

    private Integer timeUsed;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "arrival", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    private List<CommentDto> comment;

    @ManyToOne
    @XmlTransient
    private OriginDto origin;


    /*
     *   Custom setter methods for relations
     */

    public void setComment(List<CommentDto> comments) {
        this.comment = comments;
        // Hibernate relationship specific
        if (comments != null) {
            for (CommentDto cmt: comments) {
                cmt.setArrival(this);
            }
        }
    }

}
