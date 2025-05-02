package no.nnsn.convertercore.helpers;

import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventTypeCertainty;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;

public class EventTypeSetter {
    public static void setEventTypeAndUncertainty(Event event, Line1 line1, String defaultEvent, String defaultCertainty) {

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
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "O":
                event.setType(EventType.OTHER_EVENT);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "S":
                event.setType(EventType.SONIC_BOOM);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "C":
            case "G":
                event.setType(EventType.ICE_QUAKE);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "L":
            case "X": // deprecated Nordic code
                event.setType(EventType.LANDSLIDE);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "V":
                event.setType(EventType.VOLCANIC_ERUPTION);
                event.setTypeCertainty(EventTypeCertainty.KNOWN);
                break;
            case "":
            case " ":
                EventType eventType = EventType.fromValue(defaultEvent);
                if(eventType != null) {
                    event.setType(eventType);
                }
                EventTypeCertainty eventTypeCertainty = EventTypeCertainty.fromValue(defaultCertainty);
                if(eventTypeCertainty != null) {
                    event.setTypeCertainty(eventTypeCertainty);
                }
                break;
            default:
                // All other nordic codes become not_reported
                event.setType(EventType.NOT_REPORTED);
                break;
        }

    }
}
