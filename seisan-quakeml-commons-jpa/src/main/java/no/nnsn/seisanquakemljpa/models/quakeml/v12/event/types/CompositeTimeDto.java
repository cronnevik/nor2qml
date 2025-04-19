package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CompositeTimeDto {
    private IntegerQuantityDto year;
    private IntegerQuantityDto month;
    private IntegerQuantityDto day;
    private IntegerQuantityDto hour;
    private IntegerQuantityDto minute;
    private RealQuantityDto second;
}