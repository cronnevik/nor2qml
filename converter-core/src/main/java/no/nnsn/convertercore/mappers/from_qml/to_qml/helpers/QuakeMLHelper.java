package no.nnsn.convertercore.mappers.from_qml.to_qml.helpers;

import no.nnsn.convertercore.mappers.from_qml.to_qml.annotations.QuakeMLQualifiers;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;

public class QuakeMLHelper {
    @QuakeMLQualifiers.RealQuantityToDouble
    public Double mapDouble(RealQuantity rq) {
        return (rq != null && rq.getValue() != null) ? rq.getValue() : null;
    }

    @QuakeMLQualifiers.DoubleToRealQuantity
    public RealQuantity mapRealQuantity(Double val) {
        return val != null ? new RealQuantity(val) : null;
    }
}
