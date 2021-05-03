package no.nnsn.seisanquakemljpa.models.catalog;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"sfile", "catalog"})
@Entity(name = "SfileEvent")
@Table(name = "sfile_event")
public class SfileEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String eventID;

    @ManyToOne(fetch=FetchType.LAZY)
    @XmlTransient
    private SfileCheck sfile;

    private String time;

    private Double latitude;
    private Double longitude;
    private Double radius;
    private Double depth;

    private String author;
    @ManyToOne(fetch=FetchType.EAGER) // Unidirectional
    @XmlTransient
    private Catalog catalog;

    private String contributor;
    private String contributorID;

    private String magType;
    private Double magnitude;
    private String magAuthor;

    private String locationName;
    @Enumerated(EnumType.STRING)
    private EventType type;

}
