package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class LineE extends Line {

    private PropertyObject textGap =
            new PropertyObject("The text GAP", 2, 5, PropertyType.STRING);
    private PropertyObject gap =
            new PropertyObject("Gap", 6, 8, PropertyType.INTEGER);
    private PropertyObject originTimeError =
            new PropertyObject("Origin time error", 15, 20, PropertyType.DOUBLE, 2);
    private PropertyObject latitudeError =
            new PropertyObject("Latitude (y) error", 25, 30, PropertyType.DOUBLE, 1);
    private PropertyObject longitudeError =
            new PropertyObject("Longitude (x) error", 33, 38, PropertyType.DOUBLE, 1);
    private PropertyObject depthError =
            new PropertyObject("Depth (z) error", 39, 43, PropertyType.DOUBLE, 1);
    private PropertyObject covarianceXY =
            new PropertyObject("Covariance (x,y)", 44, 55, PropertyType.EXPONENTIAL, 4);
    private PropertyObject covarianceXZ =
            new PropertyObject("Covarience (x,z)", 56, 67, PropertyType.EXPONENTIAL, 4);
    private PropertyObject covarianceYZ =
            new PropertyObject("Covariance (y,z)", 68, 79, PropertyType.EXPONENTIAL, 4);

    public LineE() {
        this.initFields();
    }

    public LineE(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "E";
        fields = new ArrayList<PropertyObject>();

        fields.add(textGap);
        fields.add(gap);
        fields.add(originTimeError);
        fields.add(latitudeError);
        fields.add(longitudeError);
        fields.add(depthError);
        fields.add(covarianceXY);
        fields.add(covarianceXZ);
        fields.add(covarianceYZ);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String createLine() {
        String wSpace = " ";
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.textGap, true)
                + PropertyFixes.fixLineFormat(this.gap, true)
                + wSpace
                + wSpace
                + wSpace
                + wSpace
                + wSpace
                + wSpace
                + PropertyFixes.fixLineFormat(this.originTimeError, true)
                + wSpace
                + wSpace
                + wSpace
                + wSpace
                + PropertyFixes.fixLineFormat(this.latitudeError, true)
                + wSpace
                + wSpace
                + PropertyFixes.fixLineFormat(this.longitudeError, true)
                + PropertyFixes.fixLineFormat(this.depthError, true)
                + PropertyFixes.fixLineFormat(this.covarianceXY, true)
                + PropertyFixes.fixLineFormat(this.covarianceXZ, true)
                + PropertyFixes.fixLineFormat(this.covarianceYZ, true)
                + this.lineNumber
                ;
    }

    public void setTextGap(String val) { this.textGap.setValue(val.trim()); }

    public String getTextGap() {
        return textGap.getValue();
    }

    public void setGap(String val) {
        this.setTextGap("GAP=");
        this.gap.setValue(val.trim());
    }

    public String getGap() {
        return gap.getValue();
    }

    public void setOriginTimeError(String val) { this.originTimeError.setValue(val.trim()); }

    public String getOriginTimeError() {
        return originTimeError.getValue();
    }

    public void setLatitudeError(String val) { this.latitudeError.setValue(val.trim()); }

    public String getLatitudeError() {
        return latitudeError.getValue();
    }

    public void setLongitudeError(String val) { this.longitudeError.setValue(val.trim()); }

    public String getLongitudeError() {
        return longitudeError.getValue();
    }

    public void setDepthError(String val) { this.depthError.setValue(val.trim()); }

    public String getDepthError() {
        return depthError.getValue();
    }

    public void setCovarianceXY(String val) { this.covarianceXY.setValue(val.trim()); }

    public String getCovarianceXY() {
        return covarianceXY.getValue();
    }

    public void setCovarianceXZ(String val) { this.covarianceXZ.setValue(val.trim()); }

    public String getCovarianceXZ() {
        return covarianceXZ.getValue();
    }

    public void setCovarianceYZ(String val) { this.covarianceYZ.setValue(val.trim()); }

    public String getCovarianceYZ() {
        return covarianceYZ.getValue();
    }

}
