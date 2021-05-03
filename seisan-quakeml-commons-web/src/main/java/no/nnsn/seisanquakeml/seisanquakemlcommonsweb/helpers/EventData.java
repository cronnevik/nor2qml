package no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.nnsn.convertercore.errors.IgnoredLineError;

import java.util.List;

@Data
@AllArgsConstructor
public class EventData {
    private String qml;
    private List<IgnoredLineError> errors;
}
