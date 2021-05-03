package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class PrincipalAxesDto {

    @Embedded
    private AxisDto tAxis;
    @Embedded
    private AxisDto pAxis;
    @Embedded
    private AxisDto nAxis;

}