package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers.*;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineF;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for QuakeML {@literal=>} Seisan Line type F
 *
 * @author Christian Rønnevik
 */

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class}
)
public abstract class LineFMapper {
    public static LineFMapper INSTANCE = Mappers.getMapper(LineFMapper.class);

    @Mappings({
            // link the Origin ID to Line1 for row ordering of other lines (such as LineF)
            @Mapping(target = "orgID", source = "focalMechanism.triggeringOriginID"),
            @Mapping(target = "strike", source = "focalMechanism.nodalPlanes.nodalPlane1.strike.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "dip", source = "focalMechanism.nodalPlanes.nodalPlane1.dip.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "rake", source = "focalMechanism.nodalPlanes.nodalPlane1.rake.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorStrike", source = "focalMechanism.nodalPlanes.nodalPlane1.strike.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorDip", source = "focalMechanism.nodalPlanes.nodalPlane1.dip.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorRake", source = "focalMechanism.nodalPlanes.nodalPlane1.rake.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "stationDistRatio", source = "focalMechanism.stationDistributionRatio", qualifiedBy = DoubleToString.class),
            @Mapping(target = "programUsed", source = "focalMechanism.methodID"),
            @Mapping(target = "agencyCode", ignore = true),
    })
    public abstract LineF mapLineFGeneral(FocalMechanism focalMechanism);

    @Mappings({
            // link the Origin ID to Line1 for row ordering of other lines (such as LineF)
            @Mapping(target = "orgID", source = "focalMechanism.triggeringOriginID"),
            @Mapping(target = "strike", source = "focalMechanism.nodalPlanes.nodalPlane1.strike.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "dip", source = "focalMechanism.nodalPlanes.nodalPlane1.dip.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "rake", source = "focalMechanism.nodalPlanes.nodalPlane1.rake.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorStrike", source = "focalMechanism.nodalPlanes.nodalPlane1.strike.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorDip", source = "focalMechanism.nodalPlanes.nodalPlane1.dip.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorRake", source = "focalMechanism.nodalPlanes.nodalPlane1.rake.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "stationDistRatio", source = "focalMechanism.stationDistributionRatio", qualifiedBy = DoubleToString.class),
            @Mapping(target = "programUsed", source = "focalMechanism.methodID"),
            @Mapping(target = "agencyCode", source = "focalMechanism.creationInfo.agencyID"),
            @Mapping(target = "blankField", ignore = true)
    })
    public abstract LineF mapLineFFromNode1(FocalMechanism focalMechanism);

    @Mappings({
            // link the Origin ID to Line1 for row ordering of other lines (such as LineF)
            @Mapping(target = "orgID", source = "focalMechanism.triggeringOriginID"),
            @Mapping(target = "strike", source = "focalMechanism.nodalPlanes.nodalPlane2.strike.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "dip", source = "focalMechanism.nodalPlanes.nodalPlane2.dip.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "rake", source = "focalMechanism.nodalPlanes.nodalPlane2.rake.value", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorStrike", source = "focalMechanism.nodalPlanes.nodalPlane1.strike.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorDip", source = "focalMechanism.nodalPlanes.nodalPlane1.dip.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "errorRake", source = "focalMechanism.nodalPlanes.nodalPlane1.rake.uncertainty", qualifiedBy = DoubleToString.class),
            @Mapping(target = "stationDistRatio", source = "focalMechanism.stationDistributionRatio", qualifiedBy = DoubleToString.class),
            @Mapping(target = "programUsed", source = "focalMechanism.methodID"),
            @Mapping(target = "agencyCode", source = "focalMechanism.creationInfo.agencyID"),
            @Mapping(target = "blankField", ignore = true)
    })
    public abstract LineF mapLineFFromNode2(FocalMechanism focalMechanism);

    @AfterMapping
    protected void setProperAgencyID(@MappingTarget LineF lineF, FocalMechanism focalMechanism) {
        if ( focalMechanism != null && focalMechanism.getCreationInfo() != null) {
            String agencyID =  focalMechanism.getCreationInfo().getAgencyID();
            // ISC format give the agencyID within author
            String author = focalMechanism.getCreationInfo().getAuthor();
            lineF.setAgencyCode(LineHelper.setMagAgencies(agencyID, author));
        }
    }

}
