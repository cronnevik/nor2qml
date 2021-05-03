package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MtInfo {
    String value;
    int exponent;

    public String getScaledValue(int commonEx) {
        Double dVal = Double.parseDouble(this.value);
        for (int i = 0; i < commonEx; i++) {
            dVal /= 10;
        }
        return String.valueOf(dVal);
    }
}
