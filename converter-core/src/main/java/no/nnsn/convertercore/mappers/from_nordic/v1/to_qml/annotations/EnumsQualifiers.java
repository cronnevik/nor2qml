package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations;

import org.mapstruct.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class EnumsQualifiers {

    /*
     * Amplitude Entity Qualifiers
     *
     */

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface Line4ToAmplitudeCategory {}

    /*
     * Origin Entity Qualifiers
     *
     */

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface StringToOriginDepthType {}

    /*
     * Pick Entity Qualifiers
     *
     */

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface StringToPickOnset {}

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    public @interface StringToPickPolarity {}
}
