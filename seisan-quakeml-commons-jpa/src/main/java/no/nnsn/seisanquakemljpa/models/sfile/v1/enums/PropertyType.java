package no.nnsn.seisanquakemljpa.models.sfile.v1.enums;

public enum PropertyType {
    STRING("String"),
    INTEGER("Integer"),
    DOUBLE("Double"),
    DOUBLEORSTRING("DoubleOrString"),
    EXPONENTIAL("Exponential"),
    DOUBLEOREXPONENTIAL("DoubleOrExponential");

    private String propertyType;

    PropertyType(String type) {
        this.propertyType = type;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
