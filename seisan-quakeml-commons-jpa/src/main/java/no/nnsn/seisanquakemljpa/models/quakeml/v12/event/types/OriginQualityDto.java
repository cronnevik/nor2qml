package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginQualityDto {

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