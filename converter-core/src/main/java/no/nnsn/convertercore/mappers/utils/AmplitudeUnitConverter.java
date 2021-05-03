package no.nnsn.convertercore.mappers.utils;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Amplitude unit converter.
 * Unit in QuakeML is dependent of the amplitude type and can be represented as displacement, velocity, or a period.
 * Seisan can also hold different amplitude types, but normally uses nm and nm/s, while QuakeML uses m and m/s.
 * Conversion methods is thus needed for these units.
 *
 * @author Christian Rønnevik
 */
public class AmplitudeUnitConverter {

    // Decimalformat to return value with 2 decimals. Use of locale us to get point instead of comma
    private static DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    private static DecimalFormat df1 = new DecimalFormat("0.0", symbols);
    private static DecimalFormat df2 = new DecimalFormat("0.00", symbols);

    /*
     *   Seisan to QuakeML
     */

    /**
     * Convert nanometer (nm) to meter (m).
     *
     * @param nmValue Nanometer value (Double).
     * @return Double value in meter (m).
     */
    public static Double fromNmToM(Double nmValue) {
        return nmValue * Math.pow(10, -9);
    }
    /**
     * Convert nanometre per second (nm/s) to metre per second (m/s).
     *
     * @param nmsValue Nanometre per second value (Double).
     * @return Double value in metre per second (m/s).
     */
    public static Double fromNmsToMs(Double nmsValue) {
        return nmsValue * Math.pow(10, -9);
    }

    /*
     *   QuakeML to Seisan
     */

    /**
     * Convert meter (m) to nanometer (nm).
     *
     * @param mValue Meter value (Double).
     * @return Double value in nanometer (nm).
     */
    public static String fromMToNm(String mValue){
        Double ampValue = Double.parseDouble(mValue);
        Double converterAmpVal = ampValue * Math.pow(10, 9);
        return df2.format(converterAmpVal);
    }

    /**
     * Convert metre per second (m/s) to nanometre per second (nm/s).
     *
     * @param msValue Metre per second value (Double).
     * @return Double value in nanometre per second (nm/s).
     */
    public static String fromMsToNms(String msValue) {
        Double ampValue = Double.parseDouble(msValue);
        Double converterAmpVal = ampValue * Math.pow(10, 9);
        return df2.format(converterAmpVal);
    }

}
