package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.LineType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.io.UnsupportedEncodingException;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
@Data
public class Line {
    /**
     * The string representation of the entire line.
     */
    protected String lineText;
    /**
     * The {@link String} instance represent the type of this line.
     * This fixed value is used for creating new lines.
     */
    protected String lineNumber;

    /**
     * The rowNumber of type {@link Integer} represent the current count of lines within the file.
     */
    protected Integer rowNumber;

    /**
     * List of {@link PropertyObject} for each field within the line.
     * The {@link PropertyObject} instance hold the value and the associated column format description.
     */
    @XmlTransient
    protected List<PropertyObject> fields;

    /**
     * Infer which type of line the class holds.
     */
    protected LineType lineType;


    public String getLineText() {
        try {
            return new String(lineText.getBytes("ISO-8859-1"), "ISO-8859-1");
        } catch (UnsupportedEncodingException uee) {
            return null;
        }
    }

    public void setLineInfo(String line, Integer num) {
        this.lineText = line;
        this.rowNumber = num;
    }
}
