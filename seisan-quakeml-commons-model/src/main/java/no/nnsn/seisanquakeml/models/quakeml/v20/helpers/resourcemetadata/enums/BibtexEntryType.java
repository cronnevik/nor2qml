package no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.enums;

import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@NoArgsConstructor
@XmlEnum
public enum BibtexEntryType {
    @XmlEnumValue("article")
    ARTICLE,
    @XmlEnumValue("book")
    BOOK,
    @XmlEnumValue("booklet")
    BOOKLET,
    @XmlEnumValue("conference")
    CONFERENCE,
    @XmlEnumValue("inbook")
    INBOOK,
    @XmlEnumValue("incollection")
    INCOLLECTION,
    @XmlEnumValue("inproceedings")
    INPROCEEDINGS,
    @XmlEnumValue("manual")
    MANUAL,
    @XmlEnumValue("mastersthesis")
    MASTERSTHESIS,
    @XmlEnumValue("misc")
    MISC,
    @XmlEnumValue("phdthesis")
    PHDTHESIS,
    @XmlEnumValue("proceedings")
    PROCEEDINGS,
    @XmlEnumValue("techreport")
    TECHREPORT,
    @XmlEnumValue("unpublished")
    UNPUBLISHED
}
