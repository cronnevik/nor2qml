package no.nnsn.ingestorcore.repo;
;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SfileCheckerRepository extends JpaRepository<SfileCheck, String> {
}
