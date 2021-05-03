package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;

/**
 * Generic class of setting the earthModelID of various QuakeML entities.
 *
 * @author Christian Rønnevik
 */
public class EarthModelIdSetter<T> {

    /**
     * ID setter - Set the ID for specified entity based on the model marker within the Nordic format.
     * The markers are represented by 1 character letter that are converted into the coherent model name/ID in QuakeML.
     *
     * @param line1 The Line1 object within the Nordic format that contains the actual marker for the ID.
     * @param type The QuakeML entity (object) that the model should be attached to.
     */
    public void setID(Line1 line1, T type) {
        if (line1.getDistanceIndicator() != null) {
            String modelID = "";
            switch (line1.getDistanceIndicator()) {
                case "D":
                    modelID = "IASP91";
                    break;
                case "L":
                    modelID = "Local model";
                    break;
                case "R":
                    modelID = "Unknown";
                    break;
                default:
                    break;
            }

            if (!modelID.isEmpty()) {
                if (type instanceof Origin) {
                    Origin org = (Origin) type;
                    org.setEarthModelID(modelID);
                } else if (type instanceof Arrival) {
                    Arrival arr = (Arrival) type;
                    arr.setEarthModelID(modelID);
                }
            }
        }
    }
}
