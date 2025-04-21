package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationMode;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.enums.EvaluationStatus;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.LiteratureSource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.RelatedResource;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09.utils.MacroseismicIntensity;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MDP {

    @XmlAttribute
    private String publicID; // ResourceIdentifier

    private String placeReference; // ResourceIdentifier
    private MacroseismicIntensity intensity;
    private RelatedResource relatedVDP;

    private Integer reportCount;
    private TimeQuantity reportedTime;
    private String eventReference; // ResourceIdentifier
    private String methodID; // ResourceIdentifier
    private String quality;

    @Enumerated(EnumType.STRING)
    private EvaluationMode evaluationMode;
    @Enumerated(EnumType.STRING)
    private EvaluationStatus evaluationStatus;

    private CreationInfo creationInfo;

    private List<Comment> comment;
    private LiteratureSource literatureSource;
    private List<ReportReference> reportReference;
}
