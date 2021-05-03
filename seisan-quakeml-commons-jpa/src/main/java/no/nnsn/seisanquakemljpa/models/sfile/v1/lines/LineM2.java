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
public class LineM2 extends Line {

    @Setter
    @Getter
    private String orgID;

    @Setter
    @Getter
    private LineF relatedLineF;

    @Setter
    @Getter
    private LineM1 relatedLineM1;

    private PropertyObject mt = new PropertyObject("MT", 2, 3, PropertyType.STRING);
    private PropertyObject mrr = new PropertyObject("Mrr or Mzz", 4, 9, PropertyType.DOUBLE,3);
    private PropertyObject mtt = new PropertyObject("Mtt or Mxx", 11, 16, PropertyType.DOUBLE, 3);
    private PropertyObject mpp = new PropertyObject("Mpp or Myy", 18, 23, PropertyType.DOUBLE, 3);
    private PropertyObject mrt = new PropertyObject("Mrt or Mzx", 25, 30, PropertyType.DOUBLE, 3);
    private PropertyObject mrp = new PropertyObject("Mrp or Mzy", 32, 37, PropertyType.DOUBLE, 3);
    private PropertyObject mtp = new PropertyObject("Mtp or Mxy", 39, 44, PropertyType.DOUBLE, 3);
    private PropertyObject reportingAgency = new PropertyObject("Reporting Agency", 46, 48, PropertyType.STRING);
    private PropertyObject mtCoordinateSystem = new PropertyObject("MT coordinate system", 49, 49, PropertyType.STRING);
    private PropertyObject exponential = new PropertyObject("Exponental", 50, 51, PropertyType.INTEGER);
    private PropertyObject scalarMoment =
            new PropertyObject("Scalar Moment", 53, 62, PropertyType.DOUBLEOREXPONENTIAL, 3);
    private PropertyObject methodUsed = new PropertyObject("Method used", 71, 77, PropertyType.STRING);
    private PropertyObject qualityOfSolution = new PropertyObject("Quality of solution", 78, 78, PropertyType.STRING);
    private PropertyObject blankField = new PropertyObject("Blank, can be used by user", 79, 79, PropertyType.STRING);

    public LineM2() {
        this.initFields();
    }

    public LineM2(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "M";
        fields = new ArrayList<PropertyObject>();

        fields.add(this.mt);
        fields.add(this.mrr);
        fields.add(this.mtt);
        fields.add(this.mpp);
        fields.add(this.mrt);
        fields.add(this.mrp);
        fields.add(this.mtp);
        fields.add(this.reportingAgency);
        fields.add(this.mtCoordinateSystem);
        fields.add(this.exponential);
        fields.add(this.scalarMoment);
        fields.add(this.methodUsed);
        fields.add(this.qualityOfSolution);
        fields.add(this.blankField);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String createLine() {
        String wSpace = " ";
        this.setMT("MT");
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.mt, true)
                + PropertyFixes.fixLineFormat(this.mrr, true) + wSpace
                + PropertyFixes.fixLineFormat(this.mtt, true) + wSpace
                + PropertyFixes.fixLineFormat(this.mpp, true) + wSpace
                + PropertyFixes.fixLineFormat(this.mrt, true) + wSpace
                + PropertyFixes.fixLineFormat(this.mrp, true) + wSpace
                + PropertyFixes.fixLineFormat(this.mtp, true) + wSpace
                + PropertyFixes.fixLineFormat(this.reportingAgency, true)
                + PropertyFixes.fixLineFormat(this.mtCoordinateSystem, true)
                + PropertyFixes.fixLineFormat(this.exponential, true) + wSpace
                + PropertyFixes.fixLineFormat(this.scalarMoment, true)
                        + wSpace + wSpace + wSpace + wSpace
                        + wSpace + wSpace + wSpace + wSpace
                + PropertyFixes.fixLineFormat(this.methodUsed, true)
                + PropertyFixes.fixLineFormat(this.qualityOfSolution, true)
                + PropertyFixes.fixLineFormat(this.blankField, true)
                + this.lineNumber
                ;
    }

    public String getMT() {
        return mt.getValue();
    }

    public void setMT(String val) {
        this.mt.setValue(val.trim());
    }

    public String getMrr() {
        return mrr.getValue();
    }

    public void setMrr(String val) {
        mrr.setValue(val.trim());
    }

    public String getMtt() {
        return mtt.getValue();
    }

    public void setMtt(String val) {
        mtt.setValue(val.trim());
    }

    public String getMpp() {
        return mpp.getValue();
    }

    public void setMpp(String val) {
        mpp.setValue(val.trim());
    }

    public String getMrt() {
        return mrt.getValue();
    }

    public void setMrt(String val) {
        mrt.setValue(val.trim());
    }

    public String getMrp() {
        return mrp.getValue();
    }

    public void setMrp(String val) {
        mrp.setValue(val.trim());
    }

    public String getMtp() {
        return mtp.getValue();
    }

    public void setMtp(String val) {
        mtp.setValue(val.trim());
    }

    public String getReportingAgency() {
        return reportingAgency.getValue();
    }

    public void setReportingAgency(String val) {
        this.reportingAgency.setValue(val.trim());
    }

    public String getMtCoordinateSystem() {
        return mtCoordinateSystem.getValue();
    }

    public void setMtCoordinateSystem(String val) {
        this.mtCoordinateSystem.setValue(val.trim());
    }

    public String getExponential() {
        return exponential.getValue();
    }

    public void setExponential(String val) {
        this.exponential.setValue(val.trim());
    }

    public String getScalarMoment() {
        return scalarMoment.getValue();
    }

    public void setScalarMoment(String val) {
        this.scalarMoment.setValue(val.trim());
    }

    public String getMethodUsed() {
        return methodUsed.getValue();
    }

    public void setMethodUsed(String val) {
        this.methodUsed.setValue(val.trim());
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
