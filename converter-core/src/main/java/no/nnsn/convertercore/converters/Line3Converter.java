package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterErrorLogging;
import no.nnsn.convertercore.helpers.LineErrorGenerator;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line3QuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line3;

import java.util.ArrayList;
import java.util.List;

public class Line3Converter {
    private final List<Line3> l3s;
    private final SfileInfo sfileInfo;

    public Line3Converter(List<Line3> l3s, SfileInfo sfileInfo) {
        this.l3s = l3s;
        this.sfileInfo = sfileInfo;
    }

    public Line3QuakemlEntities convert(QmlMapper mapper) {
        if (l3s != null) {
            List<EventDescription> descriptions = new ArrayList<>();
            List<Comment> comments = new ArrayList<>();

            // loop through each line 3s for multiple mappings
            for (Line3 line3: l3s) {
                // Check if locality exists in line 3
                String localityID = "LOCALITY:";
                if (line3.getCommentText() != null) {
                    boolean containsLocality = line3.getCommentText().contains(localityID);
                    if (containsLocality) {
                        String commentLine = line3.getCommentText();
                        int startOfText = commentLine.indexOf(":");
                        String locName = commentLine.substring(startOfText +1).trim();

                        if (!locName.isEmpty()) {
                            descriptions.add(new EventDescription(
                                    locName,
                                    EventDescriptionType.REGION_NAME
                            ));

                            continue;
                        }
                    }
                }

                // Each line3 as comment object in QuakeML
                try {
                    comments.add(mapper.mapL3Comment(line3));
                } catch (Exception ex) {
                    ConverterErrorLogging.addError(LineErrorGenerator.generateError(line3, ex, sfileInfo));
                }
            }

            return new Line3QuakemlEntities(descriptions, comments);
        }
        return new Line3QuakemlEntities();
    }
}
