package no.nnsn.convertercore.helpers;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventTypeCertainty;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;

public class EventTypeSetter {
    public static void setEventTypeAndUncertainty(Event event, Line1 line1) {

        String eventId;

        // Check if eventID exists and if not set to blank
        if (line1.getEventID() != null) {
            eventId = line1.getEventID();
        } else {
            eventId = "";
        }

        // Check nordic codes and set type and certainty accordingly
        switch (eventId) {
            case "Q":
                event.setType(EventType.EARTHQUAKE);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "E":
                event.setType(EventType.EXPLOSION);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "P":
                event.setType(EventType.EXPLOSION);
                event.setTypeCertainty(EventTypeCertainty.SUSPECTED);
                break;
            case "I":
                event.setType(EventType.INDUCED_OR_TRIGGERED_EVENT);
                break;
            case "O":
                event.setType(EventType.OTHER_EVENT);
                break;
            case "S":
                event.setType(EventType.SONIC_BOOM);
                break;
            case "C":
                event.setType(EventType.ICE_QUAKE);
                break;
            case "G":
                event.setType(EventType.ICE_QUAKE);
                break;
            case "L":
                event.setType(EventType.LANDSLIDE);
                break;
            case "X":
                event.setType(EventType.LANDSLIDE);
                break;
            case "V":
                event.setType(EventType.VOLCANIC_ERUPTION);
                break;
            default:
                // All other nordic codes become not_reported
                event.setType(EventType.NOT_REPORTED);
                break;
        }

    }
}
