package no.nnsn.quakemlwebservice.dao;

public enum OrderByType {
    TIME("time"),
    TIME_ASC("time-asc"),
    MAGNITUDE("magnitude"),
    MAGNITUDE_ASC("magnitude-asc");

    private final String name;

    private OrderByType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static OrderByType fromString(final String s) {
        switch (s) {
            case "time":
                return OrderByType.TIME;
            case "time-asc":
                return OrderByType.TIME_ASC;
            case "magnitude":
                return OrderByType.MAGNITUDE;
            case "magnitude-asc":
                return OrderByType.MAGNITUDE_ASC;
            default:
                return null;
        }
    }

}
