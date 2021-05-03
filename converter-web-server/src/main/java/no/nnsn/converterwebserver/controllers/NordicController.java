package no.nnsn.converterwebserver.controllers;

import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.errors.SfileErrorResponse;
import no.nnsn.convertercore.errors.SimpleErrorResponse;
import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.mappers.utils.RandomString;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.EventData;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.SfileContext;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.SfileOptions;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.utils.QuakemlUtils;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.List;

@CrossOrigin(
        origins = "http://localhost:4200",
        allowCredentials = "true",
        allowedHeaders = "*",
        methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT}
)
@RestController
public class NordicController {
    final NordicToQml nordicToQml;

    @Autowired
    public NordicController(NordicToQml nordicToQml) {
        this.nordicToQml = nordicToQml;
    }

    @ExceptionHandler(CustomException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleErrorResponse handle(CustomException e) {
        return new SimpleErrorResponse(e.getMessage());
    }

    @RequestMapping(value = "/read-sfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sfile> readSfile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        InputStream input = null;

        try {
            input = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nordicToQml.readSfile(input, file.getOriginalFilename(), CallerType.WEBSERVER);
    }

    @RequestMapping(value = "/generate-xml", method = RequestMethod.POST)
    public ResponseEntity<Object> generateXml(@RequestBody SfileContext dataMap) throws IOException {

        List<Sfile> sfiles = dataMap.getSfiles();
        SfileOptions sfileOptions = dataMap.getOptions();

        String agencyID = sfileOptions.getId().get("agencyID");
        if (StringUtils.isBlank(agencyID)) {
            agencyID = "org.example";
        }
        String prefix = sfileOptions.getId().get("prefix");
        if (StringUtils.isBlank(prefix)) {
            prefix = "quakeml";
        }
        RandomString genString = new RandomString(12, new SecureRandom());
        EventOverview eventOverview = nordicToQml.getEvents(sfiles, sfileOptions.getErrorHandling(), CallerType.WEBSERVER, null, genString.nextString());

        String errorHandling = sfileOptions.getErrorHandling();
        if (!errorHandling.equals("ignore")) {
            if (eventOverview.getErrors().size() > 0) {
                final List<IgnoredLineError> errors = eventOverview.getErrors();
                return new ResponseEntity<>(new SfileErrorResponse(errors), new HttpHeaders(), HttpStatus.valueOf(500));
            }
        }

        String qmlString = QuakemlUtils.generateQuakemlStringFromSfiles(
                eventOverview.getEvents(),
                prefix,
                agencyID,
                sfileOptions.getVersion()
        );
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(new EventData(qmlString, eventOverview.getErrors()), headers, HttpStatus.OK);
    }
}
