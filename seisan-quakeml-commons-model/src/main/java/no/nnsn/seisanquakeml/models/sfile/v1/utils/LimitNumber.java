package no.nnsn.seisanquakeml.models.sfile.v1.utils;

import lombok.Data;

@Data
public class LimitNumber<T> {
    private T lower, upper;

    private LimitNumber(T lower, T upper) {
        this.lower = lower;
        this.upper = upper;
    }

    private LimitNumber(T lower) {
        this.lower = lower;
    }

    public static LimitNumber<Integer> ofInt(Integer lower, Integer upper) {
        return new LimitNumber<>(lower, upper);
    }
    public static LimitNumber<Integer> ofInt(Integer lower) {
        return new LimitNumber<>(lower);
    }

    public static LimitNumber<Double> ofDouble(Double lower, Double upper) {
        return new LimitNumber<>(lower, upper);
    }
    public static LimitNumber<Double> ofDouble(Double lower) {
        return new LimitNumber<>(lower);
    }
}
