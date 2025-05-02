package no.nnsn.quakemlwebservice.helper;

import no.nnsn.quakemlwebservice.dao.TextFormat;
import no.nnsn.seisanquakeml.models.catalog.SfileEvent;

import java.util.List;

public class TextOutput {
    private final static String header = "#EventID|Time|Latitude|Longitude|Depth/km|Author|Catalog|Contributor|ContributorID|MagType|Magnitude|MagAuthor|EventLocationName|EventType";

    public static String getText(List<TextFormat> events) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(header);
        outputBuilder.append(System.lineSeparator());
        events.forEach(e -> {
            outputBuilder.append(
                    e.getEventID() + "|" +
                    e.getTime() + "|" + e.getLatitude() + "|" + e.getLongitude() + "|" + e.getDepth() + "|"
                    + e.getAuthor() + "|" + e.getCatalog() + "|" + e.getContributor() + "|" + e.getContributorID() + "|"
                    + e.getMagType() + "|" + e.getMagnitude() + "|" + e.getMagAuthor() + "|"
                    + e.getEventLocationName() + "|" + e.getEventType()
                    + System.lineSeparator()
            );
        });

        return outputBuilder.toString();
    }

    public static String getTextFormat(List<SfileEvent> events) {
        StringBuilder outputBuilder = new StringBuilder();
        outputBuilder.append(header);
        outputBuilder.append(System.lineSeparator());
        events.forEach(e -> {
            String eventID = e.getEventID();
            String time = (e.getTime() != null) ? e.getTime() : "";
            String latitude = (e.getLatitude() != null) ? e.getLatitude().toString() : "";
            String longitude = (e.getLongitude() != null) ? e.getLongitude().toString() : "";
            String depth = (e.getDepth() != null) ? e.getDepth().toString() : "";
            String author = (e.getAuthor() != null) ? e.getAuthor() : "";
            String catalog = (e.getCatalog() != null) ? e.getCatalog().getCatalogName() : "";
            String contributor = (e.getContributor() != null) ? e.getContributor() : "";
            String contributorID = (e.getContributorID() != null) ? e.getContributorID() : "";
            String magType = (e.getMagType() != null) ? e.getMagType() : "";
            String magnitude = (e.getMagnitude() != null) ? e.getMagnitude().toString() : "";
            String magAuthor = (e.getMagAuthor() != null) ? e.getMagAuthor() : "";
            String eventLocationName = (e.getLocationName() != null) ? e.getLocationName() : "";
            String eventType = (e.getType() != null) ? e.getType().value() : "";

            outputBuilder.append(
                    eventID + "|"
                    + time + "|" + latitude + "|" + longitude + "|" + depth + "|"
                    + author + "|" + catalog + "|" + contributor + "|" + contributorID + "|"
                    + magType + "|" + magnitude + "|" + magAuthor + "|"
                    + eventLocationName + "|" + eventType
                    + System.lineSeparator()
            );
        });
        return outputBuilder.toString();
    }
}
