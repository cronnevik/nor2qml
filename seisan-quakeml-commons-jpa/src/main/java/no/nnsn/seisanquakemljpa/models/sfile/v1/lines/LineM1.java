package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import lombok.Getter;
import lombok.Setter;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class LineM1 extends Line {

    @Setter
    @Getter
    private String orgID;

    @Setter
    @Getter
    private LineF relatedLineF;

    private PropertyObject year =
            new PropertyObject("Year", 2, 5, PropertyType.INTEGER);
    private PropertyObject month =
            new PropertyObject("Month", 7, 8, PropertyType.INTEGER);
    private PropertyObject day =
            new PropertyObject("Day of Month", 9, 10, PropertyType.INTEGER);
    private PropertyObject hour =
            new PropertyObject("Hour", 12, 13, PropertyType.INTEGER);
    private PropertyObject minutes =
            new PropertyObject("Minutes", 14, 15, PropertyType.INTEGER);
    private PropertyObject seconds =
            new PropertyObject("Seconds", 17, 20, PropertyType.DOUBLE);

    private PropertyObject latitude =
            new PropertyObject("Latitude", 24, 30, PropertyType.DOUBLE);
    private PropertyObject longitude =
            new PropertyObject("Longitude", 31, 38, PropertyType.DOUBLE);
    private PropertyObject depth =
            new PropertyObject("Depth", 39, 43, PropertyType.DOUBLE);

    private PropertyObject reportingAgency =
            new PropertyObject("Reporting Agency", 46, 48, PropertyType.STRING);
    private PropertyObject magnitude =
            new PropertyObject("Magnitude", 56, 59, PropertyType.DOUBLE,1);
    private PropertyObject magnitudeType =
            new PropertyObject("Type of Magnitude", 60, 60, PropertyType.STRING);
    private PropertyObject magnitudeRepAgency =
            new PropertyObject("Magnitude Reporting Agency", 61, 63, PropertyType.STRING);

    private PropertyObject methodUsed =
            new PropertyObject("Method used", 71, 77, PropertyType.STRING);
    private PropertyObject qualityOfSolution =
            new PropertyObject("Quality of solution", 78, 78, PropertyType.STRING);
    private PropertyObject blankField =
            new PropertyObject("Blank, can be used by user", 79, 79, PropertyType.STRING);

    public LineM1() {
        this.initFields();
    }

    public LineM1(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "M";
        fields = new ArrayList<>();

        fields.add(this.year);
        fields.add(this.month);
        fields.add(this.day);
        fields.add(this.hour);
        fields.add(this.minutes);
        fields.add(this.seconds);
        fields.add(this.latitude);
        fields.add(this.longitude);
        fields.add(this.depth);
        fields.add(this.reportingAgency);
        fields.add(this.magnitude);
        fields.add(this.magnitudeType);
        fields.add(this.magnitudeRepAgency);
        fields.add(this.methodUsed);
        fields.add(this.qualityOfSolution);
        fields.add(this.blankField);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String createLine() {
        String wSpace = " ";
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.year, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.month, true)
                + PropertyFixes.fixLineFormat(this.day, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.hour, true)
                + PropertyFixes.fixLineFormat(this.minutes, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.seconds, true)
                + wSpace + wSpace + wSpace
                + PropertyFixes.fixLineFormat(this.latitude, true)
                + PropertyFixes.fixLineFormat(this.longitude, true)
                + PropertyFixes.fixLineFormat(this.depth, true)
                + wSpace + wSpace
                + PropertyFixes.fixLineFormat(this.reportingAgency, true)
                + wSpace + wSpace + wSpace + wSpace + wSpace + wSpace + wSpace
                + PropertyFixes.fixLineFormat(this.magnitude, true)
                + PropertyFixes.fixLineFormat(this.magnitudeType, true)
                + PropertyFixes.fixLineFormat(this.magnitudeRepAgency, true)
                + wSpace + wSpace + wSpace + wSpace + wSpace + wSpace + wSpace
                + PropertyFixes.fixLineFormat(this.methodUsed, true)
                + PropertyFixes.fixLineFormat(this.qualityOfSolution, true)
                + PropertyFixes.fixLineFormat(this.blankField, true)
                + this.lineNumber
                ;
    }

    public String getYear() {
        return year.getValue();
    }

    public void setYear(String year) {
        this.year.setValue(year.trim());
    }

    public String getMonth() {
        return month.getValue();
    }

    public void setMonth(String month) {
        this.month.setValue(month.trim());
    }

    public String getDay() {
        return day.getValue();
    }

    public void setDay(String day) {
        this.day.setValue(day.trim());
    }

    public String getHour() {
        return hour.getValue();
    }

    public void setHour(String hour) {
        this.hour.setValue(hour.trim());
    }

    public String getMinutes() {
        return minutes.getValue();
    }

    public void setMinutes(String minutes) {
        this.minutes.setValue(minutes.trim());
    }

    public String getSeconds() {
        return seconds.getValue();
    }

    public void setSeconds(String seconds) {
        this.seconds.setValue(seconds.trim());
    }

    public String getLatitude() {
        return latitude.getValue();
    }

    public void setLatitude(String latitude) {
        this.latitude.setValue(latitude.trim());
    }

    public String getLongitude() {
        return longitude.getValue();
    }

    public void setLongitude(String longitude) {
        this.longitude.setValue(longitude.trim());
    }

    public String getDepth() {
        return depth.getValue();
    }

    public void setDepth(String depth) {
        this.depth.setValue(depth.trim());
    }

    public String getReportingAgency() {
        return reportingAgency.getValue();
    }

    public void setReportingAgency(String reportingAgency) {
        this.reportingAgency.setValue(reportingAgency.trim());
    }

    public String getMagnitude() {
        return magnitude.getValue();
    }

    public void setMagnitude(String magnitude) {
        this.magnitude.setValue(magnitude.trim());
    }

    public String getMagnitudeType() {
        return magnitudeType.getValue();
    }

    public void setMagnitudeType(String magnitudeType) {
        this.magnitudeType.setValue(magnitudeType.trim());
    }

    public String getMagnitudeRepAgency() {
        return magnitudeRepAgency.getValue();
    }

    public void setMagnitudeRepAgency(String magnitudeRepAgency) {
        this.magnitudeRepAgency.setValue(magnitudeRepAgency.trim());
    }

    public String getMethodUsed() {
        return methodUsed.getValue();
    }

    public void setMethodUsed(String methodUsed) {
        this.methodUsed.setValue(methodUsed.trim());
    }

    public String getQualityOfSolution() {
        return qualityOfSolution.getValue();
    }

    public void setQualityOfSolution(String qualityOfSolution) {
        this.qualityOfSolution.setValue(qualityOfSolution.trim());
    }

    public String getBlankField() {
        return blankField.getValue();
    }

    public void setBlankField(String blankField) {
        this.blankField.setValue(blankField.trim());
    }
}
