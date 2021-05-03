package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers.*;

/**
 * Helper class for general conversion of primitive values, but with null check, and a special boolean case.
 *
 * @author Christian Rønnevik
 */
public class GeneralHelper {

    /*
     * General conversions
     *
     */

    @StringToInteger
    public Integer mapInteger(String value){
        return value != null && !value.isEmpty() ? Integer.parseInt(value, 10) : null;
    }

    @StringToDouble
    public Double mapDouble(String value){
        return value != null && !value.isEmpty() ? Double.parseDouble(value) : null;
    }

    @StringToBoolean
    public Boolean mapBoolean(String val) {
        switch (val) {
            case "F":
                return true;
            case "":
                return false;
            case "S":
                return null;
            case "*":
                return true;
            default:
                return null;
        }
    }


}
