package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.sfile.Sfile;
import no.nnsn.seisanquakeml.models.sfile.SfileData;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class FileReaderTests {
    @Autowired
    NordicMapper nordicMapper;

    @Autowired
    QmlMapper qmlMapper;

    @Autowired
    NordicToQml nordicToQml;

    @Test
    public void testResourcesFolders() {
        String sFilePath = "src/test/resources/sfile";
        File sFiles = new File(sFilePath);
        String absoluteSfilesPath = sFiles.getAbsolutePath();
        System.out.println(absoluteSfilesPath);
        assertTrue(absoluteSfilesPath.endsWith("sfile"));

        String qmlPath = "src/test/resources/qml";
        File qmls = new File(qmlPath);
        String absoluteQmlsPath = qmls.getAbsolutePath();
        System.out.println(absoluteQmlsPath);
        assertTrue(absoluteQmlsPath.endsWith("qml"));
    }

    @Test
    public void readLinesFromSingleSfile() {
        String path = "src/test/resources/sfile/07-1645-19D.S200805";

        File sFile = new File(path);
        List<Sfile> sfiles = null;
        SfileData sf = null;
        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            Sfile parsedSfile = sfiles.get(0);
            sf = parsedSfile.getData();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Sfile sfile = sfiles.get(0);

        assertEquals(1, sfiles.size());
        assertEquals("07-1645-19D.S200805", sFile.getName());

        // Check if all line types have been read and mapped to their respective objects
        assert sf != null;
        assertEquals(3, sf.getLine1s().size());
        assertEquals(1, sf.getLineEs().size());
        assertEquals(1, sf.getLineIs().size());
        assertEquals(1, sf.getLine3s().size());
        assertEquals(25, sf.getLine4s().size());
        assertEquals(9, sf.getLine6s().size());
    }

    @Test
    public void readMultipleEventsFromSfile() {
        String path = "src/test/resources/sfile/twoevents.out";

        File sFile = new File(path);
        List<Sfile> sfiles = null;
        SfileData sf = null;
        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            if (!sfiles.isEmpty()) {
                assert true;
            } else {
                assert false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert true;
    }

    @Test
    public void readNordicVersionTwoFormatFile() {
        String path = "src/test/resources/sfile/nyformat.out";

        File sFile = new File(path);
        List<Sfile> sfiles = null;
        SfileData sf = null;
        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            if (!sfiles.isEmpty()) {
                assert true;
            } else {
                assert false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assert true;
    }

}
