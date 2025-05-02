package no.nnsn.quakemlwebservice.repository;

import no.nnsn.seisanquakeml.models.catalog.SfileInformation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfileRepository extends PagingAndSortingRepository<SfileInformation, String> {

    @Query("SELECT sc FROM SfileInformation sc WHERE sc.sfileID IN (:sfileIds) ORDER BY FIELD(sc.sfileID, :sfileIds) ")
    List<SfileInformation> getSfiles(List<String> sfileIds);
}
