package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineE;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for QuakeML {@literal=>} Seisan Line type E
 *
 * @author Christian Rønnevik
 */

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class}
        )
public abstract class LineEMapper {
    public static LineEMapper INSTANCE = Mappers.getMapper(LineEMapper.class);

    @Mappings({
        @Mapping(target = "originTimeError", source = "origin.time.uncertainty", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
        @Mapping(target = "latitudeError", source = "origin.latitude.uncertainty", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
        @Mapping(target = "longitudeError", source = "origin.longitude.uncertainty", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
        @Mapping(target = "depthError", source = "origin.depth.uncertainty", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
        @Mapping(target = "gap", source = "origin.quality.azimuthalGap", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
    })
    public abstract LineE mapFullLineE(Origin origin);

}
