package no.nnsn.quakemlwebservice.service;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;

import java.util.List;
import java.util.Set;

public interface SfileService {
    SfileCheck getSfileById(String id);
    List<SfileCheck> getSfiles(List<String> sfileIds);
}
