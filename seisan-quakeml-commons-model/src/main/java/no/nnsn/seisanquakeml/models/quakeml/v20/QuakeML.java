package no.nnsn.seisanquakeml.models.quakeml.v20;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.EventParameters;
import no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09.MacroseismicParameters;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@NoArgsConstructor
@XmlRootElement(name = "quakeml")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuakeML {

    @XmlElement(name = "eventParameters")
    private EventParameters eventParameters;

    @XmlElement(name = "macroseismicParameters")
    private MacroseismicParameters macroseismicParameters;

}
