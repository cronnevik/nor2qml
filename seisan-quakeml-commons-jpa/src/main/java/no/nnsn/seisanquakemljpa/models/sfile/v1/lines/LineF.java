package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class LineF extends Line {

    private String orgID;

    @Setter
    @Getter
    private Line1 relatedLine1;

    private PropertyObject strike =
            new PropertyObject("Strike", 1, 10, PropertyType.DOUBLE);
    private PropertyObject dip =
            new PropertyObject("Dip", 11, 20, PropertyType.DOUBLE);
    private PropertyObject rake =
            new PropertyObject("Rake", 21, 30, PropertyType.DOUBLE);
    private PropertyObject errorStrike =
            new PropertyObject("Error in strike", 31, 35, PropertyType.DOUBLE, 1);
    private PropertyObject errorDip =
            new PropertyObject("Error in dip", 36, 40, PropertyType.DOUBLE, 1);
    private PropertyObject errorRake =
            new PropertyObject("Error in rake", 41, 45, PropertyType.DOUBLE);

    private PropertyObject fitError =
            new PropertyObject("Fit error", 46, 50, PropertyType.DOUBLE, 1);
    private PropertyObject stationDistRatio =
            new PropertyObject("Station distribution ratio", 51, 55, PropertyType.DOUBLE, 1);
    private PropertyObject amplitudeRatioFit =
            new PropertyObject("Amplitude ratio fit", 56, 60, PropertyType.DOUBLE, 1);
    private PropertyObject numBadPolarities =
            new PropertyObject("Number of bad polarities", 61, 63, PropertyType.INTEGER);
    private PropertyObject numBadAmplitudeRatios =
            new PropertyObject("Number of bad amplitude ratios", 64, 65, PropertyType.INTEGER);

    private PropertyObject agencyCode =
            new PropertyObject("Agency code", 67, 69, PropertyType.STRING);
    private PropertyObject programUsed =
            new PropertyObject("Program used", 71, 77, PropertyType.STRING);
    private PropertyObject qualityOfSolution =
            new PropertyObject("Quality of solution", 78, 78, PropertyType.STRING);

    private PropertyObject blankField =
            new PropertyObject("Blank, can be used by user", 79, 79, PropertyType.STRING);

    public LineF() {
        this.initFields();
    }

    public LineF(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "F";
        fields = new ArrayList<>();

        fields.add(this.strike);
        fields.add(this.dip);
        fields.add(this.rake);

        fields.add(this.errorStrike);
        fields.add(this.errorDip);
        fields.add(this.errorRake);

        fields.add(this.fitError);
        fields.add(this.stationDistRatio);
        fields.add(this.amplitudeRatioFit);
        fields.add(this.numBadPolarities);
        fields.add(this.numBadAmplitudeRatios);

        fields.add(this.agencyCode);
        fields.add(this.programUsed);
        fields.add(this.qualityOfSolution);

        fields.add(this.blankField);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }
    public String createLine() {
        String wSpace = " ";
        return
                PropertyFixes.fixLineFormat(this.strike, true)
                + PropertyFixes.fixLineFormat(this.dip, true)
                + PropertyFixes.fixLineFormat(this.rake, true)
                + PropertyFixes.fixLineFormat(this.errorStrike, true)
                + PropertyFixes.fixLineFormat(this.errorDip, true)
                + PropertyFixes.fixLineFormat(this.errorRake, true)
                + PropertyFixes.fixLineFormat(this.fitError, true)
                + PropertyFixes.fixLineFormat(this.stationDistRatio, true)
                + PropertyFixes.fixLineFormat(this.amplitudeRatioFit, true)
                + PropertyFixes.fixLineFormat(this.numBadPolarities, true)
                + PropertyFixes.fixLineFormat(this.numBadAmplitudeRatios, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.agencyCode, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.programUsed, false)
                + PropertyFixes.fixLineFormat(this.qualityOfSolution, true)
                + PropertyFixes.fixLineFormat(this.blankField, true)
                + this.lineNumber
                ;
    }


    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getStrike() {
        return strike.getValue();
    }

    public void setStrike(String val) {
        this.strike.setValue(val.trim());
    }

    public String getDip() {
        return dip.getValue();
    }

    public void setDip(String val) {
        this.dip.setValue(val.trim());
    }

    public String getRake() {
        return rake.getValue();
    }

    public void setRake(String val) {
        this.rake.setValue(val.trim());
    }

    public String getErrorStrike() {
        return errorStrike.getValue();
    }

    public void setErrorStrike(String val) {
        this.errorStrike.setValue(val.trim());
    }

    public String getErrorDip() {
        return errorDip.getValue();
    }

    public void setErrorDip(String val) {
        this.errorDip.setValue(val.trim());
    }

    public String getErrorRake() {
        return errorRake.getValue();
    }

    public void setErrorRake(String val) {
        this.errorRake.setValue(val.trim());
    }

    public String getFitError() {
        return fitError.getValue();
    }

    public void setFitError(String val) {
        this.fitError.setValue(val.trim());
    }

    public String getStationDistRatio() {
        return stationDistRatio.getValue();
    }

    public void setStationDistRatio(String val) {
        this.stationDistRatio.setValue(val.trim());
    }

    public String getAmplitudeRatioFit() {
        return amplitudeRatioFit.getValue();
    }

    public void setAmplitudeRatioFit(String val) {
        this.amplitudeRatioFit.setValue(val.trim());
    }

    public String getNumBadPolarities() {
        return numBadPolarities.getValue();
    }

    public void setNumBadPolarities(String val) {
        this.numBadPolarities.setValue(val.trim());
    }

    public String getNumBadAmplitudeRatios() {
        return numBadAmplitudeRatios.getValue();
    }

    public void setNumBadAmplitudeRatios(String val) {
        this.numBadAmplitudeRatios.setValue(val.trim());
    }

    public String getAgencyCode() {
        return agencyCode.getValue();
    }

    public void setAgencyCode(String val) {
        this.agencyCode.setValue(val.trim());
    }

    public String getProgramUsed() {
        return programUsed.getValue();
    }

    public void setProgramUsed(String val) {
        this.programUsed.setValue(val.trim());
    }

    public String getQualityOfSolution() {
        return qualityOfSolution.getValue();
    }

    public void setQualityOfSolution(String val) {
        this.qualityOfSolution.setValue(val.trim());
    }

    public String getBlankField() {
        return blankField.getValue();
    }

    public void setBlankField(String val) {
        this.blankField.setValue(val.trim());
    }
}
