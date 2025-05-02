package no.nnsn.seisanquakeml.models.sfile.v1.lines;

import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Model for Type 1 Line - The Nordic format
 *
 * @see <a href="http://seis.geus.net/software/seisan/seisan.pdf">Seisan official documentation</a>
 *
 * @author Christian Rønnevik
 */
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
public class Line1 extends Line {

    /*
     *   Properties
     */

    /**
     * The {@link String} instance represent the origin ID used internally for row ordering of other lines (such as LineF).
     * The property is only used in mapping of QuakeML format to the Nordic format.
     */
    private String orgID;

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

    /**
     * Minutes is instantiated by a {@link PropertyObject} with 4 characters, value type of {@link Double} and 1 decimal.
     * Character columns 17-20.
     */
    private final PropertyObject seconds =
            new PropertyObject("Seconds", 17, 20, PropertyType.DOUBLE, 1);

    /**
     * Location model indicator is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value is can be represented by any character.
     * Character column 21.
     */
    private final PropertyObject locModelIndicator =
            new PropertyObject("Location model indicator",21, 21, PropertyType.STRING);

    /**
     * Distance indicator is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value holds a single letter that represent distance. L = Local, R = Regional, D = Distant, etc.
     * Character column 22.
     */
    private final PropertyObject distanceIndicator =
            new PropertyObject("Distance Indicator", 22, 22, PropertyType.STRING);

    /**
     * Event ID (also referred to as Event Type) is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value holds a single letter that represent the event type.
     * E = Confirmed explosion, P = Probable explosion, V = Volcanic, Q = Confirmed earthquake, " " = Presumed earthquake, X = Landslide.
     * Character column 23.
     */
    private final PropertyObject eventID =
            new PropertyObject("Event ID", 23, 23, PropertyType.STRING);

    /**
     * Latitude is instantiated by a {@link PropertyObject} with 7 characters, value type of {@link Double} and 3 decimals.
     * Unit in Degrees (+ N).
     * Character columns 24-30.
     */
    private final PropertyObject latitude =
            new PropertyObject("Latitude",24, 30, PropertyType.DOUBLE, 3);

    /**
     * Longitude is instantiated by a {@link PropertyObject} with 8 characters, value type of {@link Double} and 3 decimals.
     * Unit in Degrees (+ E).
     * Character columns 31-38.
     */
    private final PropertyObject longitude =
            new PropertyObject("Longitude",31, 38, PropertyType.DOUBLE, 3);

    /**
     * Depth is instantiated by a {@link PropertyObject} with 5 characters, value type of {@link Double} and 1 decimal.
     * Unit is in Km.
     * Character columns 39-43.
     */
    private final PropertyObject depth =
            new PropertyObject("Depth",39, 43, PropertyType.DOUBLE, 1);

    /**
     * Depth indicator is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value holds a single letter. F = Fixed, S = Starting value.
     * Character column 44.
     */
    private final PropertyObject depthIndicator =
            new PropertyObject("Depth Indicator", 44, 44, PropertyType.STRING);

    /**
     * Locating indicator is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * The value holds a single letter. F = Fixed, S = Starting value, * = do not locate.
     * Character column 45.
     */
    private final PropertyObject locIndicator =
            new PropertyObject("Locating indicator", 45, 45, PropertyType.STRING);
    /**
     * Hypocenter Reporting Agency is instantiated by a {@link PropertyObject} with 3 characters and value type of {@link String}.
     * Character columns 46-48.
     */
    private final PropertyObject hypoCenterRepAgency =
            new PropertyObject("Hypocenter Reporting Agency", 46, 48, PropertyType.STRING);

    /**
     * Number of Stations Used is instantiated by a {@link PropertyObject} with 3 characters and value type of {@link Integer}.
     * Character columns 49-51.
     */
    private final PropertyObject numStationsUsed =
            new PropertyObject("Number of Stations Used", 49, 51, PropertyType.INTEGER);

    /**
     * RMS of Time Residual is instantiated by a {@link PropertyObject} with 4 characters, value type of {@link Double} and 1 decimal.
     * Character columns 52-55.
     */
    private final PropertyObject rmsTimeResiduals =
            new PropertyObject("RMS of Time Residuals",52, 55, PropertyType.DOUBLE, 1);

    /**
     * Magnitude No. 1 is instantiated by a {@link PropertyObject} with 4 characters, value type of {@link Double} and 1 decimal.
     * Character columns 56-59.
     */
    private final PropertyObject magOneNo =
            new PropertyObject("Magnitude No. 1", 56, 59, PropertyType.DOUBLE, 1);

    /**
     * Type of Magnitude No. 1 is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * Character column 60.
     */
    private final PropertyObject magOneType =
            new PropertyObject("Type of Magnitude", 60, 60, PropertyType.STRING);

    /**
     * Magnitude Reporting Agency No. 1 is instantiated by a {@link PropertyObject} with 3 characters and value type of {@link String}.
     * Character columns 61-63.
     */
    private final PropertyObject magOneRepAgency =
            new PropertyObject("Magnitude Reporting Agency", 61, 63, PropertyType.STRING);

    /**
     * Magnitude No. 2 is instantiated by a {@link PropertyObject} with 4 characters, value type of {@link Double} and 1 decimal.
     * Character columns 64-67.
     */
    private final PropertyObject magTwoNo =
            new PropertyObject("Magnitude No. 2", 64, 67, PropertyType.DOUBLE, 1);
    /**
     * Type of Magnitude No. 2 is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * Character column 68.
     */
    private final PropertyObject magTwoType =
            new PropertyObject("Type of Magnitude", 68, 68, PropertyType.STRING);
    /**
     * Magnitude Reporting Agency No. 2 is instantiated by a {@link PropertyObject} with 3 characters and value type of {@link String}.
     * Character columns 69-71.
     */
    private final PropertyObject magTwoRepAgency =
            new PropertyObject("Magnitude Reporting Agency", 69, 71, PropertyType.STRING);

    /**
     * Magnitude No. 3 is instantiated by a {@link PropertyObject} with 4 characters, value type of {@link Double} and 1 decimal.
     * Character columns 72-75.
     */
    private final PropertyObject magThreeNo =
            new PropertyObject("Magnitude No. 3", 72, 75, PropertyType.DOUBLE, 1);
    /**
     * Type of Magnitude No. 3 is instantiated by a {@link PropertyObject} with 1 character and value type of {@link String}.
     * Character column 76.
     */
    private final PropertyObject magThreeType =
            new PropertyObject("Type of Magnitude", 76, 76, PropertyType.STRING);

    /**
     * Magnitude Reporting Agency No. 3 is instantiated by a {@link PropertyObject} with 3 characters and value type of {@link String}.
     * Character columns 77-79.
     */
    private final PropertyObject magThreeRepAgency =
            new PropertyObject("Magnitude Reporting Agency", 77, 79, PropertyType.STRING);

    /*
     *   Methods
     */

    public Line1() {
        this.initFields();
    }

    public Line1(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "1";
        fields = new ArrayList<PropertyObject>();

        fields.add(this.year);
        fields.add(this.month);
        fields.add(this.day);

        fields.add(this.fixOfTime);

        fields.add(this.hour);
        fields.add(this.minutes);
        fields.add(this.seconds);

        fields.add(this.locModelIndicator);
        fields.add(this.distanceIndicator);
        fields.add(this.eventID);

        fields.add(this.latitude);
        fields.add(this.longitude);
        fields.add(this.depth);
        fields.add(this.depthIndicator);
        fields.add(this.locIndicator);
        fields.add(this.hypoCenterRepAgency);

        fields.add(this.numStationsUsed);
        fields.add(this.rmsTimeResiduals);

        fields.add(this.magOneNo);
        fields.add(this.magOneType);
        fields.add(this.magOneRepAgency);

        fields.add(this.magTwoNo);
        fields.add(this.magTwoType);
        fields.add(this.magTwoRepAgency);

        fields.add(this.magThreeNo);
        fields.add(this.magThreeType);
        fields.add(this.magThreeRepAgency);
    }

    public void setValues() {ValueSetter.setPropObjValues(fields, this.lineText);}

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
                + PropertyFixes.fixLineFormat(this.locModelIndicator, true)
                + PropertyFixes.fixLineFormat(this.distanceIndicator, true)
                + PropertyFixes.fixLineFormat(this.eventID, true)
                + PropertyFixes.fixLineFormat(this.latitude, true)
                + PropertyFixes.fixLineFormat(this.longitude, true)
                + PropertyFixes.fixLineFormat(this.depth, true)
                + PropertyFixes.fixLineFormat(this.depthIndicator, true)
                + PropertyFixes.fixLineFormat(this.locIndicator, true)
                + PropertyFixes.fixLineFormat(this.hypoCenterRepAgency, true)
                + PropertyFixes.fixLineFormat(this.numStationsUsed, true)
                + PropertyFixes.fixLineFormat(this.rmsTimeResiduals, true)
                + PropertyFixes.fixLineFormat(this.magOneNo, true)
                + PropertyFixes.fixLineFormat(this.magOneType, true)
                + PropertyFixes.fixLineFormat(this.magOneRepAgency, true)
                + PropertyFixes.fixLineFormat(this.magTwoNo, true)
                + PropertyFixes.fixLineFormat(this.magTwoType, true)
                + PropertyFixes.fixLineFormat(this.magTwoRepAgency, true)
                + PropertyFixes.fixLineFormat(this.magThreeNo, true)
                + PropertyFixes.fixLineFormat(this.magThreeType, true)
                + PropertyFixes.fixLineFormat(this.magThreeRepAgency, true)
                + this.lineNumber
                ;
    }

    /*
     *   Getters and Setters
     */

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public void setTime(String time) {
        LocalDateTime dateTime = LocalDateTime.parse(time);
    }

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

    public void setLocModelIndicator(String val) {this.locModelIndicator.setValue(val.trim());}

    public String getLocModelIndicator() {
        return locModelIndicator.getValue();
    }

    public void setDistanceIndicator(String val) {this.distanceIndicator.setValue(val.trim());}

    public String getDistanceIndicator() {
        return distanceIndicator.getValue();
    }

    public PropertyObject getDistanceIndicatorObject() { return distanceIndicator; }

    public void setEventID(String val) {this.eventID.setValue(val.trim());}

    public String getEventID() {
        return eventID.getValue();
    }

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

    public void setDepthIndicator(String val) {this.depthIndicator.setValue(val.trim());}

    public String getDepthIndicator() {
        return depthIndicator.getValue();
    }

    public PropertyObject getDepthIndicatorObject() { return depthIndicator; }

    public void setLocIndicator(String val) {this.locIndicator.setValue(val.trim());}

    public String getLocIndicator() {
        return locIndicator.getValue();
    }

    public PropertyObject getLocIndicatorObject() { return locIndicator; }

    public void setHypoCenterRepAgency(String val) {this.hypoCenterRepAgency.setValue(val.trim());}

    public String getHypoCenterRepAgency() {
        return hypoCenterRepAgency.getValue();
    }

    public void setNumStationsUsed(String val) {this.numStationsUsed.setValue(val.trim());}

    public String getNumStationsUsed() {
        return numStationsUsed.getValue();
    }

    public PropertyObject getNumStationsUsedObject() { return numStationsUsed; }

    public void setRmsTimeResiduals(String val) {this.rmsTimeResiduals.setValue(val.trim());}

    public String getRmsTimeResiduals() {
        return rmsTimeResiduals.getValue();
    }

    public PropertyObject getRmsTimeResidualsObject() { return rmsTimeResiduals; }

    public void setMagOneNo(String val) {this.magOneNo.setValue(val.trim());}

    public String getMagOneNo() {
        return magOneNo.getValue();
    }

    public PropertyObject getMagOneNoObject() { return magOneNo; }

    public void setMagOneType(String val) {this.magOneType.setValue(val.trim());}

    public String getMagOneType() {
        return magOneType.getValue();
    }

    public PropertyObject getMagOneTypeObject() { return magOneType; }

    public void setMagOneRepAgency(String val) {this.magOneRepAgency.setValue(val.trim());}

    public String getMagOneRepAgency() {
        return magOneRepAgency.getValue();
    }

    public PropertyObject getMagOneRepAgencyObject() { return  magOneRepAgency; }

    public void setMagTwoNo(String val) {this.magTwoNo.setValue(val.trim());}

    public String getMagTwoNo() {
        return magTwoNo.getValue();
    }

    public PropertyObject getMagTwoNoObject() { return magTwoNo; }

    public void setMagTwoType(String val) {this.magTwoType.setValue(val.trim());}

    public String getMagTwoType() {
        return magTwoType.getValue();
    }

    public PropertyObject getMagTwoTypeObject() { return magTwoType; }

    public void setMagTwoRepAgency(String val) {this.magTwoRepAgency.setValue(val.trim());}

    public String getMagTwoRepAgency() {
        return magTwoRepAgency.getValue();
    }

    public PropertyObject getMagTwoRepAgencyObject() { return magTwoRepAgency; }

    public void setMagThreeNo(String val) {this.magThreeNo.setValue(val.trim());}

    public String getMagThreeNo() {
        return magThreeNo.getValue();
    }

    public PropertyObject getMagThreeNoObject() { return magThreeNo; }

    public void setMagThreeType(String val) {this.magThreeType.setValue(val.trim());}

    public String getMagThreeType() {
        return magThreeType.getValue();
    }

    public PropertyObject getMagThreeTypeObject() { return  magThreeType; }

    public void setMagThreeRepAgency(String val) {this.magThreeRepAgency.setValue(val.trim());}

    public String getMagThreeRepAgency() {
        return magThreeRepAgency.getValue();
    }

    public PropertyObject getMagThreeRepAgencyObject() { return magThreeRepAgency; }
}
