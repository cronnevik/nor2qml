package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportReference {
    private String eqReportID; // ResourceIdentifier
}
