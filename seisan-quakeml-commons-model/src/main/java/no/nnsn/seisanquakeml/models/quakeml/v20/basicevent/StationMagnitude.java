package no.nnsn.seisanquakeml.models.quakeml.v20.basicevent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class StationMagnitude {
    @XmlTransient
    private String publicID; // ResourceReference

    private String originID; // ResourceReference
    private RealQuantity mag;
    private String type;
    private String amplitudeID; // ResourceReference
    private String methodID; // ResourceReference
    private WaveformStreamID waveformID;
    private CreationInfo creationInfo;

    private List<Comment> comment;
}
