package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterErrorLogging;
import no.nnsn.convertercore.errors.CustomException;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line1QuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineE;

import java.util.ArrayList;
import java.util.List;

public class Line1Converter {

    private final List<Line1> line1s;
    private final List<LineE> lineEs;
    private final SfileInfo sfileInfo;

    public Line1Converter(List<Line1> line1s, List<LineE> lineEs, SfileInfo sfileInfo) {
        this.line1s = line1s;
        this.lineEs = lineEs;
        this.sfileInfo = sfileInfo;
    }

    public Line1QuakemlEntities convert(QmlMapper mapper) {
        String preferredOriginID = null;
        String preferredMagnitudeID = null;
        List<Origin> origins = new ArrayList<>();
        List<Magnitude> magnitudes = new ArrayList<>();

        if (line1s != null) {
            for (int i = 0; i < line1s.size() ; i++) {
                Line1 line1 = line1s.get(i);

                // Map Origin entity
                Origin org = null;
                try {
                    Object orgObj = mapper.mapOrigin(line1, lineEs, sfileInfo.getErrorHandling());
                    if (orgObj instanceof Origin) {
                        org = (Origin) orgObj;
                        origins.add(org);
                        line1.setOrgID(org.getPublicID());
                        // Set preferred origin to the first line (type 1)
                        if (i == 0) {
                            preferredOriginID = org.getPublicID();
                        }
                    } else if (orgObj instanceof IgnoredLineError) {
                        IgnoredLineError error = (IgnoredLineError) orgObj;
                        error.setEventNumber(sfileInfo.getEventCount());
                        error.setFilename(sfileInfo.getFilename());
                        if (i == 0) { // Only remove Event if it is the first Line1
                            error.setEventRemoved(true); // Removing Event
                            ConverterErrorLogging.addError(error);
                            return new Line1QuakemlEntities(true);
                        } else {
                            ConverterErrorLogging.addError(error);
                            continue;
                        }
                    }
                } catch (Exception ex) {
                    throw new CustomException(
                            "Error in mapping to Origin entity. "
                                    + "Time: " + line1.getYear() + "-" + line1.getMonth() + "-" + line1.getDay()
                                    + "_" + line1.getHour() + ":" + line1.getMinutes() + ":" + line1.getSeconds() + "-"
                                    + ex.getMessage()
                    );
                }

                // Map Magnitude entity
                List<Object> magObjs = mapper.mapLine1Magnitudes(line1, org);
                magObjs.forEach(o -> {
                    if (o instanceof Magnitude) {
                        Magnitude m = (Magnitude) o;
                        magnitudes.add(m);
                    } else if (o instanceof IgnoredLineError) {
                        IgnoredLineError error = (IgnoredLineError) o;
                        error.setFilename(sfileInfo.getFilename());
                        ConverterErrorLogging.addError(error);
                    }
                });
            }

            // Determine Preferred Magnitude ID
            if (!magnitudes.isEmpty()) {
                // First should be preferred
                Magnitude firstMagnitude = magnitudes.get(0);
                preferredMagnitudeID = firstMagnitude.getPublicID();
            }

            return new Line1QuakemlEntities(preferredOriginID, preferredMagnitudeID, origins, magnitudes, false);
        }
        return new Line1QuakemlEntities();
    }
}
