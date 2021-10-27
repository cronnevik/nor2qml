package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyFixes;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public class Line4 extends Line {

    private PropertyObject stationName =
            new PropertyObject("Station Name", 2, 6, PropertyType.STRING);
    private PropertyObject instrumentType =
            new PropertyObject("Instrument Type", 7, 7, PropertyType.STRING);
    private PropertyObject component =
            new PropertyObject("Component", 8, 8, PropertyType.STRING);

    private PropertyObject freeOrWeight =
            new PropertyObject("Free or weight", 9, 9, PropertyType.STRING);

    private PropertyObject qualityIndicator =
            new PropertyObject("Quality Indicator", 10, 10, PropertyType.STRING);
    private PropertyObject phaseID =
            new PropertyObject("Phase ID", 11, 14, PropertyType.STRING);
    private PropertyObject weightingIndicator =
            new PropertyObject("Weighting Indicator", 15, 15, PropertyType.STRING);

    private PropertyObject flagA =
            new PropertyObject("Free or flag A to indicate automartic pick", 16, 16, PropertyType.STRING);

    private PropertyObject firstMotion =
            new PropertyObject("First Motion", 17, 17, PropertyType.STRING);
    private PropertyObject extLastPhaseIdChar =
            new PropertyObject("Note", 18, 18, PropertyType.STRING);

    private PropertyObject hour =
            new PropertyObject("Hour (can be up to 48)", 19, 20, PropertyType.INTEGER);
    private PropertyObject minutes =
            new PropertyObject("Minutes", 21, 22, PropertyType.INTEGER);
    private PropertyObject seconds =
            new PropertyObject("Seconds", 23, 28, PropertyType.DOUBLE, 2);

    private PropertyObject duration =
            new PropertyObject("Duration (to noise)", 30, 33, PropertyType.INTEGER);
    private PropertyObject amplitude =
            new PropertyObject("Amplitude (Zero-Peak)", 34, 40, PropertyType.DOUBLEOREXPONENTIAL, 1);
    private PropertyObject periodSeconds =
            new PropertyObject("Period Seconds", 42, 45, PropertyType.DOUBLE, 2);
    private PropertyObject directionDegrees =
            new PropertyObject("Direction of Approach, back azimuth", 47, 51, PropertyType.DOUBLE, 1);
    private PropertyObject phaseVelocity =
            new PropertyObject("Phase Velocity", 53, 56, PropertyType.DOUBLE);

    private PropertyObject angleOfIncidence =
            new PropertyObject("Angle of incidence", 57, 60, PropertyType.DOUBLE, 0);
    private PropertyObject backAzimuthResidual =
            new PropertyObject("Back azimuth residual", 61, 63, PropertyType.INTEGER);
    private PropertyObject travelTimeResidual =
            new PropertyObject("Travel time residual", 64, 68, PropertyType.DOUBLE, 2);
    private PropertyObject weight =
            new PropertyObject("Weight", 69, 70, PropertyType.INTEGER);
    private PropertyObject epicentralDistance =
            new PropertyObject("Epicentral distance(km)", 71, 75, PropertyType.DOUBLE, 1);
    private PropertyObject azimuthAtSource =
            new PropertyObject("Azimuth at source", 77, 79, PropertyType.INTEGER);

    public Line4() {
        this.initFields();
    }

    public Line4(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "4";
        fields = new ArrayList<PropertyObject>();

        fields.add(stationName);
        fields.add(instrumentType);
        fields.add(component);
        fields.add(freeOrWeight);

        fields.add(qualityIndicator);
        fields.add(phaseID);
        fields.add(weightingIndicator);
        fields.add(flagA);
        fields.add(firstMotion);
        fields.add(extLastPhaseIdChar);

        fields.add(hour);
        fields.add(minutes);
        fields.add(seconds);

        fields.add(duration);
        fields.add(amplitude);
        fields.add(periodSeconds);
        fields.add(directionDegrees);
        fields.add(phaseVelocity);

        fields.add(angleOfIncidence);
        fields.add(backAzimuthResidual);
        fields.add(travelTimeResidual);
        fields.add(weight);
        fields.add(epicentralDistance);
        fields.add(azimuthAtSource);
    }

    public void setValues() {ValueSetter.setPropObjValues(fields, this.lineText);}

    public String createLine() {

        String wSpace = " ";
        return
                wSpace
                + PropertyFixes.fixLineFormat(this.stationName, false)
                + PropertyFixes.fixLineFormat(this.instrumentType, true)
                + PropertyFixes.fixLineFormat(this.component, true)
                + PropertyFixes.fixLineFormat(this.freeOrWeight, true)
                + PropertyFixes.fixLineFormat(this.qualityIndicator, true)
                + PropertyFixes.fixLongPhasenames(
                        this.phaseID,
                        this.weightingIndicator,
                        this.flagA,
                        this.firstMotion,
                        this.extLastPhaseIdChar
                )
                + PropertyFixes.fixLineFormat(this.hour, true)
                + PropertyFixes.fixLineFormat(this.minutes, true)
                + PropertyFixes.fixLineFormat(this.seconds, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.duration, true)
                + PropertyFixes.fixLineFormat(this.amplitude, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.periodSeconds, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.directionDegrees, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.phaseVelocity, true)
                + PropertyFixes.fixLineFormat(this.angleOfIncidence, true)
                + PropertyFixes.fixLineFormat(this.backAzimuthResidual, true)
                + PropertyFixes.fixLineFormat(this.travelTimeResidual, true)
                + PropertyFixes.fixLineFormat(this.weight, true)
                + PropertyFixes.fixLineFormat(this.epicentralDistance, true)
                + wSpace
                + PropertyFixes.fixLineFormat(this.azimuthAtSource, true)
                + wSpace
        ;
    }

    public void setStationName(String val) {this.stationName.setValue(val.trim());}

    public String getStationName() {
        return stationName.getValue();
    }

    public PropertyObject getStationNameObject() { return stationName; }

    public void setInstrumentType(String val) {this.instrumentType.setValue(val.trim());}

    public String getInstrumentType() {
        return instrumentType.getValue();
    }

    public PropertyObject getInstrumentTypeObject() { return instrumentType; }

    public void setComponent(String val) {this.component.setValue(val.trim());}

    public String getComponent() {
        return component.getValue();
    }

    public PropertyObject getComponentObject() { return component; }

    public String getFreeOrWeight() {
        return freeOrWeight.getValue();
    }

    public void setFreeOrWeight(String val) {
        this.freeOrWeight.setValue(val.trim());
    }

    public void setQualityIndicator(String val) {this.qualityIndicator.setValue(val.trim());}

    public String getQualityIndicator() {
        return qualityIndicator.getValue();
    }

    public String getFlagA() {
        return flagA.getValue();
    }

    public PropertyObject getFlagAObject() {
        return flagA;
    }

    public void setFlagA(String val) {
        this.flagA.setValue(val.trim());
    }

    public void setPhaseID(String val) {this.phaseID.setValue(val.trim());}

    public String getPhaseID() {
        return phaseID.getValue();
    }

    public PropertyObject getPhaseIDObject() {
        return phaseID;
    }

    public void setWeightingIndicator(String val) {this.weightingIndicator.setValue(val.trim());}

    public String getWeightingIndicator() {
        return weightingIndicator.getValue();
    }

    public PropertyObject getWeightingIndicatorObject() {
        return weightingIndicator;
    }

    public void setFirstMotion(String val) {this.firstMotion.setValue(val.trim());}

    public String getFirstMotion() {
        return firstMotion.getValue();
    }

    public PropertyObject getFirstMotionObject() {
        return firstMotion;
    }

    public void setExtLastPhaseIdChar(String val) {
        this.extLastPhaseIdChar.setValue(val.trim());
    }

    public String getExtLastPhaseIdChar() {
        return extLastPhaseIdChar.getValue();
    }

    public PropertyObject getExtLastPhaseIdCharObject() {
        return extLastPhaseIdChar;
    }

    public void setHour(String val) {this.hour.setValue(val.trim());}

    public String getHour() {
        return hour.getValue();
    }

    public PropertyObject getHourObject() {return hour; }

    public void setMinutes(String val) {this.minutes.setValue(val.trim());}

    public String getMinutes() {
        return minutes.getValue();
    }

    public PropertyObject getMinutesObject() { return minutes; }

    public void setSeconds(String val) {this.seconds.setValue(val.trim());}

    public String getSeconds() {
        return seconds.getValue();
    }

    public PropertyObject getSecondsObject() { return seconds; }

    public void setDuration(String val) {this.duration.setValue(val.trim());}

    public String getDuration() {
        return duration.getValue();
    }

    public PropertyObject getDurationObject() { return duration; }

    public void setAmplitude(String val) {this.amplitude.setValue(val.trim());}

    public String getAmplitude() {
        return amplitude.getValue();
    }

    public void setPeriodSeconds(String val) {this.periodSeconds.setValue(val.trim());}

    public String getPeriodSeconds() {
        return periodSeconds.getValue();
    }

    public PropertyObject getPeriodSecondsObject() { return periodSeconds; }

    public void setDirectionDegrees(String val) {this.directionDegrees.setValue(val.trim());}

    public String getDirectionDegrees() {
        return directionDegrees.getValue();
    }

    public void setPhaseVelocity(String val) {this.phaseVelocity.setValue(val.trim());}

    public String getPhaseVelocity() {
        return phaseVelocity.getValue();
    }

    public PropertyObject getPhaseVelocityObject() { return phaseVelocity; }

    public void setAngleOfIncidence(String val) {this.angleOfIncidence.setValue(val.trim());}

    public String getAngleOfIncidence() {
        return angleOfIncidence.getValue();
    }

    public void setBackAzimuthResidual(String val) {this.backAzimuthResidual.setValue(val.trim());}

    public String getBackAzimuthResidual() {
        return backAzimuthResidual.getValue();
    }

    public void setTravelTimeResidual(String val) {this.travelTimeResidual.setValue(val.trim());}

    public String getTravelTimeResidual() {
        return travelTimeResidual.getValue();
    }

    public PropertyObject getTravelTimeResidualObject() { return travelTimeResidual; }

    public void setWeight(String val) {this.weight.setValue(val.trim());}

    public String getWeight() {
        return weight.getValue();
    }

    public void setEpicentralDistance(String val) {this.epicentralDistance.setValue(val.trim());}

    public String getEpicentralDistance() {
        return epicentralDistance.getValue();
    }

    public PropertyObject getEpicentralDistanceObject() { return epicentralDistance; }



    @XmlTransient // used for sorting
    public Integer getEpiDistInt() {
        String value = epicentralDistance.getValue();
        return value != null ? (int) Double.parseDouble(value) : 0;
    }

    public void setAzimuthAtSource(String val) {this.azimuthAtSource.setValue(val.trim());}

    public String getAzimuthAtSource() {
        return azimuthAtSource.getValue();
    }

    public PropertyObject getAzimuthAtSourceObject() { return azimuthAtSource; }


}
