package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers;

public class GeneralLineHelper {
    /*
     * General conversions
     *
     */

    @GeneralLineQualifiers.DoubleToString
    public String mapDouble(Double value){
        return value != null && !value.isNaN() ? Double.toString(value) : null;
    }

    @GeneralLineQualifiers.IntegerToString
    public String mapInteger(Integer value){
        return value != null ? Integer.toString(value) : null;
    }
}
