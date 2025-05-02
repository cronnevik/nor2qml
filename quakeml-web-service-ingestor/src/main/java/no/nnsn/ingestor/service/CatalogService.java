package no.nnsn.ingestor.service;

import no.nnsn.seisanquakeml.models.catalog.Catalog;

import java.util.List;

public interface CatalogService {
    void addCatalog(Catalog catalog);
    void updateCatalog(Catalog catalog);
    Catalog getCatalogByID(String catalogID);
    Catalog findCatalogID(String id);
    Catalog getCatalogByName(String name);
    List<Catalog> getAllCatalogs();
    void deleteCatalogByID(String catalogID);
}
