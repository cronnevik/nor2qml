package no.nnsn.convertercore.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SfileInfo {
    int eventCount;
    String filename;
    String errorHandling;
}
