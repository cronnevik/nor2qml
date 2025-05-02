package no.nnsn.seisanquakeml.models.sfile.v1.lines;

import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
public class LineH extends Line{

    /**
     * Year is instantiated by a {@link PropertyObject} with 4 characters and value type of {@link Integer}.
     * Character columns 2-5.
     */
    private final PropertyObject year =
            new PropertyObject("Year", 2, 5, PropertyType.INTEGER);

    /**
     * Month is instantiated by a {@link PropertyObject} with 2 characters and value type of {@link Integer}.
     * Character columns 7-8.
     */
    private final PropertyObject month =
            new PropertyObject("Month", 7, 8, PropertyType.INTEGER);

    /**
     * Day of month is instantiated by a {@link PropertyObject} with 2 characters and value type of {@link Integer}.
     * Character columns 9-10.
     */
    private final PropertyObject day =
            new PropertyObject("Day of Month",9, 10, PropertyType.INTEGER);

    /**
     * Fix o. time is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value is normally blank, but an F fixes origin time.
     * Character column 11.
     */
    private final PropertyObject fixOfTime =
            new PropertyObject("Fix o. time", 11,11, PropertyType.STRING);

    /**
     * Hour is instantiated by a {@link PropertyObject} with 2 characters and value type of {@link Integer}.
     * Character columns 12-13.
     */
    private final PropertyObject hour =
            new PropertyObject("Hour", 12, 13, PropertyType.INTEGER);

    /**
     * Minutes is instantiated by a {@link PropertyObject} with 2 characters and value type of {@link Integer}.
     * Character columns 14-15.
     */
    private final PropertyObject minutes =
            new PropertyObject("Minutes", 14, 15, PropertyType.INTEGER);

    private final PropertyObject seconds =
            new PropertyObject("Seconds", 17, 22, PropertyType.DOUBLE, 3);

    private final PropertyObject latitude =
            new PropertyObject("Latitude",24, 32, PropertyType.DOUBLE, 5);

    private final PropertyObject longitude =
            new PropertyObject("Longitude",34, 43, PropertyType.DOUBLE, 5);

    private final PropertyObject depth =
            new PropertyObject("Depth",45, 52, PropertyType.DOUBLE, 3);

    private final PropertyObject rms =
            new PropertyObject("RMS",54, 59, PropertyType.DOUBLE, 3);

    private final PropertyObject freeText =
            new PropertyObject("Free",60, 79, PropertyType.STRING);

    /*
     *   Methods
     */

    public LineH() {
        this.initFields();
    }

    public LineH(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "H";
        fields = new ArrayList<>();

        fields.add(this.year);
        fields.add(this.month);
        fields.add(this.day);
        fields.add(this.fixOfTime);
        fields.add(this.hour);
        fields.add(this.minutes);
        fields.add(this.seconds);
        fields.add(this.latitude);
        fields.add(this.longitude);
        fields.add(this.depth);
        fields.add(this.rms);
        fields.add(this.freeText);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);}

    public String createLine() {
        String wSpace = " ";
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.year, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.month, true)
                + PropertyFixes.fixLineFormat(this.day, true)
                + PropertyFixes.fixLineFormat(this.fixOfTime, true)
                + PropertyFixes.fixLineFormat(this.hour, true)
                + PropertyFixes.fixLineFormat(this.minutes, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.seconds, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.latitude, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.longitude, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.depth, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.freeText, true)
                + this.lineNumber
                ;
    }

    /*
     *   Getters and Setters
     */

    public void setYear(String val) {this.year.setValue(val.trim());}
    public String getYear() {
        return year.getValue();
    }
    public PropertyObject getYearObject() { return year; }

    public void setMonth(String val) {this.month.setValue(val.trim());}
    public String getMonth() {
        return month.getValue();
    }
    public PropertyObject getMonthObject() { return month; }

    public void setDay(String val) {this.day.setValue(val.trim());}
    public String getDay() {
        return day.getValue();
    }
    public PropertyObject getDayObjct() { return day; }

    public void setFixOfTime(String val) {this.fixOfTime.setValue(val.trim());}
    public String getFixOfTime() {
        return fixOfTime.getValue();
    }
    public PropertyObject getFixOfTimeObject() { return fixOfTime; }

    public void setHour(String val) {this.hour.setValue(val.trim());}
    public String getHour() {
        return hour.getValue();
    }
    public PropertyObject getHourObject() { return hour; }

    public void setMinutes(String val) {this.minutes.setValue(val.trim());}
    public String getMinutes() {
        return minutes.getValue();
    }
    public PropertyObject getMinutesObject() { return minutes; }

    public void setSeconds(String val) {this.seconds.setValue(val.trim());}
    public String getSeconds() {
        return seconds.getValue();
    }
    public PropertyObject getSecondsObject() { return seconds; }

    public void setLatitude(String val) {this.latitude.setValue(val.trim());}
    public String getLatitude() {
        return latitude.getValue();
    }
    public PropertyObject getLatitudeObject() { return latitude; }

    public void setLongitude(String val) {this.longitude.setValue(val.trim());}
    public String getLongitude() {
        return longitude.getValue();
    }
    public PropertyObject getLongitudeObject() { return  longitude; }

    public void setDepth(String val) {this.depth.setValue(val.trim());}
    public String getDepth() {
        return depth.getValue();
    }
    public PropertyObject getDepthObject() { return depth; }

    public void setRms(String val) {
        this.rms.setValue(val.trim());
    }
    public String getRms() { return rms.getValue(); }
    public PropertyObject getRmsObject() { return rms; }

    public void setFreeText(String val) { this.freeText.setValue(val.trim()); }
    public String getFreeText() { return freeText.getValue(); }
    public PropertyObject getFreeTextObject() { return freeText; }

}
