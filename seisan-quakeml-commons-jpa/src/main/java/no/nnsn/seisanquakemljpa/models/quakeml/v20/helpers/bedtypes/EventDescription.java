package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@Data
@NoArgsConstructor
@Entity
@Table(name = "qmlV20_evDescription")
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @XmlTransient
    private Integer eventDescriptionID;

    private String text;
    @Enumerated(EnumType.STRING)
    private EventDescriptionType type;

    /*
     *   Custom Methods
     */

    public EventDescription(String text, EventDescriptionType type) {
        this.text = text;
        this.type = type;
    }

    /*
     *   Relations
     */

    @ManyToOne(fetch = FetchType.LAZY)
    @XmlTransient
    private Event event;

}
