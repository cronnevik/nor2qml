package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.DCMITypeURI;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.LanguageCodeURI;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.enums.BibtexEntryType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class LiteratureSource {

    private String identifier; // ResourceReference
    private DCMITypeURI type;
    @Enumerated(EnumType.STRING)
    private BibtexEntryType bibtexType;

    private LanguageCodeURI language;
    private String title;

    private String author;
    private String editor;
    private String bibliographicCitation;
    private String date;
    private String booktitle;
    private String volume;
    private String number;
    private String series;
    private String issue;
    private String year;
    private String edition;
    private String startPage;
    private String endPage;
    private String publisher;
    private String address;
    private String rights;
    private String rightsHolder;
    private String accessRights;
    private String license;
    private String publicationStatus;

    private Author creator;
}
