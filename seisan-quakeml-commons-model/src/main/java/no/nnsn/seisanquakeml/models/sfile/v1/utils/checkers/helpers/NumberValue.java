package no.nnsn.seisanquakeml.models.sfile.v1.utils.checkers.helpers;

import lombok.Data;

@Data
public class NumberValue<T> {
    private T value;

    private NumberValue(T value) {
        this.value = value;
    }

    public static NumberValue<Integer> ofInt(Integer value) {
        return new NumberValue<>(value);
    }
    public static NumberValue<Double> ofDouble(Double value) {
        return new NumberValue<>(value);
    }
}
