package no.nnsn.quakemlwebservice.service;

import no.nnsn.seisanquakemljpa.models.catalog.Catalog;

import java.util.List;

public interface CatalogService {
    Catalog getCatalogNyName(String name);
    List<Catalog> getCatalogs();
    List<String> getCatalogNames();
    List<String> getContributors();
}
