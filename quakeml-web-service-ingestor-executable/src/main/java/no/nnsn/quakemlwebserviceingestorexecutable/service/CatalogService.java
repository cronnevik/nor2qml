package no.nnsn.quakemlwebserviceingestorexecutable.service;

import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.EventParameters;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
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
