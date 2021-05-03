package no.nnsn.quakemlwebservice.service;

import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public interface SfileEventService {
    SfileEvent getSfileEventById(String eventID);
    SfileCheck getSfileFromEventId(String eventID);
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
