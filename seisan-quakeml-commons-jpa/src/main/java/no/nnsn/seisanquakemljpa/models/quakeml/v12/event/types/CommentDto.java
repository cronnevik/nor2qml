package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.*;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_rmComment")
@XmlAccessorType(XmlAccessType.FIELD)
public class CommentDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer commentID;

    private String text;
    private String id;
    @Embedded
    private CreationInfoDto creationInfo;


    /*
     *   Relations
     */

    @ManyToOne
    @XmlTransient
    private AmplitudeDto amplitude;

    @ManyToOne
    @XmlTransient
    private ArrivalDto arrival;

    @ManyToOne
    @XmlTransient
    private EventDto event;

    @ManyToOne
    @XmlTransient
    private EventParametersDto eventParameters;

    @ManyToOne
    @XmlTransient
    private FocalMechanismDto focalMechanism;

    @ManyToOne
    @XmlTransient
    private MagnitudeDto magnitude;

    @ManyToOne
    @XmlTransient
    private MomentTensorDto momentTensor;

    @ManyToOne
    @XmlTransient
    private OriginDto origin;

    @ManyToOne
    @XmlTransient
    private PickDto pick;

    @ManyToOne
    @XmlTransient
    private StationMagnitudeDto stationMagnitude;

}
