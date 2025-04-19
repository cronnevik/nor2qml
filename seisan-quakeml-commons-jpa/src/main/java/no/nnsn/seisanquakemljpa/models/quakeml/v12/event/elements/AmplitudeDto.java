package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.elements;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.AmplitudeCategoryDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.AmplitudeUnitDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationModeDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.enums.EvaluationStatusDto;
import no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;


/**
 * Data transfer object for the Amplitude Entity in QuakeML 1.2
 *
 * @see <a href="https://quake.ethz.ch/quakeml/docs/REC?action=AttachFile&do=view&target=QuakeML-BED-20130214b.pdf">QuakeML 1.2 official documentation</a>
 *
 * @author Christian Rønnevik
 */
@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class AmplitudeDto {

    /**
     * Unique resource identifier for the Amplitude Object.
     *
     * @param publicID Resource Identifier following the generic form:
     *                 {@literal [smi|quakeml]:<authority-id>/<resource-id>[#local-id] }
     * @return The public ID of the Amplitude (QuakeML v1.2) entity.
     */
    @XmlAttribute
    private String publicID;

    /**
     * Generic Amplitude.
     * Data transfer object of RealQuantity for QuakeML v1.2
     *
     * @param genericAmplitude Amplitude
     * @return RealQuantityDto - Object
     */
    private RealQuantityDto genericAmplitude;

    /**
     * Type.
     *
     * @param type String
     * @return String
     */
    private String type;

    /**
     * Category
     * Data transfer object of AmplitudeCategory for QuakeML v1.2
     *
     * @param category Category
     * @return AmplitudeCategoryDto - Enum
     */
    private AmplitudeCategoryDto category;

    /**
     * Unit
     * Data transfer object of AmplitudeUnit for QuakeML v1.2
     *
     * @param unit Unit by Enum type {@link AmplitudeUnitDto}
     * @return AmplitudeUnitDto - Enum
     */
    private AmplitudeUnitDto unit;

    /**
     * ResourceReference for the MethodID represented by a String
     *
     * @param methodID String
     * @return String
     */
    private String methodID;

    private RealQuantityDto period;
    private Double snr;
    private TimeWindowDto timeWindow;
    private String pickID;
    private WaveformStreamIDDto waveformID;
    private String filterID;
    private TimeQuantityDto scalingTime;
    private String magnitudeHint;
    private EvaluationModeDto evaluationMode;
    private EvaluationStatusDto evaluationStatus;
    private CreationInfoDto creationInfo;

    /*
     *   Relations
     */

    private List<CommentDto> comment;
}
