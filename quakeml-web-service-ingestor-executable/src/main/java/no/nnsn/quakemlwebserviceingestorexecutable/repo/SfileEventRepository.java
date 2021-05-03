package no.nnsn.quakemlwebserviceingestorexecutable.repo;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfileEventRepository extends JpaRepository<SfileEvent, String> {

    @Query("SELECT s.sfile FROM SfileEvent s WHERE s.catalog.catalogName = ?1")
    List<SfileCheck> getSfilesByCatalog(String name);
}
