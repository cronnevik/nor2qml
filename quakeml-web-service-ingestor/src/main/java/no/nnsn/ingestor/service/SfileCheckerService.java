package no.nnsn.ingestor.service;

import no.nnsn.seisanquakeml.models.catalog.SfileInformation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface SfileCheckerService {
    List<SfileInformation> getSfileListAll();
    SfileInformation getSfileById(String id);
    void addSfile(SfileInformation sfileInformation);
    void addCollection(List<SfileInformation> sfileInformations);
    void deleteSfile(String id);
    void deleteCollection(Set<SfileInformation> filePaths);
}
