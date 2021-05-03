package no.nnsn.seisanquakemljpa.models.quakeml.v12;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements.EventParametersDto;

import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@XmlRootElement(name = "quakeml")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuakeMLDto {

    @XmlAttribute
    private String xmlns = "http://quakeml.org/xmlns/bed/1.2";

    @XmlElement(name = "eventParameters")
    private EventParametersDto eventParameters;

}
