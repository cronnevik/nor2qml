package no.nnsn.convertercore.helpers.collections;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;

import java.util.List;

@Data
@AllArgsConstructor
public class LineIEntityCollection {
    List<Comment> commentList;
    List<IgnoredLineError> errors;
}