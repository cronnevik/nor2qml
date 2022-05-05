package no.nnsn.ingestor.repo;
;
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SfileCheckerRepository extends JpaRepository<SfileInformation, String> {
}
