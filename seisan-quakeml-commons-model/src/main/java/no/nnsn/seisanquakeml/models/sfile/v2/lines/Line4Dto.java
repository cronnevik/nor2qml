package no.nnsn.seisanquakeml.models.sfile.v2.lines;

import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class Line4Dto extends Line {
    private PropertyObject stationName =
            new PropertyObject("Station Name", 2, 6, PropertyType.STRING);
    private PropertyObject component =
            new PropertyObject("Component", 7, 9, PropertyType.STRING);
    private PropertyObject networkCode =
            new PropertyObject("Network Code", 11, 12, PropertyType.STRING);
    private PropertyObject location =
            new PropertyObject("Location", 13, 14, PropertyType.STRING);
    private PropertyObject qualityIndicator =
            new PropertyObject("Quality Indicator", 16, 16, PropertyType.STRING);
    private PropertyObject phaseID =
            new PropertyObject("Phase ID", 17, 24, PropertyType.STRING);
    private PropertyObject weightingIndicator =
            new PropertyObject("Weighting Indicator", 25, 25, PropertyType.STRING);
    private PropertyObject freeOrFlag =
            new PropertyObject("Free or flag A to indicate automatic pick", 26, 26, PropertyType.STRING);
    private PropertyObject hour =
            new PropertyObject("Hour", 27, 28, PropertyType.INTEGER);
    private PropertyObject minutes =
            new PropertyObject("Minutes", 29, 30, PropertyType.INTEGER);
    private PropertyObject seconds =
            new PropertyObject("Seconds", 32, 37, PropertyType.DOUBLE, 3);
    private PropertyObject parameterOne =
            new PropertyObject("Parameter 1", 38, 44, PropertyType.DOUBLEORSTRING, 1);
    private PropertyObject parameterTwo =
            new PropertyObject("Parameter 2", 45, 50, PropertyType.DOUBLEORSTRING, 2);
    private PropertyObject agency =
            new PropertyObject("Agency", 52, 54, PropertyType.STRING);
    private PropertyObject operator =
            new PropertyObject("Operator", 56, 58, PropertyType.STRING);
    private PropertyObject angleOfIncidence =
            new PropertyObject("Angle of incidence", 60, 63, PropertyType.DOUBLE, 1);
    private PropertyObject residual =
            new PropertyObject("Residual", 64, 68, PropertyType.DOUBLE, 3, false);
    private PropertyObject weight =
            new PropertyObject("weight", 69, 70, PropertyType.INTEGER);
    private PropertyObject epicentralDistance =
            new PropertyObject("Epicentral distance (km)", 71, 75, PropertyType.DOUBLE, 1);
    private PropertyObject azimuthAtSource =
            new PropertyObject("Azimuth at source", 77, 79, PropertyType.INTEGER);

    public Line4Dto() {
        this.initFields();
    }

    public Line4Dto(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "4";
        fields = new ArrayList<PropertyObject>();

        fields.add(stationName);
        fields.add(component);

        fields.add(networkCode);
        fields.add(location);

        fields.add(qualityIndicator);
        fields.add(phaseID);
        fields.add(weightingIndicator);
        fields.add(freeOrFlag);
        fields.add(hour);
        fields.add(minutes);

        fields.add(seconds);
        fields.add(parameterOne);
        fields.add(parameterTwo);

        fields.add(agency);

        fields.add(operator);

        fields.add(angleOfIncidence);
        fields.add(residual);
        fields.add(weight);
        fields.add(epicentralDistance);

        fields.add(azimuthAtSource);
    }

    public void setLineInfo(String line, Integer num) {
        this.lineText = line;
        this.rowNumber = num;
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);}

    public String createLine() {
        String wSpace = " ";
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.stationName, false)
                + PropertyFixes.fixLineFormat(this.component, false)
                + wSpace
                + PropertyFixes.fixLineFormat(this.networkCode, false)
                + PropertyFixes.fixLineFormat(this.location, false)
                + wSpace
                + PropertyFixes.fixLineFormat(this.qualityIndicator, true)
                + PropertyFixes.fixLineFormat(this.phaseID, false)
                + PropertyFixes.fixLineFormat(this.weightingIndicator, false)
                + PropertyFixes.fixLineFormat(this.freeOrFlag, false)
                + PropertyFixes.fixLineFormat(this.hour, true)
                + PropertyFixes.fixLineFormat(this.minutes, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.seconds, true)
                + PropertyFixes.fixLineFormat(this.parameterOne, true)
                + PropertyFixes.fixLineFormat(this.parameterTwo, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.agency, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.operator, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.angleOfIncidence, true)
                + PropertyFixes.fixLineFormat(this.residual, true)
                + PropertyFixes.fixLineFormat(this.weight, true)
                + PropertyFixes.fixLineFormat(this.epicentralDistance, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.azimuthAtSource, true)
                + wSpace;
    }

    public String getStationName() {
        return this.stationName.getValue();
    }
    public void setStationName(String val) {this.stationName.setValue(val.trim());}
    public PropertyObject getStationNameObject() { return this.stationName; }


    public String getComponent() {
        return this.component.getValue();
    }
    public void setComponent(String val) {
        this.component.setValue(val.trim());
    }
    public PropertyObject getComponentTypeObject() { return this.component; }

    public String getNetworkCode() {
        return networkCode.getValue();
    }
    public void setNetworkCode(String val) {
        this.networkCode.setValue(val.trim());
    }
    public PropertyObject getNetworkCodeObject() {return this.networkCode;}

    public String getLocation() {
        return location.getValue();
    }
    public void setLocation(String val) {
        this.location.setValue(val.trim());
    }
    public PropertyObject getLocationObject() {return this.location;}

    public String getQualityIndicator() {
        return qualityIndicator.getValue();
    }
    public void setQualityIndicator(String val) {
        this.qualityIndicator.setValue(val.trim());
    }
    public PropertyObject getQualityIndicatorObject() {return this.qualityIndicator;}

    public String getPhaseID() {
        return phaseID.getValue();
    }
    public void setPhaseID(String val) {
        this.phaseID.setValue(val.trim());
    }
    public PropertyObject getPhaseIDObject() {return this.phaseID;}

    public String getWeightingIndicator() {
        return weightingIndicator.getValue();
    }
    public void setWeightingIndicator(String val) {
        this.weightingIndicator.setValue(val.trim());
    }
    public PropertyObject getWeightingIndicatorObject() {return this.weightingIndicator;}

    public String getFreeOrFlag() {
        return freeOrFlag.getValue();
    }
    public void setFreeOrFlag(String val) {
        this.freeOrFlag.setValue(val.trim());
    }
    public PropertyObject getFreeOrFlagObject() {return this.freeOrFlag;}

    public String getHour() {
        return hour.getValue();
    }
    public void setHour(String val) {
        this.hour.setValue(val.trim());
    }
    public PropertyObject getHourObject() {return this.hour;}

    public String getMinutes() {
        return minutes.getValue();
    }
    public void setMinutes(String val) {
        this.minutes.setValue(val.trim());
    }
    public PropertyObject getMinutesObject() {return this.minutes;}

    public String getSeconds() {
        return seconds.getValue();
    }
    public void setSeconds(String val) {
        this.seconds.setValue(val.trim());
    }
    public PropertyObject getSecondsObject() {return this.seconds;}

    public String getParameterOne() {
        return parameterOne.getValue();
    }
    public void setParameterOne(String val) {
        this.parameterOne.setValue(val.trim());
    }
    public PropertyObject getParameterOneObject() {return this.parameterOne;}

    public String getParameterTwo() {
        return parameterTwo.getValue();
    }
    public void setParameterTwo(String val) {
        this.parameterTwo.setValue(val.trim());
    }
    public PropertyObject getParameterTwoObject() {return this.parameterTwo;}

    public String getAgency() {
        return agency.getValue();
    }
    public void setAgency(String val) {
        this.agency.setValue(val.trim());
    }
    public PropertyObject getAgencyObject() {return this.agency;}

    public String getOperator() {
        return operator.getValue();
    }
    public void setOperator(String val) {
        this.operator.setValue(val.trim());
    }
    public PropertyObject getOperatorObject() {return this.operator;}

    public String getAngleOfIncidence() {
        return angleOfIncidence.getValue();
    }
    public void setAngleOfIncidence(String val) {
        this.angleOfIncidence.setValue(val.trim());
    }
    public PropertyObject getAngleOfIncidenceObject() {return angleOfIncidence;}

    public String getResidual() {
        return residual.getValue();
    }
    public void setResidual(String val) {
        this.residual.setValue(val.trim());
    }
    public PropertyObject getResidualObject() {return residual;}

    public String getWeight() {
        return weight.getValue();
    }
    public void setWeight(String val) {
        this.weight.setValue(val.trim());
    }
    public PropertyObject getWeightObject() {return this.weight;}

    public String getEpicentralDistance() {
        return epicentralDistance.getValue();
    }
    public void setEpicentralDistance(String val) {
        this.epicentralDistance.setValue(val.trim());
    }
    public PropertyObject getEpicentralDistanceObject() {return this.epicentralDistance;}

    public String getAzimuthAtSource() {
        return azimuthAtSource.getValue();
    }
    public void setAzimuthAtSource(String val) {
        this.azimuthAtSource.setValue(val.trim());
    }
    public PropertyObject getAzimuthAtSourceObject() {return azimuthAtSource;}

    @XmlTransient // used for sorting
    public Integer getEpiDistInt() {
        String value = epicentralDistance.getValue();
        return value != null ? (int) Double.parseDouble(value) : 0;
    }

}
