package no.nnsn.convertercore.helpers;

import lombok.Data;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakeml.models.sfile.Sfile;

import java.util.List;

@Data
public class EventOverview {
    private String filename;
    private List<Event> events;
    private List<Sfile> ignoredSfiles;

    public int getEventSize() {
        return this.events.size();
    }

    public EventOverview(List<Event> events, List<Sfile> ignoredSfiles) {
        this.events = events;
        this.ignoredSfiles = ignoredSfiles;
    }
}
