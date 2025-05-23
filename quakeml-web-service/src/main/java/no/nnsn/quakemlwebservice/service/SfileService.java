package no.nnsn.quakemlwebservice.service;

import no.nnsn.seisanquakeml.models.catalog.SfileInformation;

import java.util.List;

public interface SfileService {
    SfileInformation getSfileById(String id);
    List<SfileInformation> getSfiles(List<String> sfileIds);
}
