package no.nnsn.convertercore.interfaces;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.ConverterOptions;
import no.nnsn.convertercore.helpers.ConverterProfile;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;

import java.io.InputStream;
import java.util.List;

public interface NordicToQml {
    public List<Sfile> readSfile(InputStream is, String filename, CallerType caller);
    public EventOverview convertToQuakeml(
            List<Sfile> sFiles, ConverterOptions options
    );
}
