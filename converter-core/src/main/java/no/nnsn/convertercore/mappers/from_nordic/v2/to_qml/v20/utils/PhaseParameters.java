package no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils;

import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhaseParameters {

    private static String polarityPattern = "C|D";

    public static ParameterOneType identifyParameterOneType(String paramOne, String phaseID) {
        if (paramOne != null && !paramOne.isEmpty()) {
            // Polarity
            Pattern r = Pattern.compile(polarityPattern);
            Matcher m = r.matcher(paramOne);
            if (m.find()) {
                return ParameterOneType.POLARITY;
            }
        }
        if (phaseID != null && !phaseID.isEmpty()) {
            // Duration
            if (phaseID.equals("END")) {
                return ParameterOneType.DURATION;
            }

            // Back azimuth
            if (phaseID.length() >= 3) {
                String substring = phaseID.substring(0, 3);
                if (substring.equals("BAZ")) {
                    return ParameterOneType.BACK_AZIMUTH;
                }
            }

            // Amplitude phase
            if (phaseID.length() >= 1) {
                String substringOne = phaseID.substring(0, 1);
                if (substringOne.equals("A")) {
                    return ParameterOneType.AMPLITUDE;
                }

                if (phaseID.length() >=2) {
                    String substringTwo = phaseID.substring(0, 2);
                    if (substringTwo.equals("IA")) {
                        return ParameterOneType.AMPLITUDE;
                    } else if (substringTwo.equals("IV")) {
                        return ParameterOneType.AMPLITUDE;
                    }
                }
            }
        }

        return ParameterOneType.NONE;

    }

    public static ParameterTwoType identifyParameterTwoType(ParameterOneType pOneType) {
        if ((pOneType == ParameterOneType.POLARITY) || (pOneType == ParameterOneType.DURATION)) {
            return ParameterTwoType.BLANK;
        }

        if (pOneType == ParameterOneType.AMPLITUDE) {
            return ParameterTwoType.PERIOD;
        }

        if (pOneType == ParameterOneType.BACK_AZIMUTH) {
            return ParameterTwoType.APPARENT_VELOCITY;
        }

        return ParameterTwoType.NONE;
    }
}
