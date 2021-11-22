package no.nnsn.ingestorcore.service;

import no.nnsn.ingestorcore.repo.SfileCheckerRepository;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
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
    public List<SfileCheck> getSfileListAll() {
        List<SfileCheck> checks = new ArrayList<>();
        repo.findAll().forEach(s -> checks.add(s));
        return checks;
    }

    @Override
    public SfileCheck getSfileById(String id) {
        return repo.findById(id).get();
    }

    @Override
    public void addSfile(SfileCheck sfileCheck) {
        repo.save(sfileCheck);
    }

    @Override
    public void addCollection(List<SfileCheck> sfileChecks) {
        repo.saveAll(sfileChecks);
    }

    @Override
    public void deleteSfile(String id) {
        repo.deleteById(id);
    }

    @Override
    public void deleteCollection(Set<SfileCheck> filePaths) {
        repo.deleteAll();
    }
}
