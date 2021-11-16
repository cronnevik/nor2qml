package no.nnsn.quakemlwebservice.dao;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class EventFormQuery {
    String catalogName;

    String startTime;
    String endTime;

    Double minLatitude;;
    Double maxLatitude;
    Double minLongitude;
    Double maxLongitude;

    String eventTypes;

    Double minDepth;
    Double maxDepth;

    Double minMagnitude;
    Double maxMagnitude;

    String orderBy = OrderByType.TIME.toString();
    Integer limit = 1000;
    String format = FormatType.XML.toString();
    Integer noData = NoDataType.FOURNULLFOUR.getName();

    Boolean includePhases;
    Boolean includeFocalMechanism;

    // INTAROS default parameters
    public EventFormQuery getIntarosInstance() {
        startTime = "2002-02-20T00:00:00";
        endTime = "2002-02-22T00:00:00";
        minLatitude = 65.0;
        maxLatitude = 90.0;
        minLongitude = -180.0;
        maxLongitude = 180.0;

        return this;
    }

    // NNSN default parameters
    public EventFormQuery getNnsnInstance() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLimit = now.minusWeeks(1);
        startTime = weekLimit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        endTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        minLatitude = 54.0;
        maxLatitude = 85.0;
        minLongitude = -15.0;
        maxLongitude = 35.0;

        return this;
    }

    // NNSN default parameters
    public EventFormQuery getDefaultInstance() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime weekLimit = now.minusWeeks(1);
        startTime = weekLimit.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        endTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        minLatitude = -90.0;
        maxLatitude = 90.0;
        minLongitude = -180.0;
        maxLongitude = 180.0;

        return this;
    }
}
