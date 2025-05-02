package no.nnsn.quakemlwebservice.service;

import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.seisanquakeml.models.catalog.SfileInformation;
import no.nnsn.seisanquakeml.models.catalog.SfileEvent;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventType;

import java.util.List;

public interface SfileEventService {
    SfileEvent getSfileEventById(String eventID);
    SfileInformation getSfileFromEventId(String eventID);
    List<SfileEvent> getSfileEvents(String starttime,
                                    String endtime,
                                    Double minlatitude,
                                    Double maxlatitude,
                                    Double minlongitude,
                                    Double maxlongitude,
                                    List<EventType> eventTypes,
                                    Double mindepth,
                                    Double maxdepth,
                                    Double minmagnitude,
                                    Double maxmagnitude,
                                    String catalogName,
                                    Integer limit,
                                    OrderByType order);
    List<String> getSfileIds(String starttime,
                             String endtime,
                             Double minlatitude,
                             Double maxlatitude,
                             Double minlongitude,
                             Double maxlongitude,
                             List<EventType> eventTypes,
                             Double mindepth,
                             Double maxdepth,
                             Double minmagnitude,
                             Double maxmagnitude,
                             String catalogName,
                             Integer limit,
                             OrderByType order);

    List<EventType> getUsedEventTypes();
}
