package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.TimeQualifiers;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Helper class time conversion when relying on multiple sources.
 *
 * @author Christian Rønnevik
 */
public class TimeHelper {
    @TimeQualifiers.Line1ToIsoTime
    public String mapLine1ToIsoTime(Line1 line1){

        // year, month and day cannot be 0
        int year = line1.getYear() != null && !line1.getYear().isEmpty() ? Integer.parseInt(line1.getYear()) : 1111;
        int month = line1.getMonth() != null && !line1.getMonth().isEmpty() ? Integer.parseInt(line1.getMonth()) : 1;
        int day = line1.getDay() != null && !line1.getDay().isEmpty() && !line1.getDay().equals("0") ? Integer.parseInt(line1.getDay()) : 1;
        int hour = line1.getHour() != null && !line1.getHour().isEmpty() ? Integer.parseInt(line1.getHour()) : 0;
        int minute = line1.getMinutes() != null && !line1.getMinutes().isEmpty() ? Integer.parseInt(line1.getMinutes()) : 0;
        int second = 0;
        int nanoSec = 0;

        String secTemp = line1.getSeconds();
        if (secTemp != null && !secTemp.isEmpty()) {
            Double dSec = Double.parseDouble(secTemp);
            second = dSec.intValue();

            String[] secSplit = String.valueOf(dSec).split("\\.");
            if (secSplit.length == 2) {
                NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                DecimalFormat df = (DecimalFormat)nf;
                df.applyPattern("0.00");

                double[] secNums = new double[2];
                secNums[0] = Double.parseDouble(secSplit[0]);
                secNums[1] = Double.parseDouble("0." + secSplit[1]);

                //Check for Seisan 60 seconds bug
                if (secNums[0] == 60 || secNums[0] == 60.0) {
                    secNums[0] = 0.0;
                    second = 0;
                    nanoSec = 0;
                    return LocalDateTime.of(year, month, day, hour, minute, second, nanoSec).plusMinutes(1).format(DateTimeFormatter.ISO_DATE_TIME);
                }

                double nanoSecTemp = (secNums[1]) * Math.pow(10,9);
                nanoSec = (int) nanoSecTemp;


            }
        }

        return LocalDateTime.of(year, month, day, hour, minute, second, nanoSec).format(DateTimeFormatter.ISO_DATE_TIME);
    }

}
