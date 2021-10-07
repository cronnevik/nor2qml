package no.nnsn.quakemlwebserviceingestorexecutable.components;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.ConverterOptions;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
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
        String id = "";
        try {
            Path p = Paths.get(path);
            Path fileNamePath = p.getFileName();
            id = fileNamePath.toString();
        } catch (Exception ex) {
            System.out.println("Error in reading path: " + ex.getLocalizedMessage());
        }

        try {
            ConverterOptions options = new ConverterOptions(null, CallerType.INGESTOR, arguments.getProfile(), id);
            eventOverview = nordicToQml.convertToQuakeml(sFileEvents, options);
            eventOverview.setFilename(path);
        } catch (Exception ex) {
            //throw new CustomException(ex.getMessage());
            System.out.println("Error in converting file: " + path + " " + ex.getMessage());
        }

        return eventOverview;
    }

}
