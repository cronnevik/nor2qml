package no.nnsn.seisanquakeml.models.sfile.v1.enums;

public enum PropertyIdType {
    PROPERTY_WEIGHT("seisan-weight");

    private String propertyIdtype;

    PropertyIdType(String type) {this.propertyIdtype = type;}

    public String getPropertyIdtype() {return this.propertyIdtype;}
    public void setPropertyIdtype(String propertyIdtype) {
        this.propertyIdtype = propertyIdtype;
    }
    public boolean equalValue(String passedValue){
        return this.propertyIdtype.equals(passedValue);
    }
}
