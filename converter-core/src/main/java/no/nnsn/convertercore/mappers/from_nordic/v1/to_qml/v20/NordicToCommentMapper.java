package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.*;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to Comment Mapper - Mapping of the Nordic format to Comment entity in QuakeML 2.0.
 * The comments may come from various sources, which typically is the {@link Line3}, {@link Line6} and {@link LineI} within the Nordic format.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public abstract class NordicToCommentMapper {

    /** Mapper instance. */
    public static NordicToCommentMapper INSTANCE = Mappers.getMapper(NordicToCommentMapper.class);

    /**
     * Mapping of properties for the Comment entity from the type 3 lines within Nordic format to QuakeML version 2.0.
     *
     * @param line3 Line3 object
     * @return Comment
     */
    @Mappings({
            // String
            @Mapping(target = "text", source="line3.lineText"),
            // String - identifier for line type
            @Mapping(target = "id",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType.LINETYPE_3.getLineType())"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Comment mapLine3ToComment(Line3 line3);

    /**
     * Mapping of properties for the Comment entity from the type 5 lines within Nordic format to QuakeML version 2.0.
     *
     * @param line5 Line5 object
     * @return Arrival
     */
    @Mappings({
            // String
            @Mapping(target = "text", source="line5.lineText"),
            // String - identifier for line type
            @Mapping(target = "id",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType.LINETYPE_5.getLineType())"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Comment mapLine5ToComment(Line5 line5);

    /**
     * Mapping of properties for the Comment entity from the type 6 lines within Nordic format to QuakeML version 2.0.
     *
     * @param line6 Line6 object
     * @return Arrival
     */
    @Mappings({
            // String
            @Mapping(target = "text", source="line6.lineText"),
            // String - identifier for line type
            @Mapping(target = "id",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType.LINETYPE_6.getLineType())"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Comment mapLine6ToComment(Line6 line6);

    /**
     * Mapping of properties for the Comment entity from the type I lines within Nordic format to QuakeML version 2.0.
     *
     * @param lineI LineI object
     * @return Arrival
     */
    @Mappings({
            // String
            @Mapping(target = "text", source="lineI.lineText"),
            // String - identifier for line type
            @Mapping(target = "id",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType.LINETYPE_I.getLineType())"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Comment mapLineITOComment(LineI lineI);

    @Mappings({
            // String
            @Mapping(target = "text", source="lineS.lineText"),
            // String - identifier for line type
            @Mapping(target = "id",
                    expression = "java(no.nnsn.seisanquakeml.models.sfile.v1.enums.LineType.LINETYPE_S.getLineType())"),
            // CreationInfo (Object) - NO MAPPING DETERMINED
            @Mapping(target = "creationInfo", ignore = true),
    })
    public abstract Comment mapLineSToComment(LineS lineS);
}
