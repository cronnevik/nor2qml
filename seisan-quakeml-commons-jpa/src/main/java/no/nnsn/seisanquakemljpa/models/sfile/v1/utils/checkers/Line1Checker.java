package no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers;

import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.*;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.LimitNumber;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.PropertyObject;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers.helpers.FieldVerifier;

import java.time.LocalDate;

public class Line1Checker {
    public static Line1 checkLine1Values(Line1 line1) {

        // Format checker for all fields
        GeneralChecker.checkNumFormats(line1.getFields());

        // Logical check of values
        FieldVerifier year = checkYearValue(line1.getYearObject());
        FieldVerifier month = checkMonthValue(line1.getMonthObject());
        checkDayValue(line1.getDayObjct(), year, month);
        checkHourValue(line1.getHourObject());
        checkMinutesValue(line1.getMinutesObject());
        checkSecondsValue(line1.getSecondsObject());
        checkDistanceIndicatorValue(line1.getDistanceIndicatorObject());
        checkLatitudeValue(line1.getLatitudeObject());
        checkLongitudeValue(line1.getLongitudeObject());
        checkDepthValue(line1.getDepthObject());
        checkDepthIndicatorValue(line1.getDepthIndicatorObject());
        checkLocatingIndicatorValue(line1.getDepthIndicatorObject());
        checkNumberOfStationsValue(line1.getNumStationsUsedObject());
        checkRmsValue(line1.getRmsTimeResidualsObject());
        FieldVerifier magOneNum = checkMagNumOneValue(line1.getMagOneNoObject());
        checkMagTypeOneValue(line1.getMagOneTypeObject(), magOneNum);
        checkMagAgencyOneValue(line1.getMagOneRepAgencyObject(), magOneNum);
        FieldVerifier magTwoNum = checkMagNumTwoValue(line1.getMagTwoNoObject());
        checkMagTypeTwoValue(line1.getMagTwoTypeObject(), magTwoNum);
        checkMagAgencyTwoValue(line1.getMagTwoRepAgencyObject(), magTwoNum);
        FieldVerifier magThreeNum = checkMagNumThreeValue(line1.getMagThreeNoObject());
        checkMagTypeThreeValue(line1.getMagThreeTypeObject(), magThreeNum);
        checkMagAgencyThreeValue(line1.getMagThreeRepAgencyObject(), magThreeNum);

        return line1;
    }

    private static FieldVerifier checkYearValue(PropertyObject year) {
        if (year.getValue() != null) {
            if (year.getParsable()) {
                // Set upper limit by adding x year to current datetime stamp
                int x = 1;
                LocalDate uLimit = LocalDate.now().plusYears(x);
                Integer y = uLimit.getYear();

                year.setLimits(LimitNumber.ofInt(0, y));
                return GeneralChecker.checkBoundaryOfNumbers(year, false);
            }
        }
        return new FieldVerifier(null, true);
    }
    private static FieldVerifier checkMonthValue(PropertyObject month) {
        if (month.getValue() != null) {
            month.setLimits(LimitNumber.ofInt(0, 12));
            return GeneralChecker.checkBoundaryOfNumbers(month, false);
        }
        return new FieldVerifier(null, true);
    }

    private static void checkDayValue(PropertyObject day, FieldVerifier year, FieldVerifier month) {
        if (day.getValue() != null) {
            if (day.getParsable()) {
                // Can only set lower limit directly
                day.setLimits(LimitNumber.ofInt(0));

                // Upper limit depends on the month
                int dayVal = Integer.parseInt(day.getValue());
                // Check if year and month from line1 can be used
                if (!year.getError() && !month.getError()) {
                    // Check lower limit
                    GeneralChecker.checkBoundaryOfNumbers(day, false);
                    try {
                        LocalDate test = LocalDate.of(
                                (Integer) year.getNumber().getValue(),
                                (Integer) month.getNumber().getValue(),
                                dayVal);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (!month.getError()){
                    System.out.println("WARNING - Year is not valid. Month is valid and checked with leap year: 2020");
                    // Check lower limit
                    GeneralChecker.checkBoundaryOfNumbers(day, false);
                    // Test if the day can be valid for a certain month by a predefined leap year
                    try {
                        LocalDate test = LocalDate.of(
                                2020,
                                (Integer) month.getNumber().getValue(),
                                dayVal);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("WARNING - Year and month are not valid, but checking against the limit of a 31 days month");
                    day.setLimits(LimitNumber.ofInt(0, 31));
                    GeneralChecker.checkBoundaryOfNumbers(day, false);
                }
            }
        }
    }

    public static void checkHourValue(PropertyObject hour) {
        if (hour.getParsable()) {
            hour.setLimits(LimitNumber.ofInt(0, 23));
            GeneralChecker.checkBoundaryOfNumbers(hour, false);
        }
    }

    public static void checkMinutesValue(PropertyObject minutes) {
        if (minutes.getParsable()) {
            minutes.setLimits(LimitNumber.ofInt(0, 59));
            GeneralChecker.checkBoundaryOfNumbers(minutes, false);
        }
    }

    public static void checkSecondsValue(PropertyObject seconds) {
        if (seconds.getParsable()) {
            seconds.setLimits(LimitNumber.ofDouble(0.0, 59.999));
            GeneralChecker.checkBoundaryOfNumbers(seconds, false);
        }
    }

    public static void checkDistanceIndicatorValue(PropertyObject distanceIndicator) {
        if (distanceIndicator.getValue() != null) {
            GeneralChecker.enumChecker(distanceIndicator, DistaceIndicatorType.class);
        }
    }

    public static void checkLatitudeValue(PropertyObject latitude) {
        if (latitude.getParsable()) {
            latitude.setLimits(LimitNumber.ofDouble(-90.0, 90.0));
            GeneralChecker.checkBoundaryOfNumbers(latitude, false);
        }
    }

    public static void checkLongitudeValue(PropertyObject longitude) {
        if (longitude.getParsable()) {
            longitude.setLimits(LimitNumber.ofDouble(-180.0, 180.0));
            GeneralChecker.checkBoundaryOfNumbers(longitude, false);
        }
    }

    public static void checkDepthValue(PropertyObject depth) {
        if (depth.getParsable()) {
            depth.setLimits(LimitNumber.ofDouble(-9.90, 900.0));
            GeneralChecker.checkBoundaryOfNumbers(depth, false);
        }
    }

    public static void checkDepthIndicatorValue(PropertyObject depthIndicator) {
        if (depthIndicator.getValue() != null) {
            GeneralChecker.enumChecker(depthIndicator, DepthIndicatorType.class);
        }
    }

    public static void checkLocatingIndicatorValue(PropertyObject locatingIndicator) {
        if (locatingIndicator.getValue() != null) {
            GeneralChecker.enumChecker(locatingIndicator, LocatingIndicatorType.class);
        }
    }

    public static void checkNumberOfStationsValue(PropertyObject numOfStations) {
        if (numOfStations.getParsable()) {
            numOfStations.setLimits(LimitNumber.ofInt(0));
            GeneralChecker.checkBoundaryOfNumbers(numOfStations, false);
        }
    }

    public static void checkRmsValue(PropertyObject rms) {
        if (rms.getParsable()) {
            rms.setLimits(LimitNumber.ofDouble(0.0));
            GeneralChecker.checkBoundaryOfNumbers(rms, false);
        }
    }

    public static FieldVerifier checkMagNumOneValue(PropertyObject magNumOne) {
        if (magNumOne.getParsable()) {
            magNumOne.setLimits(LimitNumber.ofDouble(-2.0, 10.0));
            return GeneralChecker.checkBoundaryOfNumbers(magNumOne, false);
        } else {
            return null;
        }
    }

    public static void checkMagTypeOneValue(PropertyObject magTypeOne, FieldVerifier magNumOne) {
        if (magTypeOne.getValue() != null) {
            GeneralChecker.enumChecker(magTypeOne, MagniudeType.class);
        } else {
            if (magNumOne != null) {
                if (magNumOne.getNumber().getValue() != null) {
                    System.out.println(magTypeOne.getName() + " is missing for magnitude: " + magNumOne.getNumber().getValue());
                }
            }
        }
    }

    public static void checkMagAgencyOneValue(PropertyObject magAgencyOne, FieldVerifier magNumOne) {
        if (magAgencyOne != null) {
            GeneralChecker.checkForAlphaNumericValues(magAgencyOne);
        } else {
            if (magNumOne != null) {
                if (magNumOne.getNumber().getValue() != null) {
                    System.out.println(magAgencyOne.getName() + " is missing for magnitude: " + magNumOne.getNumber().getValue());
                }
            }
        }
    }


    public static FieldVerifier checkMagNumTwoValue(PropertyObject magNumTwo) {
        if (magNumTwo.getParsable()) {
            magNumTwo.setLimits(LimitNumber.ofDouble(-2.0, 10.0));
            return GeneralChecker.checkBoundaryOfNumbers(magNumTwo, false);
        } else {
            return null;
        }
    }

    public static void checkMagTypeTwoValue(PropertyObject magTypeTwo, FieldVerifier magNumTwo) {
        if (magTypeTwo.getValue() != null) {
            GeneralChecker.enumChecker(magTypeTwo, MagniudeType.class);
        } else {
            if (magNumTwo != null) {
                if (magNumTwo.getNumber().getValue() != null) {
                    System.out.println(magTypeTwo.getName() + " is missing for magnitude: " + magNumTwo.getNumber().getValue());
                }
            }
        }
    }

    public static void checkMagAgencyTwoValue(PropertyObject magAgencyTwo, FieldVerifier magNumTwo) {
        if (magAgencyTwo != null) {
            GeneralChecker.checkForAlphaNumericValues(magAgencyTwo);
        } else {
            if (magNumTwo != null) {
                if (magNumTwo.getNumber().getValue() != null) {
                    System.out.println(magAgencyTwo.getName() + " is missing for magnitude: " + magNumTwo.getNumber().getValue());
                }
            }
        }
    }

    public static FieldVerifier checkMagNumThreeValue(PropertyObject magNumThree) {
        if (magNumThree.getParsable()) {
            magNumThree.setLimits(LimitNumber.ofDouble(-2.0, 10.0));
            return GeneralChecker.checkBoundaryOfNumbers(magNumThree, false);
        } else {
            return null;
        }
    }

    public static void checkMagTypeThreeValue(PropertyObject magTypeThree, FieldVerifier magNumThree) {
        if (magTypeThree.getValue() != null) {
            GeneralChecker.enumChecker(magTypeThree, MagniudeType.class);
        } else {
            if (magNumThree != null) {
                if (magNumThree.getNumber().getValue() != null) {
                    System.out.println(magTypeThree.getName() + " is missing for magnitude: " + magNumThree.getNumber().getValue());
                }
            }
        }
    }

    public static void checkMagAgencyThreeValue(PropertyObject magAgencyThree, FieldVerifier magNumThree) {
        if (magAgencyThree != null) {
            GeneralChecker.checkForAlphaNumericValues(magAgencyThree);
        } else {
            if (magNumThree != null) {
                if (magNumThree.getNumber().getValue() != null) {
                    System.out.println(magAgencyThree.getName() + " is missing for magnitude: " + magNumThree.getNumber().getValue());
                }
            }
        }
    }
}
