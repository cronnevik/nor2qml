package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.enums;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@NoArgsConstructor
@XmlEnum
public enum RelationType {
    @XmlEnumValue("IsNewVersionOf")
    ISNEWVERSIONOF,
    @XmlEnumValue("IsPreviousVersionOf")
    ISPREVIOUSVERSIONOF,
    @XmlEnumValue("IsDerivedFrom")
    ISDERIVEDFROM,
    @XmlEnumValue("IsSourceOf")
    ISSOURCEOF,
    @XmlEnumValue("Supersedes")
    SUPERSEDES,
    @XmlEnumValue("IsSupersededBy")
    ISSUPERSEDEDBY
}
