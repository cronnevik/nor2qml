package no.nnsn.quakemlwebserviceingestorexecutable.service;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;

import java.util.List;

public interface SfileEventService {
    void addOrUpdateEvent(SfileEvent event);
    void addEvents(List<SfileEvent> events);
    SfileEvent getEventByID(String eventID);
    List<SfileEvent> getAllEvents();
    void deleteEventByID(String eventID);
    List<SfileCheck> getSfileListByCatalogName(String name);
}
