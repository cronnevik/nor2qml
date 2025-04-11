package no.nnsn.seisanquakemljpa.models.sfile.v1.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Object to hold data and metadata about each field within the Sfile lines
 *
 * @author Christian Rønnevik
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
public class PropertyObject {
    /** The name of each field as given in Seisan manual */
    private String name;
    /** The value of each field */
    private String value;
    /** The starting column for fields (out of a total of 80 possible columns) */
    private int columnStart;
    /** The ending column for fields (out of a total of 80 possible columns) */
    private int columnEnd;
    /** Type of format for the field - Enum */
    private PropertyType format;
    /** Specifies the amount of decimals used for a floating (Double) number in a field.
     * If another format (e.g. String) is used, then this property is unused/null */
    private int decimals;
    /** Specify if there should be a leading zero before decimal values - default value is true */
    private Boolean leadingZero = true;
    /** Marks if the value is parsable to a numeric value */
    private Boolean parsable;

    /** Boundaries conditions for the numeric value */
    LimitNumber limits;

    public PropertyObject() {}

    // Constructor for Double to specify number of decimals
    public PropertyObject(String name, int columnStart, int columnEnd, PropertyType format, int decimals) {
        this.name = name;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
        this.format = format;
        this.decimals = decimals;
        this.parsable = false;
    }

    // Constructor for Double to specify number of decimals with the option of specifying the presence of leading zero before decimals
    public PropertyObject(String name, int columnStart, int columnEnd, PropertyType format, int decimals, Boolean leadingZero) {
        this.name = name;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
        this.format = format;
        this.decimals = decimals;
        this.leadingZero = leadingZero;
        this.parsable = false;
    }

    // Constructor for Integer and String, setting decimals to 0
    public PropertyObject(String name, int columnStart, int columnEnd, PropertyType format) {
        this.name = name;
        this.columnStart = columnStart;
        this.columnEnd = columnEnd;
        this.format = format;
        this.decimals = 0;
        this.parsable = false;
    }

    // Returns the character length of property within the s-file
    public int getLengthSize() {
        return (this.columnEnd + 1) - this.columnStart;
    }
    public void setValue(String value) {
        this.value = !StringUtils.isBlank(value) ? value : null;
    }
}
