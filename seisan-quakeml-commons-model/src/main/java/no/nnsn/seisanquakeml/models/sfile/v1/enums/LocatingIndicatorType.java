package no.nnsn.seisanquakeml.models.sfile.v1.enums;

public enum LocatingIndicatorType {
    FIXED("F"),
    STARTING_VALUE("S"),
    DO_NOT_LOCATE("*");

    LocatingIndicatorType (String value) { this.value = value; }

    private final String value;

    public static LocatingIndicatorType hasType(String loc) {
        for (LocatingIndicatorType l: values()) {
            if (l.toString().equals(loc)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
