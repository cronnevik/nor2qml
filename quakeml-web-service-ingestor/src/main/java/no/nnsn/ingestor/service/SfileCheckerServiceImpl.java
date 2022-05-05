package no.nnsn.ingestor.service;

import no.nnsn.ingestor.repo.SfileCheckerRepository;
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SfileCheckerServiceImpl implements SfileCheckerService {

    final SfileCheckerRepository repo;

    @Autowired
    public SfileCheckerServiceImpl(SfileCheckerRepository repo) {
        this.repo = repo;
    }

    @Override
    @Async
    public List<SfileInformation> getSfileListAll() {
        List<SfileInformation> checks = new ArrayList<>();
        repo.findAll().forEach(s -> checks.add(s));
        return checks;
    }

    @Override
    public SfileInformation getSfileById(String id) {
        return repo.findById(id).get();
    }

    @Override
    public void addSfile(SfileInformation sfileInformation) {
        repo.save(sfileInformation);
    }

    @Override
    public void addCollection(List<SfileInformation> sfileInformations) {
        repo.saveAll(sfileInformations);
    }

    @Override
    public void deleteSfile(String id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteCollection(Set<SfileInformation> filePaths) {
        repo.deleteAll();
    }
}
