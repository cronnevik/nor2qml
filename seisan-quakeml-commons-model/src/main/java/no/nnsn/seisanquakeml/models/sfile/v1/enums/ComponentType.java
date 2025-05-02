package no.nnsn.seisanquakeml.models.sfile.v1.enums;

public enum ComponentType {
    Z("Z"),
    N ("N"),
    E("E"),
    A("A"),
    B("B"),
    C("C"),
    ONE("1"),
    TWO("2"),
    THREE("3"),
    U("U"),
    V("V"),
    W("W"),
    S("S"),
    P("P"),
    R("R"),
    X("X"),
    Y("Y"),
    T("T"),
    H("H");

    ComponentType(String value) {
        this.value = value;
    }

    private final String value;

    public static ComponentType hasType(String comp) {
        for (ComponentType c: values()) {
            if (c.toString().equals(comp)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
