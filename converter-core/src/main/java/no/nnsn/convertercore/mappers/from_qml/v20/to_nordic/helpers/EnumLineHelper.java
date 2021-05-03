package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.EnumLineQualifiers;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;

public class EnumLineHelper {
    @EnumLineQualifiers.DepthTypeToDepthIndicator
    public String mapDepthIndicator(OriginDepthType type) {
        switch (type) {
            case OPERATOR_ASSIGNED:
                return "F";
            case FROM_LOCATION:
                return " ";
            default:
                return "S";
        }
    }
}
