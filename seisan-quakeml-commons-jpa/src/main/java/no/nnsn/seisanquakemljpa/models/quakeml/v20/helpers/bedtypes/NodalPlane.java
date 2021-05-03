package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class NodalPlane {
    @Embedded
    private RealQuantity strike;
    @Embedded
    private RealQuantity dip;
    @Embedded
    private RealQuantity rake;
}
