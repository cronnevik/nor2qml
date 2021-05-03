package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.EventDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EventDescriptionTypeDto;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV12_evDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDescriptionDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer eventDescriptionID;

    private String text;
    @Enumerated(EnumType.STRING)
    private EventDescriptionTypeDto type;

    /*
     *   Custom Methods
     */

    public EventDescriptionDto(String text, EventDescriptionTypeDto type) {
        this.text = text;
        this.type = type;
    }

    /*
     *   Relations
     */

    @ManyToOne
    @XmlTransient
    private EventDto event;

}
