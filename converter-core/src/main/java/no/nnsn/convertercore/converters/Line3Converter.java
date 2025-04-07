package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.LineErrorGenerator;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line3QuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventDescriptionType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line3;

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
            List<IgnoredLineError> errors = new ArrayList<>();

            // loop through each line 3s for multiple mappings
            line3Loop:
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

                            continue line3Loop;
                        }
                    }
                }

                // Each line3 as comment object in QuakeML
                try {
                    comments.add(mapper.mapL3Comment(line3));
                } catch (Exception ex) {
                    errors.add(LineErrorGenerator.generateError(line3, ex, sfileInfo));
                    continue line3Loop;
                }
            }

            return new Line3QuakemlEntities(descriptions, comments, errors);
        }
        return new Line3QuakemlEntities();
    }
}
