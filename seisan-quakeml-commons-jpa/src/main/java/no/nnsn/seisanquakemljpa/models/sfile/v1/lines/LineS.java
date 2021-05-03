package no.nnsn.seisanquakemljpa.models.sfile.v1.lines;

import lombok.EqualsAndHashCode;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.ValueSetter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
@EqualsAndHashCode
public class LineS extends Line {
    private PropertyObject station =
            new PropertyObject("Station", 2, 6, PropertyType.STRING);
    private PropertyObject component =
            new PropertyObject("Component", 7, 9, PropertyType.STRING);
    private PropertyObject network =
            new PropertyObject("Network", 10, 11, PropertyType.STRING);
    private PropertyObject location =
            new PropertyObject("Location", 12, 13, PropertyType.STRING);
    private PropertyObject logOmega =
            new PropertyObject("Log Omega0", 15, 18, PropertyType.DOUBLE);
    private PropertyObject cornerFrequency =
            new PropertyObject("Corner frequency(Hz)", 19, 22, PropertyType.DOUBLE);
    private PropertyObject spectralSlope =
            new PropertyObject("Spectral slope", 23, 25, PropertyType.DOUBLE);
    private PropertyObject hourMinSec =
            new PropertyObject("Hour Min Sec for start of spectrum", 26, 31, PropertyType.INTEGER);
    private PropertyObject windowLength =
            new PropertyObject("Window length(s)", 32, 35, PropertyType.DOUBLE);
    private PropertyObject geoDistance =
            new PropertyObject("Geo-distance(km)", 36, 40, PropertyType.DOUBLE);
    private PropertyObject logMoment =
            new PropertyObject("Log moment", 41, 44, PropertyType.DOUBLE);
    private PropertyObject stressDrop =
            new PropertyObject("Stress drop(bar)", 45, 47, PropertyType.DOUBLE);
    private PropertyObject sourceRadius =
            new PropertyObject("Source radius(km)", 48, 51, PropertyType.DOUBLE);
    private PropertyObject kappa =
            new PropertyObject("Kappa", 52, 55, PropertyType.DOUBLE);
    private PropertyObject velocityAtSource =
            new PropertyObject("Velocity at source(km/s)", 56, 59, PropertyType.DOUBLE);
    private PropertyObject typeOfSpectrum =
            new PropertyObject("Type of spectrum,, P or S", 60, 60, PropertyType.STRING);
    private PropertyObject densityAtSource =
            new PropertyObject("Density at source (g/cm*cm*cm)", 61, 64, PropertyType.DOUBLE);
    private PropertyObject qZero =
            new PropertyObject("Q0", 65, 68, PropertyType.DOUBLE);
    private PropertyObject qAlpha =
            new PropertyObject("Q-alpha", 69, 72, PropertyType.DOUBLE);
    private PropertyObject qOne = new PropertyObject(
            "Frequency(Hz) below which Q(f) changes to constant Q", 73, 75, PropertyType.DOUBLE);
    private PropertyObject momentMag =
            new PropertyObject("Moment magnitude", 76, 79, PropertyType.DOUBLE);

    public LineS() {
        this.initFields();
    }

    public LineS(String lineText, int rowNum) {
        this.initFields();
        this.setLineInfo(lineText, rowNum);
        this.setValues();
    }

    private void initFields() {
        this.lineNumber = "S";
        fields = new ArrayList<PropertyObject>();
        fields.add(station);
        fields.add(component);
        fields.add(network);
        fields.add(location);
        fields.add(logOmega);
        fields.add(cornerFrequency);
        fields.add(spectralSlope);
        fields.add(hourMinSec);
        fields.add(windowLength);
        fields.add(geoDistance);
        fields.add(logMoment);
        fields.add(stressDrop);
        fields.add(sourceRadius);
        fields.add(kappa);
        fields.add(velocityAtSource);
        fields.add(typeOfSpectrum);
        fields.add(densityAtSource);
        fields.add(qZero);
        fields.add(qAlpha);
        fields.add(qOne);
        fields.add(momentMag);
    }

    public void setValues() {
        ValueSetter.setPropObjValues(fields, this.lineText);
    }

    public String createLine() {
        String returnText = "";
        if (lineText != null && !lineText.isEmpty()) {
            returnText = lineText;
        }
        return returnText;
    }

    public String getStation() {
        return station.getValue();
    }
    public void setStation(String station) {
        this.station.setValue(station);
    }
    public PropertyObject getStationObject() { return station; }

    public String getComponent() {
        return component.getValue();
    }
    public void setComponent(String component) {
        this.component.setValue(component);
    }
    public PropertyObject getComponentObject() {
        return component;
    }

    public String getNetwork() {
        return network.getValue();
    }
    public void setNetwork(String network) {
        this.network.setValue(network);
    }
    public PropertyObject getNetworkObject() {
        return network;
    }

    public String getLocation() {
        return location.getValue();
    }
    public void setLocation(String location) {
        this.location.setValue(location);
    }
    public PropertyObject getLocationObject() {
        return location;
    }

    public String getLogOmega() {
        return logOmega.getValue();
    }
    public void setLogOmega(String logOmega) {
        this.logOmega.setValue(logOmega);
    }
    public PropertyObject getLogOmegaObject() {
        return logOmega;
    }

    public String getCornerFrequency() {
        return cornerFrequency.getValue();
    }
    public void setCornerFrequency(String cornerFrequency) {
        this.cornerFrequency.setValue(cornerFrequency);
    }
    public PropertyObject getCornerFrequencyObject() {
        return cornerFrequency;
    }

    public String getSpectralSlope() {
        return spectralSlope.getValue();
    }
    public void setSpectralSlope(String spectralSlope) {
        this.spectralSlope.setValue(spectralSlope);
    }
    public PropertyObject getSpectralSlopeObject() {
        return spectralSlope;
    }

    public String getHourMinSec() {
        return hourMinSec.getValue();
    }
    public void setHourMinSec(String hourMinSec) {
        this.hourMinSec.setValue(hourMinSec);
    }
    public PropertyObject getHourMinSecObject() {
        return hourMinSec;
    }

    public String getWindowLength() {
        return windowLength.getValue();
    }
    public void setWindowLength(String windowLength) {
        this.windowLength.setValue(windowLength);
    }
    public PropertyObject getWindowLengthObject() {
        return windowLength;
    }

    public String getGeoDistance() {
        return geoDistance.getValue();
    }
    public void setGeoDistance(String geoDistance) {
        this.geoDistance.setValue(geoDistance);
    }
    public PropertyObject getGeoDistanceObject() {
        return geoDistance;
    }

    public String getLogMoment() {
        return logMoment.getValue();
    }
    public void setLogMoment(String logMoment) {
        this.logMoment.setValue(logMoment);
    }
    public PropertyObject getLogMomentObject() {
        return logMoment;
    }

    public String getStressDrop() {
        return stressDrop.getValue();
    }
    public void setStressDrop(String stressDrop) {
        this.stressDrop.setValue(stressDrop);
    }
    public PropertyObject getStressDropObject() {
        return stressDrop;
    }

    public String getSourceRadius() {
        return sourceRadius.getValue();
    }
    public void setSourceRadius(String sourceRadius) {
        this.sourceRadius.setValue(sourceRadius);
    }
    public PropertyObject getSourceRadiusObject() {
        return sourceRadius;
    }

    public String getKappa() {
        return kappa.getValue();
    }
    public void setKappa(String kappa) {
        this.kappa.setValue(kappa);
    }
    public PropertyObject getKappaObject() {
        return kappa;
    }

    public String getVelocityAtSource() {
        return velocityAtSource.getValue();
    }
    public void setVelocityAtSource(String velocityAtSource) {
        this.velocityAtSource.setValue(velocityAtSource);
    }
    public PropertyObject getVelocityAtSourceObject() {
        return velocityAtSource;
    }

    public String getTypeOfSpectrum() {
        return typeOfSpectrum.getValue();
    }
    public void setTypeOfSpectrum(String typeOfSpectrum) {
        this.typeOfSpectrum.setValue(typeOfSpectrum);
    }
    public PropertyObject getTypeOfSpectrumObject() {
        return typeOfSpectrum;
    }

    public String getDensityAtSource() {
        return densityAtSource.getValue();
    }
    public void setDensityAtSource(String densityAtSource) {
        this.densityAtSource.setValue(densityAtSource);
    }
    public PropertyObject getDensityAtSourceObject() {
        return densityAtSource;
    }

    public String getqZero() {
        return qZero.getValue();
    }
    public void setqZero(String qZero) {
        this.qZero.setValue(qZero);
    }
    public PropertyObject getqZeroObject() {
        return qZero;
    }

    public String getqAlpha() {
        return qAlpha.getValue();
    }
    public void setqAlpha(String qAlpha) {
        this.qAlpha.setValue(qAlpha);
    }
    public PropertyObject getqAlphaObject() {
        return qAlpha;
    }

    public String getqOne() {
        return qOne.getValue();
    }
    public void setqOne(String qOne) {
        this.qOne.setValue(qOne);
    }
    public PropertyObject getqOneObject() {
        return qOne;
    }

    public String getMomentMag() {
        return momentMag.getValue();
    }
    public void setMomentMag(String momentMag) {
        this.momentMag.setValue(momentMag);
    }
    public PropertyObject getMomentMagObject() {
        return momentMag;
    }
}
