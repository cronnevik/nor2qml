package no.nnsn.converterwebserver.controllers;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.helpers.SfileOverview;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.converterwebserver.jpa.ConvertOptions;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.QuakemlContent;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.utils.QuakemlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
)
@RestController
public class QuakemlController {

    final QmlToSfile qmlToSfile;

    @Autowired
    public QuakemlController(QmlToSfile qmlToSfile) {
        this.qmlToSfile = qmlToSfile;
    }

    @RequestMapping(value = "/read-qml-service", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String readQmlService(@RequestBody ConvertOptions options) throws URISyntaxException {
        InputStream stream = QuakemlUtils.getQuakemlStreamFromWebService(options.getData().getUrl());
        QuakemlContent content = QuakemlUtils.getQuakemlContent(stream);
        SfileOverview sfileOverview = qmlToSfile.convertToSfiles(
                QuakemlUtils.getEventsFromQuakemlString(content), CallerType.WEBSERVER, getNordicVersion(options.getVersion())
        );
        return sfileOverview.getSfiletext();
    }

    @RequestMapping(value = "/read-qml-rawtext", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String readQmlRawText(@RequestBody String rawText) {
        InputStream stream = QuakemlUtils.getQuakemlStreamFromRawText(rawText);
        QuakemlContent content = QuakemlUtils.getQuakemlContent(stream);
        SfileOverview sfileOverview = qmlToSfile.convertToSfiles(QuakemlUtils.getEventsFromQuakemlString(content), CallerType.WEBSERVER, getNordicVersion(""));
        return sfileOverview.getSfiletext();
    }

    @RequestMapping(value = "/read-xml-file", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String readSfile(@RequestParam("file") MultipartFile file, @RequestHeader("nordic-version") String version) throws IOException {

        if (file.isEmpty()) {
            return null;
        }

        InputStream stream = file.getInputStream();
        QuakemlContent content = QuakemlUtils.getQuakemlContent(stream);
        SfileOverview sfileOverview = qmlToSfile.convertToSfiles(QuakemlUtils.getEventsFromQuakemlString(content), CallerType.WEBSERVER, getNordicVersion(version));
        return sfileOverview.getSfiletext();
    }

    private NordicFormatVersion getNordicVersion(String version) {
        if (version.equals("nordic1")) {
            return NordicFormatVersion.VERSION1;
        } else if (version.equals("nordic2")) {
            return NordicFormatVersion.VERSION2;
        }
        // Default version1
        return NordicFormatVersion.VERSION1;
    }
}
