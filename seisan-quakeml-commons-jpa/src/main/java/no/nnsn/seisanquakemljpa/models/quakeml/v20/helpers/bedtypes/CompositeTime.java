package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.IntegerQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@Entity
@Table(name = "qmlv20_composite_time")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompositeTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer compositeTimeID;

    private IntegerQuantity year;
    private IntegerQuantity month;
    private IntegerQuantity day;
    private IntegerQuantity hour;
    private IntegerQuantity minute;
    private RealQuantity second;
    private String calendar;


    /*
     *   Relations
     */

    @XmlTransient
    @ManyToOne(fetch = FetchType.LAZY)
    private Origin origin;

}
