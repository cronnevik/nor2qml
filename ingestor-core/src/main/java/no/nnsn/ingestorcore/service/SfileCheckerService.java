package no.nnsn.ingestorcore.service;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface SfileCheckerService {
    List<SfileCheck> getSfileListAll();
    SfileCheck getSfileById(String id);
    void addSfile(SfileCheck sfileCheck);
    void addCollection(List<SfileCheck> sfileChecks);
    void deleteSfile(String id);
    void deleteCollection(Set<SfileCheck> filePaths);
}
