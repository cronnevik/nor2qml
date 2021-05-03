package no.nnsn.quakemlwebservice.dao;

public enum FormatType {
    XML("xml"),
    TEXT("text"),
    QUAKEML20("quakeml20"),
    NORDIC("nordic");

    private final String name;

    private FormatType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public static FormatType fromString(final String s) {
        switch (s) {
            case "xml":
                return FormatType.XML;
            case "text":
                return FormatType.TEXT;
            case "quakeml20":
                return FormatType.QUAKEML20;
            case "nordic":
                return FormatType.NORDIC;
            default:
                return null;
        }
    }
}
