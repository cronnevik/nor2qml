package no.nnsn.convertercore.interfaces;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.helpers.SfileOverview;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Event;

import java.util.List;

public interface QmlToSfile {
    SfileOverview convertToNordic(List<Event> events, CallerType caller, NordicFormatVersion nordicFormatVersion);
}
