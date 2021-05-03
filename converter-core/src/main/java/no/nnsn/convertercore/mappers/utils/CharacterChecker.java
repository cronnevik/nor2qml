package no.nnsn.convertercore.mappers.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Check if a string follows a certain character pattern
 *
 * @author Christian Rønnevik
 */
public class CharacterChecker {

    private static String alpaRegex = "[a-zA-Z]";
    private static String digits = "[0-9]+";

    /**
     * Check if a string only contains letters of lower and uppercase (a-z and A-Z).
     *
     * @param text Text to be checked
     * @return Boolean
     */
    public static Boolean onlyAlphabetic(String text) {
        Pattern p = Pattern.compile(alpaRegex);
        Matcher m = p.matcher(text);
        return m.find();
    }

    public static Boolean noNumbers(String text) {
        Pattern p = Pattern.compile(digits);
        Matcher m = p.matcher(text);
        return !m.find();
    }

    public static Boolean onlyNumbers(String text) {
        Pattern p = Pattern.compile(digits);
        Matcher m = p.matcher(text);
        return m.find();
    }
}
