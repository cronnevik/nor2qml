package no.nnsn.quakemlwebservice.helper;

import lombok.Data;

@Data
public class EnumWrapper<T extends Enum<T>> {
    private String value;
    private Class<T> type;

    public EnumWrapper(String value) {
        this.value = value;
    }
}