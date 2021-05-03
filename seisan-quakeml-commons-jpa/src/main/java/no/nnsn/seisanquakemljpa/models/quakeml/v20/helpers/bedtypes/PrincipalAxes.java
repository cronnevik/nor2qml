package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;


import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class PrincipalAxes {
    @Embedded
    private Axis tAxis;
    @Embedded
    private Axis pAxis;
    @Embedded
    private Axis nAxis;
}
