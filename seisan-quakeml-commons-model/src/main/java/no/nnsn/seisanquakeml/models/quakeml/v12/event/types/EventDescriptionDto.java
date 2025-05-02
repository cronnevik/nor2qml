package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.EventDescriptionTypeDto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class EventDescriptionDto {
    private String text;
    @Enumerated(EnumType.STRING)
    private EventDescriptionTypeDto type;
}
