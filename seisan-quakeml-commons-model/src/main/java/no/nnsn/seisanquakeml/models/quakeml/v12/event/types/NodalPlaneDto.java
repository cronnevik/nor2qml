package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NodalPlaneDto {
    private RealQuantityDto strike;
    private RealQuantityDto dip;
    private RealQuantityDto rake;
}