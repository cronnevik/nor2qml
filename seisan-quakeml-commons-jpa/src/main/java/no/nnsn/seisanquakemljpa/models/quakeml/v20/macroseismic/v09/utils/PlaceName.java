package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.OpenEpoch;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.enums.PlaceNameType;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaceName {
    private String name;

    @Enumerated(EnumType.STRING)
    private PlaceNameType type;
    @Enumerated(EnumType.STRING)
    private PlaceNameType alternateType;

    private String language;

    @Embedded
    private OpenEpoch epoch;
}
