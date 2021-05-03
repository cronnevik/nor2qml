package no.nnsn.quakemlwebserviceingestorexecutable.components;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Component
public class Converter {

    final NordicToQml nordicToQml;
    final Arguments arguments;

    @Autowired
    public Converter(NordicToQml nordicToQml, Arguments arguments) {
        this.nordicToQml = nordicToQml;
        this.arguments = arguments;
    }

    public EventOverview getQmlEventsFromNordic(InputStream stream, String path) {
        List<Sfile> sFileEvents = nordicToQml.readSfile(stream, path, CallerType.INGESTOR);
        EventOverview eventOverview = null;

        // Get filename to provide id string
        String fileName = "";
        String id = "";
        try {
            Path p = Paths.get(path);
            Path fileNamePath = p.getFileName();
            fileName = fileNamePath.toString();
        } catch (Exception ex) {
            System.out.println("Error in reading path: " + ex.getLocalizedMessage());
        }

        try {
            String[] split = fileName.split("\\."); // First part of filename string
            id = split[0];
        } catch (Exception ex) {
            System.out.println("Error in split of filename: " + fileName + " .Error message: " + ex.getLocalizedMessage());
        }

        try {

            eventOverview = nordicToQml.getEvents(sFileEvents, null, CallerType.INGESTOR, arguments.getProfile(), id);
            eventOverview.setFilename(path);
        } catch (Exception ex) {
            //throw new CustomException(ex.getMessage());
            System.out.println("Error in converting file: " + path + " " + ex.getMessage());
        }

        return eventOverview;
    }

}
