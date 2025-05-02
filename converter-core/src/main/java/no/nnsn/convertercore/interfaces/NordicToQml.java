package no.nnsn.convertercore.interfaces;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.ConverterOptions;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.seisanquakeml.models.sfile.Sfile;

import java.io.InputStream;
import java.util.List;

public interface NordicToQml {
    List<Sfile> readSfile(InputStream is, String filename, CallerType caller) throws Exception;
    EventOverview convertToQuakeml(List<Sfile> sFiles, ConverterOptions options) throws Exception;
}
