package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

/**
 * Model for Type 2 line (Macroseismic information) - The Nordic format
 *
 * @see <a href="http://seis.geus.net/software/seisan/seisan.pdf">Seisan official documentation</a>
 *
 * @author Christian Rønnevik
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Line2 extends Line {

    private PropertyObject description =
            new PropertyObject("Any descriptive text", 6, 20, PropertyType.STRING);
    private PropertyObject diastrophismCode =
            new PropertyObject("Diastrophism code", 22, 22, PropertyType.STRING);
    private PropertyObject tsunamiCode =
            new PropertyObject("Tsunami code", 23, 23, PropertyType.STRING);
    private PropertyObject seicheCode =
            new PropertyObject("Seiche code", 24, 24, PropertyType.STRING);

    private PropertyObject culturalEffects =
            new PropertyObject("Cultural effects", 25, 25, PropertyType.STRING);
    private PropertyObject unusualEffects =
            new PropertyObject("Unusual events", 26, 26, PropertyType.STRING);
    private PropertyObject maxIntensity =
            new PropertyObject("Max Intensity", 28, 29, PropertyType.INTEGER);
    private PropertyObject maxIntensityQualifier =
            new PropertyObject("Max Intensity qualifier", 30, 30, PropertyType.STRING);
    private PropertyObject intensityScale =
            new PropertyObject("Intensity scale", 31, 32, PropertyType.STRING);

    private PropertyObject macroLatitude =
            new PropertyObject("Macroseismic latitude", 34, 39, PropertyType.DOUBLE, 2);
    private PropertyObject macroLongitude =
            new PropertyObject("Macroseismic longitude", 41, 47, PropertyType.DOUBLE, 2);
    private PropertyObject macroMagnitude =
            new PropertyObject("Macroseismic magnitude", 49, 51, PropertyType.DOUBLE, 1);
    private PropertyObject magnitudeType =
            new PropertyObject("Type of magnitudeI", 52, 52, PropertyType.STRING);

    private PropertyObject logRadiusFeltArea =
            new PropertyObject(
                    "Logarithm (base 10) of radius of felt area.",
                    53,
                    56,
                    PropertyType.DOUBLE,
                    2);
    private PropertyObject logAreaFeltSquareOne =
            new PropertyObject(
                    "Logarithm (base 10) of area (km**2) number 1",
                    57,
                    61,
                    PropertyType.DOUBLE,
                    2);
    private PropertyObject intensityBoardAreaOne =
            new PropertyObject(
                    "Intensity boardering the area number 1.",
                    62,
                    63,
                    PropertyType.INTEGER);
    private PropertyObject logAreaFeltSquareTwo =
            new PropertyObject(
                    "Logarithm (base 10) of area (km**2) number 2",
                    64,
                    68,
                    PropertyType.DOUBLE,
                    2);
    private PropertyObject intensityBoardAreaTwo =
            new PropertyObject(
                    "Intensity boardering the area number 2",
                    69,
                    70,
                    PropertyType.INTEGER);

    private PropertyObject qualityRepAgency =
            new PropertyObject("Quality rank of the report", 72, 72, PropertyType.STRING);
    private PropertyObject repAgency =
            new PropertyObject("Reporting agency", 73, 75, PropertyType.STRING);

    public Line2() {
        this.initFields();
    }

    public Line2(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "2";
        fields = new ArrayList<PropertyObject>();

        fields.add(description);
        fields.add(diastrophismCode);
        fields.add(tsunamiCode);
        fields.add(seicheCode);

        fields.add(culturalEffects);
        fields.add(unusualEffects);
        fields.add(maxIntensity);
        fields.add(maxIntensityQualifier);
        fields.add(intensityScale);

        fields.add(macroLatitude);
        fields.add(macroLongitude);
        fields.add(macroMagnitude);
        fields.add(magnitudeType);

        fields.add(logRadiusFeltArea);
        fields.add(logAreaFeltSquareOne);
        fields.add(intensityBoardAreaOne);
        fields.add(logAreaFeltSquareTwo);
        fields.add(intensityBoardAreaTwo);

        fields.add(qualityRepAgency);
        fields.add(repAgency);
    }

    public void setValues() {
        for(PropertyObject obj : fields) {
            StringBuilder value = new StringBuilder();
            for(int i = (obj.getColumnStart() - 1); i < obj.getColumnEnd(); i++) {
                value.append(this.lineText.charAt(i));
            }
            obj.setValue(value.toString());
        }

        this.lineNumber = String.valueOf(this.lineText.charAt(79));
    }

    public String createLine() {
        return "";
    }

    public String getDescription() {
        return description.getValue();
    }

    public String getDiastrophismCode() {
        return diastrophismCode.getValue();
    }

    public String getTsunamiCode() {
        return tsunamiCode.getValue();
    }

    public String getSeicheCode() {
        return seicheCode.getValue();
    }

    public String getCulturalEffects() {
        return culturalEffects.getValue();
    }

    public String getUnusualEffects() {
        return unusualEffects.getValue();
    }

    public String getMaxIntensity() {
        return maxIntensity.getValue();
    }

    public String getMaxIntensityQualifier() {
        return maxIntensityQualifier.getValue();
    }

    public String getIntensityScale() {
        return intensityScale.getValue();
    }

    public String getMacroLatitude() {
        return macroLatitude.getValue();
    }

    public String getMacroLongitude() {
        return macroLongitude.getValue();
    }

    public String getMacroMagnitude() {
        return macroMagnitude.getValue();
    }

    public String getMagnitudeType() {
        return magnitudeType.getValue();
    }

    public String getLogRadiusFeltArea() {
        return logRadiusFeltArea.getValue();
    }

    public String getLogAreaFeltSquareOne() {
        return logAreaFeltSquareOne.getValue();
    }

    public String getIntensityBoardAreaOne() {
        return intensityBoardAreaOne.getValue();
    }

    public String getLogAreaFeltSquareTwo() {
        return logAreaFeltSquareTwo.getValue();
    }

    public String getIntensityBoardAreaTwo() {
        return intensityBoardAreaTwo.getValue();
    }

    public String getQualityRepAgency() {
        return qualityRepAgency.getValue();
    }

    public String getRepAgency() {
        return repAgency.getValue();
    }
}
