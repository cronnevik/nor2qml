package no.nnsn.quakemlwebservice.repository;

import no.nnsn.seisanquakeml.models.catalog.SfileInformation;
import no.nnsn.seisanquakeml.models.catalog.SfileEvent;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SfileEventRepository extends PagingAndSortingRepository<SfileEvent, String> {

    // Queries by SfileCheck

    @Query("SELECT se.sfile FROM SfileEvent se WHERE se.eventID = ?1")
    SfileInformation findSfileByEventId(String eventID);

    // Queries by SfileEvent

    @Query("SELECT se FROM SfileEvent se " +
            "WHERE ((:catalogName IS NULL) OR (se.catalog.catalogName = :catalogName)) " +
            "AND se.time BETWEEN :starttime AND :endtime " +
            "AND ((:minlatitude IS NULL OR :maxlatitude IS NULL) OR (se.latitude BETWEEN :minlatitude AND :maxlatitude)) " +
            "AND ((:minlongitude IS NULL OR :maxlongitude IS NULL) OR (se.longitude BETWEEN :minlongitude AND :maxlongitude)) " +
            "AND (se.type IN (:eventTypes)) " +
            "AND ((:mindepth IS NULL OR :maxdepth IS NULL) OR (se.depth BETWEEN :mindepth AND :maxdepth)) " +
            "AND ((:minmagnitude IS NULL OR :maxmagnitude IS NULL) OR (se.magnitude BETWEEN :minmagnitude AND :maxmagnitude))")
    Page<SfileEvent> findAllSfileEvents(
            String starttime, String endtime,
            Double minlatitude, Double maxlatitude, Double minlongitude, Double maxlongitude,
            List<EventType> eventTypes,
            Double mindepth, Double maxdepth,
            Double minmagnitude, Double maxmagnitude,
            String catalogName,
            Pageable pageable
    );

    // Queries by IDS

    @Query("SELECT se.sfile.sfileID FROM SfileEvent se WHERE se.time BETWEEN :starttime AND :endtime ")
    Page<String> findSfileIdsByTime(String starttime, String endtime, Pageable pageable);

    @Query("SELECT se.sfile.sfileID FROM SfileEvent se WHERE se.time BETWEEN :starttime AND :endtime AND se.latitude BETWEEN :minlatitude AND :maxlatitude AND se.longitude BETWEEN :minlongitude AND :maxlongitude")
    Page<String> findSfileIdsByTimeAndCords(String starttime, String endtime, Double minlatitude, Double maxlatitude, Double minlongitude, Double maxlongitude, Pageable pageable);

    @Query("SELECT se.sfile.sfileID FROM SfileEvent se " +
            "WHERE ((:catalogName IS NULL) OR (se.catalog.catalogName = :catalogName)) " +
            "AND se.time BETWEEN :starttime AND :endtime " +
            "AND ((:minlatitude IS NULL OR :maxlatitude IS NULL) OR (se.latitude BETWEEN :minlatitude AND :maxlatitude)) " +
            "AND ((:minlongitude IS NULL OR :maxlongitude IS NULL) OR (se.longitude BETWEEN :minlongitude AND :maxlongitude)) " +
            "AND (se.type IN (:eventTypes)) " +
            "AND ((:mindepth IS NULL OR :maxdepth IS NULL) OR (se.depth BETWEEN :mindepth AND :maxdepth)) " +
            "AND ((:minmagnitude IS NULL OR :maxmagnitude IS NULL) OR (se.magnitude BETWEEN :minmagnitude AND :maxmagnitude))")
    Page<String> findSfileIds(
            String starttime, String endtime,
            Double minlatitude, Double maxlatitude, Double minlongitude, Double maxlongitude,
            List<EventType> eventTypes,
            Double mindepth, Double maxdepth,
            Double minmagnitude, Double maxmagnitude,
            String catalogName,
            Pageable pageable
    );

    // Event types
    @Query("SELECT DISTINCT se.type from SfileEvent se")
    List<EventType> getDistinctEventTypes();
}
