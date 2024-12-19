package no.nnsn.seisanquakeml.converterstandalone;

import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.errors.IgnoredQmlError;
import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.QuakemlContent;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.utils.QuakemlUtils;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class Converter {

    final Arguments arguments;
    final QmlToSfile qmlToSfile;
    final NordicToQml nordicToQml;

    @Autowired
    public Converter(Arguments arguments, QmlToSfile qmlToSfile, NordicToQml nordicToQml) {
        this.arguments = arguments;
        this.qmlToSfile = qmlToSfile;
        this.nordicToQml = nordicToQml;
    }

    public void convert() {
        String data = null;
        String output = "";
        String path = null;

        if (arguments.isToQuakeml()) {
            data = convertToQml();
            output = arguments.hasOutput() ? arguments.getOutput() : "quakeml.xml";
            path = getPath(output, "quakeml.xml");
        } else if(arguments.isToSfile()) {
            data = convertToSfile();
            output = arguments.hasOutput() ? arguments.getOutput() : "sfile.out";
            path = getPath(output, "sfile.out");
        }

        if (data != null) {
            System.out.println("creating file...");
            if (path != null) {
                try {
                    Path filePath = new File(path).toPath();
                    int num = 0;
                    while(true) {
                        if (Files.exists(filePath)) {
                            filePath = new File(path + "(" + num + ")").toPath();
                            num++;
                        } else {
                            break;
                        }
                    }
                    FileUtils.writeStringToFile(new File(path), data, StandardCharsets.UTF_8);
                    System.out.println("file created at path: " + path);
                } catch (IOException e) {
                    System.out.println("Something went wrong in creating the file:");
                    e.getMessage();
                }
            } else {
                System.out.println("## Invalid output path. Please try another output. ##");
            }

        }

    }

    private String getPath(String output, String genFilename) {
        // Check if output is path to directory or file
        Path filenamePath = new File(output).toPath();
        if (Files.isDirectory(filenamePath)) {
            return output + File.separator + genFilename;
        } else if (output.equals(genFilename)) {
            return arguments.getCurrentPath() + File.separator + output;
        } else if (Files.exists(filenamePath)) {
            System.out.println("Filename specified already exists and will be overwritten");
            return output;
        }else if (!Files.exists(filenamePath)) {
            return output;
        } else {
            return null;
        }
    }

    public String convertToQml() {
        String version;
        if (arguments.isQuakemlV12()) {
            version = "v12";
        } else {
            version = "v20"; // default
        }

        String prefix = arguments.getQmlPrefix();
        String agencyID = arguments.getQmlAgency();

        IdGenerator idGenerator = IdGenerator.getInstance();
        idGenerator.setPrefix(prefix);
        idGenerator.setAuthorityID(agencyID);

        String path = null;
        final AtomicReference<String> qmlString = new AtomicReference<>();
        path = arguments.hasInput() ? arguments.getInput() : arguments.getCurrentPath();
        FileInfo fileInfo = null;
        try {
            fileInfo = new FileInfo(path, "sfile", null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Sfile> sFileEvents = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();
        Set<Path> filePaths = fileInfo.getFilePaths();
        filePaths.forEach(p -> {
            InputStream stream = null;
            try {
                stream = new FileInputStream(p.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sFileEvents.addAll(nordicToQml.readSfile(stream, p.toString(), CallerType.STANDALONE));
        });

        EventOverview eventOverview = null;
        try {
            ConverterOptions options = new ConverterOptions(
                    null,
                    CallerType.STANDALONE,
                    null,
                    "",
                    arguments.getBlankEventType(),
                    arguments.getBlankEventCertainty()
            );
            eventOverview = nordicToQml.convertToQuakeml(sFileEvents, options);
            System.out.println("Number of events: " + eventOverview.getEventSize());
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
        qmlString.set(QuakemlUtils.generateQuakemlStringFromSfiles(
                eventOverview.getEvents(),
                prefix,
                agencyID,
                version
        ));
        if (eventOverview.getErrors() != null && eventOverview.getErrors().size() > 0) {
            errors.addAll(eventOverview.getErrors());
        }

        if (errors.size() > 0) {
            System.out.println("-------------------------------------");
            System.out.println("Errors found in file and are ignored:");
            errors.forEach(er -> {
                System.out.println(
                        "File: " + er.getFilename() +
                        " | Row: " + er.getRowNumber() +
                        " | Error: " + er.getMessage() +
                        " | LineText:" + er.getLine().getLineText()
                );
            });
            System.out.println("-------------------------------------");
        } else {
            System.out.println("No errors found");
        }
        return qmlString.get();
    }

    public String convertToSfile() {

        List<IgnoredQmlError> errors = new ArrayList<>();

        NordicFormatVersion nordicFormatVersion = null;
        if (arguments.isNordic1()) {
            nordicFormatVersion = NordicFormatVersion.VERSION1;
        } else if (arguments.isNordic2()) {
            nordicFormatVersion = NordicFormatVersion.VERSION2;
        } else {
            // Default value
            nordicFormatVersion = NordicFormatVersion.VERSION1;
        }

        if (arguments.isQuakemlSourceFile()) {
            String path = null;
            path = arguments.hasInput() ? arguments.getInput() : arguments.getCurrentPath();

            FileInfo fileInfo = null;
            try {
                fileInfo = new FileInfo(path, "qml", null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // create nordic format version as final due to arrow function for filePaths
            final NordicFormatVersion nordicFormVersion = nordicFormatVersion;
            final List<String> sFiles = new ArrayList<>(); // List of converted events
            Set<Path> filePaths = fileInfo.getFilePaths();
            filePaths.forEach(p -> {
                InputStream stream = null;
                try {
                    stream = new FileInputStream(p.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                QuakemlContent content = QuakemlUtils.getQuakemlContent(stream);
                SfileOverview sfileOverview =
                        qmlToSfile.convertToNordic(QuakemlUtils.getEventsFromQuakemlString(content), CallerType.STANDALONE, nordicFormVersion);
                this.printQmlErrors(sfileOverview.getErrors());
                sFiles.add(sfileOverview.getSfiletext());

            });
            String response = "";
            for (String sFile: sFiles) {
                response += sFile;
            }
            return response;

        } else if (arguments.isQuakemlSourceWeb()) {
            String webServiceUrl = arguments.getQmlUrl();
            try {
                InputStream stream = QuakemlUtils.getQuakemlStreamFromWebService(webServiceUrl);
                QuakemlContent content = QuakemlUtils.getQuakemlContent(stream);

                SfileOverview sfileOverview =
                        qmlToSfile.convertToNordic(QuakemlUtils.getEventsFromQuakemlString(content), CallerType.STANDALONE, nordicFormatVersion);
                this.printQmlErrors(sfileOverview.getErrors());
                return sfileOverview.getSfiletext();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void printQmlErrors(List<IgnoredQmlError> errors) {
        if (errors.size() > 0) {
            System.out.println("Errors found during conversion:");
            errors.forEach(er -> {
                System.out.println(
                        "eventID: " + er.getEventPublicID() +
                                " | Entity: " + er.getEntity() +
                                " | Error: " + er.getMessage()
                );
            });
        }
    }
}
