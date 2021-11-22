package no.nnsn.ingestorcore.dao;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;

import java.util.List;

public interface SfileCheckDao {
    void addSfile(SfileCheck sfileCheck);
    List<SfileCheck> getSfiles();
}
