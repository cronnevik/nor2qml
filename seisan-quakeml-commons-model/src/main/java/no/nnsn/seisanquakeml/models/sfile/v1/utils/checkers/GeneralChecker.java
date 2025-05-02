package no.nnsn.seisanquakeml.models.sfile.v1.utils.checkers;

import no.nnsn.seisanquakeml.models.sfile.v1.enums.PropertyType;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.checkers.helpers.FieldVerifier;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.checkers.helpers.NumberValue;

import java.util.EnumSet;
import java.util.List;

public class GeneralChecker {

    public static void checkNumFormats(List<PropertyObject> objects) {
        objects.forEach(o -> {
            if(o.getValue() != null) {
                PropertyType format = o.getFormat();
                if (format.equals(PropertyType.DOUBLE) || format.equals(PropertyType.DOUBLEOREXPONENTIAL) || format.equals(PropertyType.EXPONENTIAL)) {
                    try {
                        Double.parseDouble(o.getValue());
                        o.setParsable(true);
                    } catch (Exception e) {
                        System.out.println(o.getName() + " with value " + o.getValue() + " cannot be parsed to double/float");
                        o.setParsable(false);
                    }
                } else if (format.equals(PropertyType.INTEGER)) {
                    try {
                        Integer.parseInt(o.getValue());
                        o.setParsable(true);
                    } catch (Exception e) {
                        System.out.println(o.getName() + " with value " + o.getValue() + " cannot be parsed to integer");
                        o.setParsable(false);
                    }
                }
            }
        });
    }

    public static void checkForAlphaNumericValues(PropertyObject object) {
        String alphaNumeric = "^[a-zA-Z0-9]*$";
        if (!object.getValue().matches(alphaNumeric)) {
            System.out.println(object.getName() + "value (" + object.getValue() + ") consist of other characters than letters and numbers");
        }
    }

    public static <E extends Enum<E>> void enumChecker(PropertyObject obj, Class<E> enumType) {
        EnumSet<E> types = EnumSet.allOf(enumType);
        try {
           boolean contains = false;
            for (E e: enumType.getEnumConstants()) {
                if (e.toString().equals(obj.getValue())) {
                    contains = true;
                }
            }
            if (!contains) {
                System.out.println(obj.getName() + " value (" + obj.getValue()
                        + ") is not included in the nordic format description and is therefore set to blank."
                        + " Allowed values are: " + types.toString());
                obj.setValue("");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static FieldVerifier checkBoundaryOfNumbers(PropertyObject obj, Boolean setValueToLimit) {
        final PropertyType format = obj.getFormat();
        String objName = obj.getName();
        String message = "Value is set to: ";
        boolean error = false;
        NumberValue num = null;

        switch (format) {
            case DOUBLE:
                Double dVal = Double.parseDouble(obj.getValue());

                if (obj.getLimits().getLower() != null) {
                    final Double lowerLimitDouble = (Double) obj.getLimits().getLower();
                    String dLowerMessage = lowerLimitChecker(dVal, objName, lowerLimitDouble);
                    num = NumberValue.ofDouble(dVal);

                    if (dLowerMessage != null) {
                        error = true;
                        if (setValueToLimit) {
                            obj.setValue(lowerLimitDouble.toString());
                            System.out.println(dLowerMessage + ". " + message + lowerLimitDouble);
                        } else {
                            System.out.println(dLowerMessage);
                        }
                    }
                }

                if (obj.getLimits().getUpper() != null) {
                    final Double upperLimitDouble = (Double) obj.getLimits().getUpper();
                    String dUpperMessage = upperLimitChecker(dVal, objName, upperLimitDouble);
                    num = NumberValue.ofDouble(dVal);

                    if (dUpperMessage != null) {
                        error = true;
                        if (setValueToLimit) {
                            obj.setValue(upperLimitDouble.toString());
                            System.out.println(dUpperMessage + ". " + message + upperLimitDouble);
                        } else {
                            System.out.println(dUpperMessage);
                        }
                    }
                }
                break;
            case INTEGER:
                Integer iVal = Integer.parseInt(obj.getValue());
                if (obj.getLimits().getLower() != null) {
                    final Integer lowerLimitInt = (Integer) obj.getLimits().getLower();
                    String iLowerMessage = lowerLimitChecker(iVal, objName, lowerLimitInt);
                    num = NumberValue.ofInt(iVal);

                    if (iLowerMessage != null) {
                        error = true;
                        if (setValueToLimit) {
                            obj.setValue(lowerLimitInt.toString());
                            System.out.println(iLowerMessage + ". " + message + lowerLimitInt);
                        } else {
                            System.out.println(iLowerMessage);
                        }
                    }
                }

                if (obj.getLimits().getUpper() != null) {
                    final Integer upperLimitInt = (Integer) obj.getLimits().getUpper();
                    String iUpperMessage = upperLimitChecker(iVal, objName, upperLimitInt);
                    num = NumberValue.ofInt(iVal);

                    if (iUpperMessage != null) {
                        error = true;
                        if (setValueToLimit) {
                            obj.setValue(upperLimitInt.toString());
                            System.out.println(iUpperMessage + ". " + message + upperLimitInt);
                        } else {
                            System.out.println(iUpperMessage);
                        }
                    }
                }
                break;
        }

        return new FieldVerifier(num, error);
    }

    private static String lowerLimitChecker(Double value, String name, Double lowerLimit) {
        if (lowerLimit != null) {
            if (value < lowerLimit) {
                return name + " value (" + value + ") is lower than " + lowerLimit;
            }
        }
        return null;
    }

    private static String upperLimitChecker(Double value, String name, Double upperLimit) {
        if (upperLimit != null) {
            if (value >= upperLimit) {
                return name + " value (" + value + ") is higher than " + upperLimit;
            }
        }
        return null;
    }

    private static String lowerLimitChecker(Integer value, String name, Integer lowerLimit) {
        if (lowerLimit != null) {
            if (value < lowerLimit) {
                return name + " value (" + value + ") is lower than " + lowerLimit;
            }
        }
        return null;
    }

    private static String upperLimitChecker(Integer value, String name, Integer upperLimit) {
        if (upperLimit != null) {
            if (value >= upperLimit) {
                return name + " value (" + value + ") is higher than " + upperLimit;
            }
        }
        return null;
    }
}
