package no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.DataUsedWaveType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class DataUsed {
    private Integer componentCount;
    private Double longestPeriod;
    private Double shortestPeriod;
    private Integer stationCount;

    @Enumerated(EnumType.STRING)
    private DataUsedWaveType waveType;
}
