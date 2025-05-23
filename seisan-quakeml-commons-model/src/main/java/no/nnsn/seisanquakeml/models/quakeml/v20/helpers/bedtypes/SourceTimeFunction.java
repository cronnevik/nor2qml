package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.SourceTimeFunctionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class SourceTimeFunction {
    private Double decayTime;
    private Double duration;
    private Double riseTime;
    @Enumerated(EnumType.STRING)
    private SourceTimeFunctionType type;
}
