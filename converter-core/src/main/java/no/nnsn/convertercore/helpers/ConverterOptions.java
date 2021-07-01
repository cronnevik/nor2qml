package no.nnsn.convertercore.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConverterOptions {
    String errorHandling;
    CallerType caller;
    ConverterProfile profile;
    String idSuffix;
}
