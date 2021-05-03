package no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers;

import lombok.Data;

import java.util.Map;

@Data
public class SfileOptions {
    private Map<String, String> id;
    private String version;
    private String errorHandling;
}