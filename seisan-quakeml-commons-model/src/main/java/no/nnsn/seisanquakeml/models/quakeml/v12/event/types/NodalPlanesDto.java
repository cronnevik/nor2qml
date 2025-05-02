package no.nnsn.seisanquakeml.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class NodalPlanesDto {
    private NodalPlaneDto nodalPlane1;
    private NodalPlaneDto nodalPlane2;
    private Integer preferredPlane;

}