package no.nnsn.seisanquakeml.models.sfile.v1.enums;

public enum DescriptionType {
    EVENT_LOCALITY("event_locality"),
    EVENT_TYPE("event_type"),
    EVENT_COMMENT("event_comment");

    private String descriptionType;

    DescriptionType(String type) {
        this.descriptionType = type;
    }

    public String getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(String descriptionType) {
        this.descriptionType = descriptionType;
    }
}
