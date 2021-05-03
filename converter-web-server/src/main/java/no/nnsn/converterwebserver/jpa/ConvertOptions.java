package no.nnsn.converterwebserver.jpa;

import lombok.Data;

@Data
public class ConvertOptions {
    private IdOptions id;
    private DataOptions data;
    private String version;
    private String errorHandling;
}
