package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.OriginDto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qml_v12_composite_time")
@XmlAccessorType(XmlAccessType.FIELD)
public class CompositeTimeDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer compositeTimeID;

    @Embedded
    private IntegerQuantityDto year;
    @Embedded
    private IntegerQuantityDto month;
    @Embedded
    private IntegerQuantityDto day;
    @Embedded
    private IntegerQuantityDto hour;
    @Embedded
    private IntegerQuantityDto minute;
    @Embedded
    private RealQuantityDto second;

    /*
     *   Relations
     */

    @ManyToOne
    @XmlTransient
    private OriginDto origin;

}