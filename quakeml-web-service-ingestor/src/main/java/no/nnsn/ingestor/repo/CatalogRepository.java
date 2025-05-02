package no.nnsn.ingestor.repo;

import no.nnsn.seisanquakeml.models.catalog.Catalog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatalogRepository extends CrudRepository<Catalog, String> {
    @Query("SELECT c FROM Catalog c WHERE c.publicID = ?1")
    Catalog findCatalogById(String id);

    @Query("SELECT c FROM Catalog c WHERE c.catalogName = ?1")
    Catalog findCatalogsByName(String name);

    @Query("SELECT c.catalogName FROM Catalog c ")
    List<String> getCatalogNames();

    @Query("SELECT c.creationInfo.agencyID FROM Catalog c ")
    List<String> getContributors();
}
