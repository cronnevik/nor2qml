package no.nnsn.quakemlwebservice.service;

import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.quakemlwebservice.repository.SfileEventRepository;
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SfileEventServiceImpl implements SfileEventService {

    final
    SfileEventRepository repo;

    public SfileEventServiceImpl(SfileEventRepository repo) {
        this.repo = repo;
    }

    @Override
    public SfileEvent getSfileEventById(String eventID) {
        return repo.findById(eventID).get();
    }

    @Override
    public SfileInformation getSfileFromEventId(String eventID) {
        return repo.findSfileByEventId(eventID);
    }

    @Override
    public List<SfileEvent> getSfileEvents(
            String starttime, String endtime,
            Double minlatitude, Double maxlatitude,
            Double minlongitude, Double maxlongitude,
            List<EventType> eventTypes,
            Double mindepth, Double maxdepth,
            Double minmagnitude, Double maxmagnitude,
            String catalogName,
            Integer limit,
            OrderByType order) {

        Sort sorting = Sort.by("time").descending();

        switch (order) {
            case TIME:
                sorting = Sort.by("time").descending();
                break;
            case TIME_ASC:
                sorting = Sort.by("time").ascending();
                break;
            case MAGNITUDE:
                sorting = Sort.by("magnitude").descending();
                break;
            case MAGNITUDE_ASC:
                sorting = Sort.by("magnitude").ascending();
                break;
        }
        Pageable pageable = PageRequest.of(0, limit, sorting);
        return repo.findAllSfileEvents(
                starttime, endtime,
                minlatitude, maxlatitude, minlongitude, maxlongitude,
                eventTypes,
                mindepth, maxdepth,
                minmagnitude, maxmagnitude,
                catalogName,
                pageable).getContent();
    }

    @Override
    public List<String> getSfileIds(String starttime,
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
                                        OrderByType order) {

        Sort sorting = Sort.by("time").descending();

        switch (order) {
            case TIME:
                sorting = Sort.by("time").descending();
                break;
            case TIME_ASC:
                sorting = Sort.by("time").ascending();
                break;
            case MAGNITUDE:
                sorting = Sort.by("magnitude").descending();
                break;
            case MAGNITUDE_ASC:
                sorting = Sort.by("magnitude").ascending();
                break;
        }
        Pageable pageable = PageRequest.of(0, limit, sorting);
        return repo.findSfileIds(
                starttime, endtime,
                minlatitude, maxlatitude, minlongitude, maxlongitude,
                eventTypes,
                mindepth, maxdepth,
                minmagnitude, maxmagnitude,
                catalogName,
                pageable
        ).getContent();
    }

    @Override
    public List<EventType> getUsedEventTypes() {
        return repo.getDistinctEventTypes();
    }
}
