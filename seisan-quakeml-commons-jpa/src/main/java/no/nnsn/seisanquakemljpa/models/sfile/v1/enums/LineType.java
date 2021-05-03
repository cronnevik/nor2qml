package no.nnsn.seisanquakemljpa.models.sfile.v1.enums;

public enum LineType {
    LINETYPE_3("seisan-linetype-3"),
    LINETYPE_5("seisan-linetype-5"),
    LINETYPE_6("seisan-linetype-6"),
    LINETYPE_I("seisan-linetype-I"),
    LINETYPE_S("seisan-linetype-S");


    private String lineType;

    LineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public boolean equalValue(String passedValue){
        return this.lineType.equals(passedValue);
    }
}
