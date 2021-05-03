package no.nnsn.quakemlwebservice.dao;

public enum NoDataType {
    TWONULLFOUR(204),
    FOURNULLFOUR(404);

    private final int name;

    NoDataType(int name) {
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public static NoDataType fromString(final int n) {
        switch (n) {
            case 204:
                return NoDataType.TWONULLFOUR;
            case 404:
                return NoDataType.FOURNULLFOUR;
            default:
                return null;
        }
    }
}
