package no.nnsn.quakemlwebservice.repository;

import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfileRepository extends PagingAndSortingRepository<SfileCheck, String> {

    @Query("SELECT sc FROM SfileCheck sc WHERE sc.sfileID IN (:sfileIds) ORDER BY FIELD(sc.sfileID, :sfileIds) ")
    List<SfileCheck> getSfiles(List<String> sfileIds);
}
