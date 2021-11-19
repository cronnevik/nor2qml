package no.nnsn.quakemlwebservice.service;

import no.nnsn.quakemlwebservice.repository.CatalogRepository;
import no.nnsn.quakemlwebservice.repository.SfileEventRepository;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    final
    CatalogRepository repo;

    public CatalogServiceImpl(CatalogRepository repo) {
        this.repo = repo;
    }

    @Override
    public Catalog getCatalogNyName(String name) {
        return repo.findCatalogByName(name);
    }

    @Override
    public List<Catalog> getCatalogs() {
        List<Catalog> catalogs = new ArrayList<>();
        repo.findAll().forEach(catalogs::add);
        return catalogs;
    }

    @Override
    public List<String> getCatalogNames() {
        return repo.getCatalogNames();
    }

    @Override
    public List<String> getContributors() {
        return repo.getContributors();
    }

}
