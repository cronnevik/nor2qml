package no.nnsn.seisanquakemljpa.models.sfile.v1.utils;

import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyType;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PropertyFixes {

    public static String fixLineFormat(PropertyObject obj, Boolean rightAlignment) {
        if (obj.getFormat().equals(PropertyType.INTEGER) && obj.getValue() != null) {
            fixIntegerFormat(obj);
        }
        if (obj.getFormat().equals(PropertyType.DOUBLE) && obj.getValue() != null) {
            fixDoubleFormat(obj);
        }
        if (obj.getFormat().equals(PropertyType.DOUBLEORSTRING) && obj.getValue() != null) {
            fixDoubleFormat(obj);
        }
        if (obj.getFormat().equals(PropertyType.DOUBLEOREXPONENTIAL) && obj.getValue() != null) {
            fixDoubleOrExponentialFormat(obj);
        }
        if (obj.getFormat().equals(PropertyType.EXPONENTIAL) && obj.getValue() != null) {
            fixExponentialFormat(obj);
        }
        String lineFixed = fixLineSpace(obj, rightAlignment);
        return lineFixed;
    }

    private static String fixLineSpace(PropertyObject obj, Boolean emptyFirst) {
        int currentLength = 0;
        if (obj.getValue() != null) {
            currentLength = obj.getValue().trim().length();
        }

        int expectedLength = obj.getLengthSize();

        if (currentLength != expectedLength) {
            String wSpace = " ";

            String[] valSplit = new String[0];
            if (obj.getValue() != null) {
                String valueGet = obj.getValue().trim();
                if (valueGet.length() > 0) {
                    valSplit = valueGet.split("(?!^)");
                }

            }

            String newVal = "";
            String emptySpace = "";
            for (int i = 1; i <= expectedLength; i++) {
                if ((expectedLength - valSplit.length) >= i) {
                    emptySpace += wSpace;
                } else if ((valSplit.length - expectedLength) > 0 ) {
                    newVal +=  valSplit[i-1];
                } else {
                    int diff = expectedLength - valSplit.length;
                    newVal += valSplit[i - 1 - diff];
                }
            }
            return emptyFirst == true ? (emptySpace + newVal) : (newVal + emptySpace);
        } else {
            return obj.getValue();
        }
    }

    private static void fixIntegerFormat(PropertyObject obj) {
        String value = obj.getValue();
        Double d = null;

        if (value.contains(".")) {
            d = Double.valueOf(obj.getValue());
            Integer intVal = d.intValue();
            value = intVal.toString();
        }

        int valueLengthSize = value.length();
        int objLengthSize = obj.getLengthSize();

        if (valueLengthSize > objLengthSize) {
            System.out.println("Value: " + value + " is too large for columns " + obj.getColumnStart() + " - " + obj.getColumnEnd());
            value = "";
        }

        obj.setValue(value);

    }

    private static void fixDoubleFormat(PropertyObject obj) {
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        DecimalFormat formatter = (DecimalFormat) nf;

        String value = obj.getValue();

        if (value.contains(".")) {
            Double d = Double.valueOf(obj.getValue());
            int decimals = obj.getDecimals();

            // If number of decimals is given, format the number accordingly
            if (decimals > 0) {
                String pattern = "";
                if (obj.getLeadingZero() == false) {
                    pattern = ".";
                } else {
                    pattern = "0.";
                }
                for (int i = 0; i < decimals; i ++) {
                    pattern += "0";
                }
                formatter.applyPattern(pattern);
                obj.setValue(formatter.format(d));
            }

            else if (decimals == 0){
                Integer iNum = (int) Math.round(d);
                obj.setValue(iNum.toString());
            }

            value = obj.getValue();

            int valueLengthSize = obj.getValue().length();
            int objLengthSize = obj.getLengthSize();

            // Check if the number exceeds the character limit
            if (valueLengthSize > objLengthSize) {
                String[] split = value.split("\\.");
                int before = split[0].length();
                int after = split[1].length();

                String format = "";
                // Rounded value should at least have . and a number after (1+1) within the limit of objLengthSize
                if ((before + 2) <= objLengthSize && value.contains(".")) {
                    for (int i = 0; i < before; i++) {
                        format += "#";
                    }
                    format += ".";

                    for (int i = 0; i < (objLengthSize - before -1); i++) {
                        format += "#";
                    }

                    formatter.applyPattern(format);
                    obj.setValue(formatter.format(d));
                } else if (split[1].equals("0")) { // can represent as a whole number
                    obj.setValue(split[0]);
                } else {
                    int diff = obj.getColumnEnd() - obj.getColumnStart();
                    System.out.println(
                            "Field: " + obj.getName() +  " ,Value: " + value +
                            " is too large for columns " + obj.getColumnStart() +
                            " - " + obj.getColumnEnd() + ". Value length: " + obj.getValue().length() +
                            ". Allowed length is " + (diff + 1) + ". Value is therefore ignored.");
                    obj.setValue("");
                }

            }
        }
    }

    private static void fixDoubleOrExponentialFormat(PropertyObject obj) {
        if (obj.getValue().contains(".")) {
            Double d = Double.valueOf(obj.getValue());
            int valueLengthSize = obj.getValue().length();
            int objLengthSize = obj.getLengthSize();
            //Only modify formatting if number of character exceeds the nordic format limit
            if (valueLengthSize > objLengthSize) {
                int midFormatCount = valueLengthSize - 3 - 2;
                if (midFormatCount > 0) {
                    String initialFormat = "#0.";
                    String endFormat = "E0";

                    String format = initialFormat;
                    for (int i = 0; i < midFormatCount; i++) {
                        format += "#";
                    }
                    format += endFormat;

                    NumberFormat nf = NumberFormat.getInstance(Locale.US);
                    DecimalFormat formatter = (DecimalFormat) nf;
                    formatter.applyPattern(format);

                    obj.setValue(formatter.format(d));
                }
            }
            // Else - do nothing by keeping the incomming value
        }
    }

    private static void fixExponentialFormat(PropertyObject obj) {
        String format = "0.0000E0";
        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        DecimalFormat formatter = (DecimalFormat) nf;
        formatter.applyPattern(format);

        if (obj.getValue().contains(".")) {
            Double d = Double.valueOf(obj.getValue());
            obj.setValue(formatter.format(d));
        }

    }

    public static String fixLongPhasenames(
            PropertyObject phaseID,
            PropertyObject weightingIndicator,
            PropertyObject flagA,
            PropertyObject firstMotion,
            PropertyObject extLastPhaseIdChar) {

        // Check if phase ID converted from QuakeML is longer than the property of phase ID format
        // and if so, extend the phaseid across column 11-18 (8 chars)
        if (phaseID.getValue() != null) {
            int phaseIDLength = phaseID.getValue().length();
            if (phaseIDLength > 4) {
                PropertyObject newPropObj = new PropertyObject("Long phase name", 11, 18, PropertyType.STRING);
                newPropObj.setValue(phaseID.getValue());
                return fixLineSpace(newPropObj, false);

            } else {
                return
                        fixLineFormat(phaseID, false)
                        + fixLineFormat(weightingIndicator, true)
                        + fixLineFormat(flagA, true)
                        + fixLineFormat(firstMotion, true)
                        + fixLineFormat(extLastPhaseIdChar, true);
            }
        }
        return
                fixLineFormat(phaseID, false)
                + fixLineFormat(weightingIndicator, true)
                + fixLineFormat(flagA, true)
                + fixLineFormat(firstMotion, true)
                + fixLineFormat(extLastPhaseIdChar, true);
    }
}
