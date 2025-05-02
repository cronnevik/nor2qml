package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.IntegerQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class CompositeTime {
    private IntegerQuantity year;
    private IntegerQuantity month;
    private IntegerQuantity day;
    private IntegerQuantity hour;
    private IntegerQuantity minute;
    private RealQuantity second;
    private String calendar;
}
