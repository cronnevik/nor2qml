package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Axis {
    private RealQuantity azimuth;
    private RealQuantity plunge;
    private RealQuantity length;
}
