package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum OriginDepthType {
    @XmlEnumValue("from location")
    FROM_LOCATION,
    @XmlEnumValue("from moment tensor inversion")
    FROM_MOMENT_TENSOR_INVERSION,
    @XmlEnumValue("broad band P waveforms")
    BROAD_BAND_P_WAVEFORMS,
    @XmlEnumValue("constrained by depth phases")
    CONSTRAINED_BY_DEPTH_PHASES,
    @XmlEnumValue("constrained by direct phases")
    CONSTRAINED_BY_DIRECT_PHASES,
    @XmlEnumValue("constrained by depth and direct phases")
    CONSTRAINED_BY_DEPTH_AND_DIRECT_PHASES,
    @XmlEnumValue("operator assigned")
    OPERATOR_ASSIGNED,
    @XmlEnumValue("other")
    OTHER_ORIGIN_DEPTH;
}
