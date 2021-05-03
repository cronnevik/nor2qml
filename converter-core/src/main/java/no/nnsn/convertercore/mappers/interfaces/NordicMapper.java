package no.nnsn.convertercore.mappers.interfaces;

import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;

import java.util.List;

public interface NordicMapper {
    List<Object> mapLine1s(Event event);
    List<Object> mapLine3s(Event event);
    List<Object> mapLine4s(NordicFormatVersion format, List<Pick> picks, List<Amplitude> amplitudes, List<Origin> origins);
    List<Object> mapLine6s(Event event);
    List<Object> mapLineEs(List<Origin> origins);
    List<Object> mapLineFs(List<FocalMechanism> focalMechanisms);
    List<Object> mapLineIs(Event event);
    List<Object> mapLineM1s(List<Origin> origins, List<FocalMechanism> focalMechanisms, List<Magnitude> magnitudes);
    List<Object> mapLineM2s(List<FocalMechanism> focalMechanisms);
    List<Object> mapLineS(Event event);
}
