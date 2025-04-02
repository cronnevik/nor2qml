package no.nnsn.convertercore.helpers;

import lombok.Getter;
import no.nnsn.convertercore.exeption.FileReaderException;
import no.nnsn.convertercore.mappers.utils.CharacterChecker;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.SfileVersionLine7;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v2.SfileDataDtoImpl;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SfileLineReader {

    @Getter
    private SfileData data;
    @Getter
    private SfileVersionLine7 versionLine7;
    List<Sfile> result;

    boolean phases;
    int rowNumberSfile;
    int rowNumberTotal;

    Map<Integer, String> l1s, l2s, l3s, l4s, l5s, l6s, lEs, lFs, lHs, lIs, lMs, lSs, unmapped;

    public SfileLineReader() {
        result = new ArrayList<>();
        rowNumberTotal = 0;
    }

    private void initSequenceValues() {
        rowNumberSfile = 0;
        phases = false;
        clearLineLists();
    }

    private void clearLineLists() {
        l1s = new HashMap<>();
        l2s = new HashMap<>();
        l3s = new HashMap<>();
        l4s = new HashMap<>();
        l5s = new HashMap<>();
        l6s = new HashMap<>();
        lEs = new HashMap<>();
        lFs = new HashMap<>();
        lHs = new HashMap<>();
        lIs = new HashMap<>();
        lMs = new HashMap<>();
        lSs = new HashMap<>();
        unmapped = new HashMap<>();
    }

    public List<Sfile> readAndParse(InputStream is, String filename, CallerType caller) throws FileReaderException {
        initSequenceValues();
        String line;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            while ((line = br.readLine()) != null) {
                rowNumberSfile++;
                rowNumberTotal++;

                if (line.length() < 80 && !line.isEmpty()) {
                    continue; // skip empty lines
                } else if (StringUtils.isBlank(line) || line.trim().equals("\n") || line.isEmpty()) {
                    // Multiple events within the same file
                    if (!this.l1s.isEmpty()) {
                        checkMissingDataInstance();
                        parse(filename);
                    } else {
                        // Jump over multiple empty lines
                        continue;
                    }
                } else if (line.charAt(79) == '1') {
                    l1s.put(rowNumberSfile, line);
                } else if (line.charAt(79) == '2') {
                    l2s.put(rowNumberSfile, line);
                } else if (line.charAt(79) == '3') {
                    l3s.put(rowNumberSfile, line);
                } else if ((line.charAt(79) == ' ' || line.charAt(79) == '4') && phases) {
                    if (caller.equals(CallerType.INGESTOR)) {
                        continue;
                    } else {
                        l4s.put(rowNumberSfile, line);
                    }
                } else if (line.charAt(79) == ' ' && !phases) {
                    // Line type 1 without "1" marker or line type 4 without header
                    StringBuilder yearField = new StringBuilder();
                    yearField.append(line.charAt(1));
                    yearField.append(line.charAt(2));
                    yearField.append(line.charAt(3));
                    yearField.append(line.charAt(4));

                    // Check for line type 1 - year value show always be present
                    if (CharacterChecker.onlyNumbers(yearField.toString().trim())) {
                        l1s.put(rowNumberSfile, line);
                    }
                    // Check for line type 4 (meaning type7 header line missing)
                    else if (CharacterChecker.onlyAlphabetic(yearField.toString().trim())) {
                        this.phases = true;

                        if (caller.equals(CallerType.STANDALONE)) {
                            System.out.println("Missing Line 7: " + filename);
                        }

                        // Check for new or old phase format
                        // New format has an empty string between instrument and component
                        char component = line.charAt(7);
                        if (component == ' ') {
                            versionLine7 = SfileVersionLine7.VERSION2;
                            this.data = new SfileDataDtoImpl();
                        } else {
                            versionLine7 = SfileVersionLine7.VERSION1;
                            this.data = new SfileDataImpl();
                        }


                        // If ingestor is calling this function, l4 does not need to be read
                        if (caller.equals(CallerType.INGESTOR)) {
                            continue;
                        } else {
                            l4s.put(rowNumberSfile, line);
                        }
                    } else {
                        if (caller.equals(CallerType.INGESTOR)) {
                            System.out.println("No Line 7 nor Line 4");
                        }
                        versionLine7 = SfileVersionLine7.VERSION1;
                        this.data = new SfileDataImpl();
                    }
                } else if (line.charAt(79) == '5') {
                    l5s.put(rowNumberSfile, line);
                } else if (line.charAt(79) == '6') {
                    l6s.put(rowNumberSfile, line);
                } else if (line.charAt(79) == '7') {
                    phases = true;
                    if (line.equals(SfileVersionLine7.VERSION1.toString())) {
                        versionLine7 = SfileVersionLine7.VERSION1;
                        this.data = new SfileDataImpl();
                    } else if (line.equals(SfileVersionLine7.VERSION1_OLD.toString())) {
                        versionLine7 = SfileVersionLine7.VERSION1_OLD;
                        this.data = new SfileDataImpl();
                    } else if (line.equals(SfileVersionLine7.VERSION2.toString())) {
                        versionLine7 = SfileVersionLine7.VERSION2;
                        this.data = new SfileDataDtoImpl();
                    } else {
                        if (caller.equals(CallerType.STANDALONE)) {
                            System.out.println("cannot determine sfile version on file: " + filename);
                        }
                    }
                } else if (line.charAt(79) == 'E') {
                    lEs.put(rowNumberSfile, line);
                } else if (line.charAt(79) == 'F') {
                    lFs.put(rowNumberSfile, line);
                } else if (line.charAt(79) == 'H') {
                    lHs.put(rowNumberSfile, line);
                } else if (line.charAt(79) == 'I') {
                    lIs.put(rowNumberSfile, line);
                } else if (line.charAt(79) == 'M') {
                   lMs.put(rowNumberSfile, line);
                } else if (line.charAt(79) == 'S') {
                    lSs.put(rowNumberSfile, line);
                } else {
                    unmapped.put(rowNumberSfile, line);
                }
            }
            br.close();

            checkMissingDataInstance();

        } catch (Exception e) {
            if (caller.equals(CallerType.STANDALONE)) {
                System.out.println("Cannot parse sfile: " + filename);
            } else {
                throw new FileReaderException("Cannot parse sfile");
            }

        }

        // Parse if last line was not an empty line
        if (!this.l1s.isEmpty()) {
            parse(filename);
        }

        return this.result;
    }

    private void checkMissingDataInstance() {
        if (this.data == null) {
            versionLine7 = SfileVersionLine7.VERSION1;
            this.data = new SfileDataImpl();
        }
    }

    private void parse(String filename) {
        // Line 1s
        for (Map.Entry<Integer, String> entry : l1s.entrySet()) {
            parseLine1(entry.getValue(), entry.getKey());
        }
        // Line 2s
        for (Map.Entry<Integer, String> entry : l2s.entrySet()) {
            parseLine2(entry.getValue(), entry.getKey());
        }
        // Line 3s
        for (Map.Entry<Integer, String> entry : l3s.entrySet()) {
            parseLine3(entry.getValue(), entry.getKey());
        }
        // Line 4s
        for (Map.Entry<Integer, String> entry : l4s.entrySet()) {
            parseLine4(entry.getValue(), entry.getKey());
        }
        // Line 5s
        for (Map.Entry<Integer, String> entry : l5s.entrySet()) {
            parseLine5(entry.getValue(), entry.getKey());
        }
        // Line 6s
        for (Map.Entry<Integer, String> entry : l6s.entrySet()) {
            parseLine6(entry.getValue(), entry.getKey());
        }
        // Line Es
        for (Map.Entry<Integer, String> entry : lEs.entrySet()) {
            parseLineE(entry.getValue(), entry.getKey());
        }
        // Line Fs
        for (Map.Entry<Integer, String> entry : lFs.entrySet()) {
            parseLineF(entry.getValue(), entry.getKey());
        }
        // Line Hs
        for (Map.Entry<Integer, String> entry : lHs.entrySet()) {
            parseLineH(entry.getValue(), entry.getKey());
        }
        // Line Is
        for (Map.Entry<Integer, String> entry : lIs.entrySet()) {
            parseLineI(entry.getValue(), entry.getKey());
        }
        // Line Ms
        for (Map.Entry<Integer, String> entry : lMs.entrySet()) {
            parseLineM(entry.getValue(), entry.getKey());
        }
        // Line Ss
        for (Map.Entry<Integer, String> entry : lSs.entrySet()) {
            parseLineS(entry.getValue(), entry.getKey());
        }
        // Unmapped lines
        for (String line : unmapped.values()) {
            this.data.addUnmapped(line);
        }

        attachLinesToSfile(filename);
    }


    private void attachLinesToSfile(String filename) {
        Sfile sfile = new Sfile();
        sfile.setFilename(filename);
        sfile.setData(this.getData());
        sfile.setVersion(this.versionLine7);
        this.result.add(sfile);
        initSequenceValues();
    }

    private void parseLine1(String line, int rowNumber) {
        Line1 l1 = new Line1();
        l1.setLineInfo(line, rowNumber);
        l1.setValues();
        this.data.addLine1(l1);
    }

    private void parseLine2(String line, int rowNumber) {
        Line2 l2 = new Line2();
        l2.setLineInfo(line, rowNumber);
        l2.setValues();
        this.data.addLine2(l2);
    }

    private void parseLine3(String line, int rowNumber) {
        Line3 l3 = new Line3();
        l3.setLineInfo(line, rowNumber);
        l3.setValues();
        this.data.addLine3(l3);
    }

    private void parseLine4(String line, int roNumber) {
        if (data instanceof SfileDataImpl) {
            Line4 l4 = new Line4();
            l4.setLineInfo(line, roNumber);
            l4.setValues();
            this.data.addLine4(l4);
        } else if (data instanceof SfileDataDtoImpl) {
            Line4Dto l4 = new Line4Dto();
            l4.setLineInfo(line, roNumber);
            l4.setValues();
            this.data.addLine4(l4);
        }

    }

    private void parseLine5(String line, int rowNumber) {
        Line5 l5 = new Line5();
        l5.setLineInfo(line, rowNumber);
        l5.setValues();
        this.data.addLine5(l5);
    }

    private void parseLine6(String line, int rowNumber) {
        Line6 l6 = new Line6();
        l6.setLineInfo(line, rowNumber);
        l6.setValues();
        this.data.addLine6(l6);
    }

    private void parseLineE(String line, int rowNumber) {
        LineE lE = new LineE();
        lE.setLineInfo(line, rowNumber);
        lE.setValues();
        this.data.addLineE(lE);
    }

    private void parseLineF(String line, int rowNumber) {
        LineF lF = new LineF();
        lF.setLineInfo(line, rowNumber);
        lF.setValues();

        List<Line1> l1s = this.data.getLine1s();
        if ( l1s != null && l1s.size() > 0) {
            // Related Line1 (origin) is always the first line type 1 in each event
            Line1 relatedLine1 = l1s.get(0);
            lF.setRelatedLine1(relatedLine1);
        }

        this.data.addLineF(lF);
    }

    private void parseLineH(String line, int rowNumber) {
        LineH lH = new LineH();
        lH.setLineInfo(line, rowNumber);
        lH.setValues();
        this.data.addLineH(lH);
    }

    private void parseLineI(String line, int rowNumber) {
        LineI lI = new LineI();
        lI.setLineInfo(line, rowNumber);
        lI.setValues();
        this.data.addLineI(lI);
    }

    private void parseLineM(String line, int rowNumber) {
        StringBuilder sb = new StringBuilder();
        sb.append(line.charAt(1));
        if (!sb.toString().matches("-?\\d+(\\.\\d+)?") ) {
            LineM2 lineM2 = new LineM2();
            lineM2.setLineInfo(line, rowNumber);
            lineM2.setValues();

            // Set relation for line F
            List<LineF> lfs = this.data.getLineFs();
            if (lfs != null && lfs.size() > 0) {
                String lineM2Method = lineM2.getMethodUsed();
                String lineM2Agency = lineM2.getReportingAgency();
                for (LineF lf: lfs) {
                    String lineFMethod = lf.getProgramUsed();
                    String lineFAgency = lf.getAgencyCode();

                    // Moment tensor is only connected to focalmechanism if method and agency matches
                    if (lineM2Method.equals(lineFMethod) && lineM2Agency.equals(lineFAgency)) {
                        lineM2.setRelatedLineF(lf);
                    }
                }
            }

            // Set relation for Line M1 - Previous line
            List<LineM1> lm1s = this.data.getLineM1s();
            if (lm1s != null && lm1s.size() > 0) {
                lineM2.setRelatedLineM1(lm1s.get(lm1s.size() - 1));
            }

            this.data.addLineM2(lineM2);
        } else {
            LineM1 lineM1 = new LineM1();
            lineM1.setLineInfo(line, rowNumber);
            lineM1.setValues();

            // Set relation for line F
            String lineM1Method = lineM1.getMethodUsed();
            String lineM1Agency = lineM1.getReportingAgency();

            List<LineF> lfs = this.data.getLineFs();
            if (lfs != null) {
                if (lfs.size() > 0) {
                    for (LineF lf: lfs) {
                        String lineFMethod = lf.getProgramUsed();
                        String lineFAgency = lf.getAgencyCode();

                        // Moment tensor is only connected to focalmechanism if method and agency matches
                        if (lineM1Method.equals(lineFMethod) && lineM1Agency.equals(lineFAgency)) {
                            lineM1.setRelatedLineF(lf);
                        }
                    }
                    this.data.addLineM1(lineM1);
                }
            }

        }
    }

    private void parseLineS(String line, int rowNumber) {
        LineS lS = new LineS();
        lS.setLineInfo(line, rowNumber);
        this.data.addLineS(lS);
    }
}
