package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.LineType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class Line6 extends Line {

    private PropertyObject commentText =
            new PropertyObject("Name of file or archive reference", 2, 79, PropertyType.STRING);

    public Line6() {
        this.initFields();
    }

    public Line6(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "6";
        this.lineType = LineType.LINETYPE_6;
        fields = new ArrayList<PropertyObject>();
        fields.add(commentText);
    }

    public String createLine() {
        String returnText = "";

        if (lineText != null && !lineText.isEmpty()) {
            returnText = lineText;
        }
        return returnText;
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String getCommentText() {
        return commentText.getValue();
    }

    public void setCommentText(String val) {
        this.commentText.setValue(val.trim());
    }
}
