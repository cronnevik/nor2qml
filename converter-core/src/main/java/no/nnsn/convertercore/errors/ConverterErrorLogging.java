package no.nnsn.convertercore.errors;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ConverterErrorLogging {
    @Getter
    private static final List<IgnoredLineError> ignoredErrors = new ArrayList<IgnoredLineError>();

    public static void addError(IgnoredLineError e) {
        ignoredErrors.add(e);
    }

    public static int size() {
        return ignoredErrors.size();
    }

}
