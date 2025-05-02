package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils;

import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.NodalPlanes;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.PrincipalAxes;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.IntegerQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ChildChecker {

    public static Boolean isCommentNull(List<Comment> cmts) {
        return cmts.isEmpty();
    }

    public static Boolean isCompositeTimeNull(List<CompositeTime> cts) {
        return cts.size() == 0;
    }

    public static Boolean isCreationInfoNull(CreationInfo ci) {
        return StringUtils.isBlank(ci.getAgencyID());
    }

    public static Boolean isRealQuantityNull(RealQuantity rq) {
        return rq == null || (rq.getValue() == null && rq.getUncertainty() == null);
    }

    public static Boolean isIntegerQuantityNull(IntegerQuantity iq) {
        return iq.getValue() == null && iq.getUncertainty() == null;
    }

    public static Boolean isNodalPlanesNull(NodalPlanes npls) {
        // TODO
        return null;
    }

    public static Boolean isPrincipalAxesNull(PrincipalAxes paxes) {
        // TODO
        return null;
    }

    public static Boolean isTimeQuantityNull(TimeQuantity time) {
        return time.getValue() == null && time.getUncertainty() == null;
    }

    public static Boolean isWaveformStreamIdNull(WaveformStreamID wsid) {
        if (wsid != null) {
            return (
                    wsid.getNetworkCode() == null &&
                            wsid.getStationCode() == null &&
                            wsid.getChannelCode() == null &&
                            wsid.getLocationCode() == null &&
                            wsid.getResourceURI() == null
            );
        } else {
            return false;
        }

    }






}
