package no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuakemlContent {
    private String xmlns;
    private String evParamString;
}
