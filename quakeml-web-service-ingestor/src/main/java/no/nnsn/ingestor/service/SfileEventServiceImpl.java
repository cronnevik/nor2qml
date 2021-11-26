package no.nnsn.ingestor.service;

import no.nnsn.ingestor.repo.SfileEventRepository;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class SfileEventServiceImpl implements SfileEventService {

    final SfileEventRepository repo;

    @Autowired
    public SfileEventServiceImpl(SfileEventRepository repo) {
        this.repo = repo;
    }


    @Override
    public void addOrUpdateEvent(SfileEvent event) {
        repo.save(event);
    }

    @Override
    public void addEvents(List<SfileEvent> events) {
        repo.saveAll(events);
    }

    @Override
    public SfileEvent getEventByID(String eventID) {
        return repo.findById(eventID).get();
    }

    @Override
    public List<SfileEvent> getAllEvents() {
        return null;
    }

    @Override
    public void deleteEventByID(String eventID) {
        repo.deleteById(eventID);
    }

    @Override
    public List<SfileCheck> getSfileListByCatalogName(String name) {
        return repo.getSfilesByCatalog(name);
    }
}
