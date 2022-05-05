package no.nnsn.ingestor.dao;

import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;

import java.util.List;

public interface SfileCheckDao {
    void addSfile(SfileInformation sfileInformation);
    List<SfileInformation> getSfiles();
}
