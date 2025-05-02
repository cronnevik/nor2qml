package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.helpers;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.EnumsQualifiers.*;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeCategory;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.PickOnset;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.enums.PickPolarity;


public class EnumsHelper {
    /*
     * Amplitude V2.0 Entity Conversions
     *
     */

    @Line4ToAmplitudeCategory
    public AmplitudeCategory mapAmplitudeCategory(String val) {
        return null;
    }

    /*
     * Origin V2.0 Entity Conversions
     *
     */

    @StringToOriginDepthType
    public OriginDepthType mapOriginDepthType(String val) {
        switch (val) {
            case "F":
                return OriginDepthType.OPERATOR_ASSIGNED;
            case " ":
                return OriginDepthType.FROM_LOCATION;
            default:
                return null;
        }
    }

    /*
     * Pick V2.0 Entity conversions
     *
     */

    @StringToPickOnset
    public PickOnset mapQualityIndToPickOnset(String val) {
        switch (val) {
            case "I":
                return PickOnset.IMPULSIVE;
            case "E":
                return PickOnset.EMERGENT;
            default:
                return PickOnset.QUESTIONABLE;
        }
    }

    @StringToPickPolarity
    public PickPolarity mapFirstMotionToPickPolarity(String val){
        switch(val) {
            case "C":
                return PickPolarity.POSITIVE;
            case "D":
                return PickPolarity.NEGATIVE;
            default:
                return PickPolarity.UNDECIDABLE;
        }
    }
}
