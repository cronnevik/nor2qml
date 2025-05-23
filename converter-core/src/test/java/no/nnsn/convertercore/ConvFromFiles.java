package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.sfile.Sfile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class ConvFromFiles {
    @Autowired
    NordicMapper nordicMapper;

    @Autowired
    QmlMapper qmlMapper;

    @Autowired
    NordicToQml nordicToQml;

    @Autowired
    QmlToSfile qmlToSfile;

    @Test
    public void readFileAndCOnv() {
        String path = "src/test/resources/sfile/14-0821-44L.S200806";

        File sFile = new File(path);
        List<Sfile> sfiles;

        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);

            System.out.println("Event size: " + events.getEventSize());
            System.out.println("Number of Picks: " + events.getEvents().get(0).getPick().size());

            qmlToSfile.convertToNordic(events.getEvents(), CallerType.CONVERTER, NordicFormatVersion.VERSION1);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assert true;
    }

    @Test
    public void readNewNordicFormat() {
        String path = "src/test/resources/sfile/nyformat.out";

        File sFile = new File(path);
        List<Sfile> sfiles;

        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);

            System.out.println("Event size: " + events.getEventSize());
            System.out.println("Number of Picks: " + events.getEvents().get(0).getPick().size());
            System.out.println("Number of Amplitudes: " + events.getEvents().get(0).getAmplitude().size());
            System.out.println("Number of Arrivals: " + events.getEvents().get(0).getOrigin().get(0).getArrival().size());

            /*
            System.out.println("Number of Errors: " + events.getErrors().size());
            for (IgnoredLineError error: events.getErrors()) {
                System.out.println(error.getLine().getLineText());
                System.out.println(error.getMessage());
            }
             */

            SfileOverview sfileOverview = qmlToSfile.convertToNordic(events.getEvents(), CallerType.CONVERTER, NordicFormatVersion.VERSION2);
            // System.out.println("---------------------------------");
            // System.out.println(sfileOverview.getSfiletext());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assert true;
    }

    @Test
    public void readFileWithLine1StringError() {
        String path = "src/test/resources/sfile/06-1356-43L.S201812";

        File sFile = new File(path);
        List<Sfile> sfiles;
        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);

            System.out.println("Event size: " + events.getEventSize());
            /*
            System.out.println("Number of errors: " + events.getErrors().size());
             */
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert true;
    }

    @Test
    public void convertSpectralLines() {
        System.out.println("Spectral lines");
        String path = "src/test/resources/sfile/spectral-lines.out";
        File sFile = new File(path);
        List<Sfile> sfiles;
        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);

            System.out.println("Event size: " + events.getEventSize());
            /*
            System.out.println("Number of errors: " + events.getErrors().size());

             */

            SfileOverview sfileOverview = qmlToSfile.convertToNordic(events.getEvents(), CallerType.CONVERTER, NordicFormatVersion.VERSION2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert true;
    }

}
