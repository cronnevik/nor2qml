package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.MDP;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.MDPReference;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.MDPSet;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlV20_rmComment")
@XmlAccessorType(XmlAccessType.FIELD)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer commentId;

    private String text;
    private String id;

    @Embedded
    private CreationInfo creationInfo;

    /*
     *   Relations Event
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Amplitude amplitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Arrival arrival;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private FocalMechanism focalMechanism;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Magnitude magnitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MomentTensor momentTensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Origin origin;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Pick pick;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private StationMagnitude stationMagnitude;

    /*
     *   Relations Macroseismic
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDP mdp;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDPReference mdpReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private MDPSet mdpSet;

    /*
     *   Relations Resource Metadata
     */

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private AnnotatedLiteratureSource annotatedLiteratureSource;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Author author;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private InstitutionalAffiliation institutionalAffiliation;

    @OneToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private PersonalAffiliation personalAffiliation;

}
