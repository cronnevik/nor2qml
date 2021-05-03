package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class GeneralLineQualifiers {
    /*
     * General Qualifiers
     *
     */
    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntegerToString {}

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface DoubleToString {}
}
