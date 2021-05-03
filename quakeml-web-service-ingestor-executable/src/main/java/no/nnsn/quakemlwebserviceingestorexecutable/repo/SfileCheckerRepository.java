package no.nnsn.quakemlwebserviceingestorexecutable.repo;
;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfileCheckerRepository extends JpaRepository<SfileCheck, String> {
}
