package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.enums.SourceTimeFunctionTypeDto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceTimeFunctionDto {

    @Enumerated(EnumType.STRING)
    private SourceTimeFunctionTypeDto type;
    private Double duration;
    private Double riseTime;
    private Double decayTime;
}