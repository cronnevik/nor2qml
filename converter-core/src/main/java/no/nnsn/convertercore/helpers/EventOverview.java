package no.nnsn.convertercore.helpers;

import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;

import java.util.List;

@Data
public class EventOverview {
    private String filename;
    private List<Event> events;
    private List<IgnoredLineError> errors;

    public int getEventSize() {
        return this.events.size();
    }

    public EventOverview(List<Event> events, List<IgnoredLineError> errors) {
        this.events = events;
        this.errors = errors;
    }
}
