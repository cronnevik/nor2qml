package no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers;

import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.ComponentType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.InstrumentType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.LimitNumber;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Line4Checker {

    public static Line4 checkLine4Values(Line4 line4) {

        // Special case formatting
        formatEpicentralDistance(line4.getEpicentralDistanceObject());

        // Format checker for all fields
        GeneralChecker.checkNumFormats(line4.getFields());

        // Logical check of values
        checkStationNameValue(line4.getStationNameObject());
        checkInstrumentTypeValue(line4.getInstrumentTypeObject());
        checkComponentValue(line4.getComponentObject());
        checkHourValue(line4.getHourObject());
        checkMinutesValue(line4.getMinutesObject());
        checkSecondsValue(line4.getSecondsObject());
        checkDurationValue(line4.getDurationObject());
        checkPeriodValue(line4.getPeriodSecondsObject());
        checkPhaseVelocityValue(line4.getPhaseVelocityObject());
        checkTimeResidualValue(line4.getTravelTimeResidualObject());

        return line4;
    }

    /** Formatting */
    // Special case format of distance
    private static void formatEpicentralDistance(PropertyObject epicentralDistance) {
        if (epicentralDistance.getValue() != null) {
            Double distValue = Double.parseDouble(epicentralDistance.getValue());

            String pattern = "";
            if (distValue < 10) {
                pattern = "#.##";
            } else if ((distValue >= 10) && distValue < 100) {
                pattern = "##.#";
            } else if ((distValue >= 100) && (distValue < 1000)) {
                pattern = "###";
                epicentralDistance.setFormat(PropertyType.INTEGER);
            } else if ((distValue >= 1000) && (distValue < 10000)) {
                pattern = "####";
                epicentralDistance.setFormat(PropertyType.INTEGER);
            } else {
                pattern = "#####";
                epicentralDistance.setFormat(PropertyType.INTEGER);
            }
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
            DecimalFormat df = (DecimalFormat)nf;
            df.applyPattern(pattern);

            epicentralDistance.setValue(df.format(distValue));
        }
    }

    // Special case format of distance
    // private static void format

    /** Value Cheks */

    private static void checkStationNameValue(PropertyObject stationName) {
        if (stationName.getValue() != null) {
            GeneralChecker.checkForAlphaNumericValues(stationName);
        }
    }

    private static void checkInstrumentTypeValue(PropertyObject instrumentType) {
        if (instrumentType.getValue() != null) {
            GeneralChecker.enumChecker(instrumentType, InstrumentType.class);
        }
    }

    private static void checkComponentValue(PropertyObject component) {
        if (component.getValue() != null) {
            GeneralChecker.enumChecker(component, ComponentType.class);
        }
    }

    private static void checkHourValue(PropertyObject hour) {
        if (hour.getValue() != null) {
            if (hour.getParsable()) {
                hour.setLimits(LimitNumber.ofInt(0,48));
                GeneralChecker.checkBoundaryOfNumbers(hour, false);
            }
        }
    }

    private static void checkMinutesValue(PropertyObject minutes) {
        if (minutes.getValue() != null) {
            if (minutes.getParsable()) {
                minutes.setLimits(LimitNumber.ofInt(0,60));
                GeneralChecker.checkBoundaryOfNumbers(minutes, false);
            }
        }
    }

    private static void checkSecondsValue(PropertyObject seconds) {
        if (seconds.getValue() != null) {
            if (seconds.getParsable()) {
                seconds.setLimits(LimitNumber.ofDouble(0.0, 500.0));
                GeneralChecker.checkBoundaryOfNumbers(seconds, false);
            }
        }
    }

    private static void checkDurationValue(PropertyObject duration) {
        if (duration.getValue() != null) {
            if (duration.getParsable()) {
                duration.setLimits(LimitNumber.ofInt(0));
                GeneralChecker.checkBoundaryOfNumbers(duration, false);
            }
        }
    }

    private static void checkPeriodValue(PropertyObject period) {
        if (period.getValue() != null) {
            if (period.getParsable()) {
                period.setLimits(LimitNumber.ofDouble(0.00));
                GeneralChecker.checkBoundaryOfNumbers(period, false);
            }
        }
    }

    private static void checkPhaseVelocityValue(PropertyObject phaseVelocity) {
        if (phaseVelocity.getValue() != null) {
            // First check/try if the value can be parsed to double
            if (phaseVelocity.getParsable()) {
                phaseVelocity.setLimits(LimitNumber.ofDouble(-99.9, 99.9));
                GeneralChecker.checkBoundaryOfNumbers(phaseVelocity, true);
            }
        }
    }

    private static void checkTimeResidualValue(PropertyObject timeResidual) {
        if (timeResidual.getValue() != null) {
            // First check/try if the value can be parsed to double
            if (timeResidual.getParsable()) {
                timeResidual.setLimits(LimitNumber.ofDouble(-99.9, 99.9));
                GeneralChecker.checkBoundaryOfNumbers(timeResidual, true);
            }
        }
    }
}
