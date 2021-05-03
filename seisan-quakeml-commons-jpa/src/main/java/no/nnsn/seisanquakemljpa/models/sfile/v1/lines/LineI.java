package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.LineType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class LineI extends Line {

    private PropertyObject helpTextAction =
            new PropertyObject("Help text for the action indicator", 2, 8, PropertyType.STRING);
    private PropertyObject lastActionDone =
            new PropertyObject("Last action done", 9, 11, PropertyType.STRING);
    private PropertyObject dateTimeLastAction =
            new PropertyObject("Date and time of last action", 13, 26, PropertyType.STRING);
    private PropertyObject helpTextOperator =
            new PropertyObject("Help text for operator", 28, 30, PropertyType.STRING);
    private PropertyObject helpTextStatus =
            new PropertyObject("Help text for status", 36, 42, PropertyType.STRING);
    private PropertyObject statusFlags =
            new PropertyObject("Status flags", 43, 56, PropertyType.STRING);
    private PropertyObject helpTextID =
            new PropertyObject("Help text for ID", 58, 60, PropertyType.STRING);
    private PropertyObject idYearToSec =
            new PropertyObject("ID, year to second", 61, 74, PropertyType.STRING);
    private PropertyObject newFileIdCreated =
            new PropertyObject("Indicate that a new file id had to be created", 75, 75, PropertyType.STRING);
    private PropertyObject idLocked =
            new PropertyObject("Indicate if ID is locked", 76, 76, PropertyType.STRING);

    public LineI() {
        this.initFields();
    }

    public LineI(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "I";
        this.lineType = LineType.LINETYPE_I;

        fields = new ArrayList<>();

        fields.add(helpTextAction);
        fields.add(lastActionDone);
        fields.add(dateTimeLastAction);
        fields.add(helpTextOperator);
        fields.add(helpTextStatus);
        fields.add(statusFlags);
        fields.add(helpTextID);
        fields.add(idYearToSec);
        fields.add(newFileIdCreated);
        fields.add(idLocked);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String getLineContent() {
        String content = "";
        content += PropertyFixes.fixLineFormat(this.helpTextAction, true);
        content += PropertyFixes.fixLineFormat(this.lastActionDone, true);
        content += " ";
        content += PropertyFixes.fixLineFormat(this.dateTimeLastAction, true);
        content += " ";
        content += PropertyFixes.fixLineFormat(this.helpTextOperator, true);
        content += " ";
        content += " ";
        content += " ";
        content += " ";
        content += " ";
        content += PropertyFixes.fixLineFormat(this.helpTextStatus, true);
        content += PropertyFixes.fixLineFormat(this.statusFlags, true);
        content += " ";
        content += PropertyFixes.fixLineFormat(this.helpTextID, true);
        content += PropertyFixes.fixLineFormat(this.idYearToSec, true);
        content += PropertyFixes.fixLineFormat(this.newFileIdCreated, true);
        content += PropertyFixes.fixLineFormat(this.idLocked, true);
        content += " ";
        content += " ";
        content += " ";
        return content;
    }

    public String createLine() {
        String returnText = "";

        if (lineText != null && !lineText.isEmpty()) {
            returnText = lineText;
        }
        return returnText;
    }


    public String getHelpTextAction() {
        return helpTextAction.getValue();
    }

    public void setHelpTextAction(String val) {
        this.helpTextAction.setValue(val.trim());
    }

    public String getLastActionDone() {
        return lastActionDone.getValue();
    }

    public void setLastActionDone(String val) {
        this.lastActionDone.setValue(val.trim());
    }

    public String getDateTimeLastAction() {
        return dateTimeLastAction.getValue();
    }

    public void setDateTimeLastAction(String val) {
        this.dateTimeLastAction.setValue(val.trim());
    }

    public String getHelpTextOperator() {
        return helpTextOperator.getValue();
    }

    public void setHelpTextOperator(String val) {
        this.helpTextOperator.setValue(val.trim());
    }

    public String getHelpTextStatus() {
        return helpTextStatus.getValue();
    }

    public void setHelpTextStatus(String val) {
        this.helpTextStatus.setValue(val.trim());
    }

    public String getStatusFlags() {
        return statusFlags.getValue();
    }

    public void setStatusFlags(String val) {
        this.statusFlags.setValue(val.trim());
    }

    public String getHelpTextID() {
        return helpTextID.getValue();
    }

    public void setHelpTextID(String val) {
        this.helpTextID.setValue(val.trim());
    }

    public String getIdYearToSec() {
        return idYearToSec.getValue();
    }

    public void setIdYearToSec(String val) {
        this.idYearToSec.setValue(val.trim());
    }

    public String getNewFileIdCreated() {
        return newFileIdCreated.getValue();
    }

    public void setNewFileIdCreated(String val) {
        this.newFileIdCreated.setValue(val.trim());
    }

    public String getIdLocked() {
        return idLocked.getValue();
    }

    public void setIdLocked(String val) {
        this.idLocked.setValue(val.trim());
    }
}
