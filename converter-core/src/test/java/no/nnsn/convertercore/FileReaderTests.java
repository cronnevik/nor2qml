package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
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
        }

        Sfile sfile = sfiles.get(0);

        assertTrue(sfiles.size() == 1);
        assertTrue(sFile.getName().equals("07-1645-19D.S200805"));

        // Check if all line types have been read and mapped to their respective objects
        assertTrue(sf.getLine1s().size() == 3);
        assertTrue(sf.getLineEs().size() == 1);
        assertTrue(sf.getLineIs().size() == 1);
        assertTrue(sf.getLine3s().size() == 1);
        assertTrue(sf.getLine4s().size() == 25);
        assertTrue(sf.getLine6s().size() == 9);
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
            if (sfiles.size() > 0) {
                assert true;
            } else {
                assert false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
            if (sfiles.size() > 0) {
                assert true;
            } else {
                assert false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert true;
    }

}
