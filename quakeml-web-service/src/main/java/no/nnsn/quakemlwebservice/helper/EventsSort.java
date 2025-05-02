package no.nnsn.quakemlwebservice.helper;

import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;

import java.util.Comparator;
import java.util.List;

public class EventsSort {
    public static void sort(List<Event> events, OrderByType type) {
        switch (type) {
            case TIME:
                events.sort(Comparator.comparing(e -> {
                    String prefOrgID = e.getPreferredOriginID();
                    Origin prefOrg = null;
                    for (Origin o: e.getOrigin()) {
                        if (o.getPublicID().equals(prefOrgID)) {
                            prefOrg = o;
                        }
                    }
                    return prefOrg.getTime().getValue();
                }, Comparator.reverseOrder()));
                break;
            case TIME_ASC:
                events.sort(Comparator.comparing(e -> {
                    String prefOrgID = e.getPreferredOriginID();
                    Origin prefOrg = null;
                    for (Origin o: e.getOrigin()) {
                        if (o.getPublicID().equals(prefOrgID)) {
                            prefOrg = o;
                        }
                    }
                    return prefOrg.getTime().getValue();
                }, Comparator.naturalOrder()));
                break;
            case MAGNITUDE:
                events.sort(Comparator.comparing(e -> {
                    String preMagID = e.getPreferredMagnitudeID();
                    Magnitude prefMag = null;
                    if (e.getMagnitude() != null && e.getMagnitude().size() > 0) {
                        for (Magnitude m: e.getMagnitude()) {
                            if (m.getPublicID().equals(preMagID)) {
                                prefMag = m;
                            }
                        }
                        return prefMag.getMag().getValue();
                    }
                    return null;
                }, Comparator.nullsLast(Comparator.reverseOrder())));
                break;
            case MAGNITUDE_ASC:
                events.sort(Comparator.comparing(e -> {
                    String preMagID = e.getPreferredMagnitudeID();
                    Magnitude prefMag = null;
                    if (e.getMagnitude() != null && e.getMagnitude().size() > 0) {
                        for (Magnitude m: e.getMagnitude()) {
                            if (m.getPublicID().equals(preMagID)) {
                                prefMag = m;
                            }
                        }
                        return prefMag.getMag().getValue();
                    }
                    return null;
                }, Comparator.nullsLast(Comparator.naturalOrder())));
                break;
        }
    }
}
