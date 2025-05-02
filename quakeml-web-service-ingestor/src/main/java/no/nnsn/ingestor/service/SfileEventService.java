package no.nnsn.ingestor.service;

import no.nnsn.ingestor.dao.SfileCheckInfo;
import no.nnsn.seisanquakeml.models.catalog.SfileEvent;

import java.util.List;

public interface SfileEventService {
    void addOrUpdateEvent(SfileEvent event);
    void addEvents(List<SfileEvent> events);
    SfileEvent getEventByID(String eventID);
    List<SfileEvent> getAllEvents();
    void deleteEventByID(String eventID);
    List<SfileCheckInfo> getSfileListByCatalogName(String name);
}
