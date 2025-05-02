package no.nnsn.seisanquakeml.models.sfile.v1.lines;

import no.nnsn.seisanquakeml.models.sfile.v1.enums.DescriptionType;
import no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType;
import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class Line3 extends Line{

    private DescriptionType descriptionType;
    private final PropertyObject commentText = new PropertyObject("Text", 2, 79, PropertyType.STRING);

    public Line3() {
        this.initFields();
    }

    public Line3(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "3";
        lineType = LineType.LINETYPE_3;
        fields = new ArrayList<>();
        fields.add(commentText);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String createLine() {

        String returnText = "";

        if (lineText != null && !lineText.isEmpty()) {
            returnText = lineText;
        } else {
            String whiteSpace = " ";
            String text = this.getCommentText();
            int txtLength = text.length();
            if (this.descriptionType.equals(DescriptionType.EVENT_LOCALITY)) {
                String preText = " LOCALITY: ";
                int preTextLength = preText.length();
                int emptySpaceNum =  80 - 1 - preTextLength - txtLength;

                returnText = preText + text;
                for (int i = 0; i < emptySpaceNum; i++) {
                    returnText += whiteSpace;
                }
                returnText += this.lineNumber;

            } else if  (this.descriptionType.equals(DescriptionType.EVENT_COMMENT)) {
                int emptySpaceNum =  80 - 1 - 1 - txtLength;

                returnText =  whiteSpace + text;
                for (int i = 0; i < emptySpaceNum; i++) {
                    returnText += whiteSpace;
                }
                returnText += this.lineNumber;
            } else {
                return null;
            }
        }

        return  returnText;
    }

    public DescriptionType getDescriptionType() {
        return descriptionType;
    }

    public void setDescriptionType(DescriptionType descriptionType) {
        this.descriptionType = descriptionType;
    }

    public String getCommentText() {
        if (commentText.getValue() != null) {
            try {
                return new String(commentText.getValue().getBytes("ISO-8859-1"), "ISO-8859-1");
            } catch (UnsupportedEncodingException uee) {
                return null;
            }
        } else {
            return null;
        }
    }

    public void setCommentText(String val) {
        this.commentText.setValue(val.trim());
    }
}

