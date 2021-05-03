package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class NodalPlaneDto {

    @Embedded
    private RealQuantityDto strike;
    @Embedded
    private RealQuantityDto dip;
    @Embedded
    private RealQuantityDto rake;

}