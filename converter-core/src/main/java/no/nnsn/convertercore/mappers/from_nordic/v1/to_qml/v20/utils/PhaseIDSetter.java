package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils;

import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import org.apache.commons.lang3.StringUtils;

public class PhaseIDSetter {

    public static String getExtentedPhaseID(Line4 line4) {
        String phaseID = !StringUtils.isBlank(line4.getPhaseID()) ? line4.getPhaseID() : "";
        String weighting =!StringUtils.isBlank(line4.getWeightingIndicator()) ? line4.getWeightingIndicator() : "";
        String flagA = !StringUtils.isBlank(line4.getFlagA()) ? line4.getFlagA() : "";
        String firstMotion = !StringUtils.isBlank(line4.getFirstMotion()) ? line4.getFirstMotion() : "";
        String extLastPhaseID = !StringUtils.isBlank(line4.getExtLastPhaseIdChar()) ?line4.getExtLastPhaseIdChar() : "";

        String newPhaseId =
            phaseID +
            weighting +
            flagA +
            firstMotion +
            extLastPhaseID;

        return newPhaseId;
    }
}
