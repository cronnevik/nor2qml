package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.NodalPlaneSetter;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.NodalPlanes;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineF;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to FocalMechanism Mapper - Mapping of the Nordic format to FocalMechanism entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralHelper.class})
public abstract class NordicToFocalMechMapper {

    /** Mapper instance. */
    public static NordicToFocalMechMapper INSTANCE = Mappers.getMapper(NordicToFocalMechMapper.class);

    /**
     * Mapping of properties for the FocalMechanism entity from the type 1 and F lines within Nordic format to QuakeML version 2.0.
     * The origin object from QuakeML v2.0 itself is needed to link the focalmechanism object to the triggering origin.
     *
     * @param lineF LineF object
     * @param line1 Line1 object
     * @param org Origin object
     * @return FocalMechanism
     */
    @Mappings({
            // String - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // WaveformStreamID (Object) - NO MAPPING DETERMINED
            @Mapping(target = "waveformID", ignore = true),
            // String - Pointer to Origin publicID
            @Mapping(target = "triggeringOriginID", source = "org.publicID"),
            // NodalPlanes (Object) - set in AfterMapping
            @Mapping(target = "nodalPlanes", ignore = true),
            // PrincipalAxes (Object) - NO MAPPING DETERMINED
            @Mapping(target = "principalAxes", ignore = true),
            // String - Conditions for methodID set in AfterMapping
            @Mapping(target = "methodID", source="lineF.programUsed"),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "azimuthalGap", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "misfit", ignore = true),
            // Double - NO MAPPING DETERMINED
            @Mapping(target = "stationDistributionRatio", ignore = true),
            // Integer - NO MAPPING DETERMINED
            @Mapping(target = "stationPolarityCount", ignore = true),
            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object)
            @Mapping(target = "creationInfo.agencyID", source="lineF.agencyCode")
    })
    public abstract FocalMechanism mapFocalMech(LineF lineF, Line1 line1, Origin org);

    /*
     * AfterMappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the focalMechanism object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param fMech The FocalMechanism object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void buildPublicID(@MappingTarget FocalMechanism fMech, Line1 line1) {
        fMech.setPublicID(
                IdGenerator.getInstance().genTypicalPublicID(
                        line1.getYear(),
                        line1.getHypoCenterRepAgency(),
                        FocalMechanism.class
                )
        );
    }

    /**
     * AfterMapping - Building the nodalPlanes
     *
     * @param fMech The FocalMechanism object that were build in the initial mapping.
     * @param lineF LineF object.
     */
    @AfterMapping
    protected void setNodalPlane(@MappingTarget FocalMechanism fMech, LineF lineF) {

        NodalPlanes npls = new NodalPlanes();
        fMech.setNodalPlanes(npls);

        NodalPlaneSetter.setNodalPlane(fMech, lineF);
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID} and
     * {@link NodalPlanes},
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.PrincipalAxes}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param fMech The FocalMechanism object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget FocalMechanism fMech) {
        // WaveformID
        if (ChildChecker.isWaveformStreamIdNull(fMech.getWaveformID())){
            fMech.setWaveformID(null);
        }

        // TODO - implement rest of null checks
    }

    /*
     * Project Specific AfterMappings
     *
     */

    /**
     * AfterMapping for INTAROS project - Changing the methodID to a more explainable string value
     *
     * @param fMech The FocalMechanism object that were build in the initial mapping.
     * @param lineF LineF object.
     */
    @AfterMapping // Specific for the INTAROS project
    protected  void setPorgramUsedISC(@MappingTarget FocalMechanism fMech, LineF lineF) {
        if (fMech.getMethodID() != null) {
            if (fMech.getMethodID().equals("IMSNOR")) {
                fMech.setMethodID("Read from ISC database");
            }
        }
    }
}
