package no.nnsn.quakemlwebserviceingestorexecutable.service;

import no.nnsn.quakemlwebserviceingestorexecutable.repo.CatalogRepository;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    final CatalogRepository repo;

    @Autowired
    public CatalogServiceImpl(CatalogRepository repo) {
        this.repo = repo;
    }

    @Override
    public void addCatalog(Catalog catalog) {
        repo.save(catalog);
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        repo.save(catalog);
    }

    @Override
    public Catalog getCatalogByID(String catalogID) {
        return repo.findById(catalogID).orElse(null);
    }

    @Override
    public Catalog findCatalogID(String id) {
        return null;
    }

    @Override
    public List<Catalog> getAllCatalogs() {
        List<Catalog> catalogs = new ArrayList<>();
        repo.findAll().forEach(c -> catalogs.add(c));
        return catalogs;
    }

    @Override
    public Catalog getCatalogByName(String name) {
        return repo.findCatalogsByName(name);
    }

    @Override
    public void deleteCatalogByID(String catalogID) {

    }

}