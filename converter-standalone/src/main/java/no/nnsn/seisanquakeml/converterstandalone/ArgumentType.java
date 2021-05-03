package no.nnsn.seisanquakeml.converterstandalone;

public enum ArgumentType {
    TOQUAKEML("q"),
    TOSFILE("s"),
    NORDICVERSION1("nordic"),
    NORDICVERSION2("nordic2"),
    QMLVERSION12("1.2"),
    QMLVERSION20("2.0"),
    QMLSOURCEFILE("file"),
    QMLSOURCEWEB("ws");

    public final String type;

    private ArgumentType(String type) {
        this.type = type;
    }
}
