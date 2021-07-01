package no.nnsn.convertercore.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;

import java.util.List;

@Data
@AllArgsConstructor
public class Line3EntityCollection {
    List<EventDescription> descriptionList;
    List<Comment> commentList;
    List<IgnoredLineError> errors;
}
