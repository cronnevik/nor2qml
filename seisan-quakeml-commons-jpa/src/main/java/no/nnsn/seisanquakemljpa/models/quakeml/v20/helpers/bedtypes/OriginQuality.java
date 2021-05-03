package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@Embeddable
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginQuality {
    private Integer associatedPhaseCount;
    private Integer usedPhaseCount;
    private Integer associatedStationCount;
    private Integer usedStationCount;
    private Integer depthPhaseCount;

    private Double standardError;
    private Double azimuthalGap;
    private Double secondaryAzimuthalGap;

    private String groundTruthLevel;
    private Double maximumDistance;
    private Double minimumDistance;
    private Double medianDistance;
}
