package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.EventDescription;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line3QuakemlEntities {
    List<EventDescription> descriptionList;
    List<Comment> commentList;
}
