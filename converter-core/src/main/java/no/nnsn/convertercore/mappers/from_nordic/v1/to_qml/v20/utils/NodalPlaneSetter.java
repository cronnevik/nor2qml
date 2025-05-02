package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils;

import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.NodalPlane;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineF;

public class NodalPlaneSetter {
    public static void setNodalPlane(FocalMechanism fMech, LineF lineF) {
        Double strike =
                (lineF.getStrike() != null && !lineF.getStrike().isEmpty()) ? Double.valueOf(lineF.getStrike()) : null;
        Double strikeUncertainty =
                (lineF.getErrorStrike() != null
                        && !lineF.getErrorStrike().isEmpty()) ? Double.valueOf(lineF.getErrorStrike()) : null;
        Double dip =
                (lineF.getDip() != null && !lineF.getDip().isEmpty()) ? Double.valueOf(lineF.getDip()) : null;
        Double dipUncertainty =
                (lineF.getErrorDip() != null
                        && !lineF.getErrorDip().isEmpty()) ? Double.valueOf(lineF.getErrorDip()) : null;
        Double rake =
                (lineF.getRake() != null && !lineF.getRake().isEmpty()) ? Double.valueOf(lineF.getRake()) : null;
        Double rakeUncertainty =
                (lineF.getErrorRake() != null
                        && !lineF.getErrorRake().isEmpty()) ? Double.valueOf(lineF.getErrorRake()) : null;

        if (strike != null || dip != null || rake != null) {
            NodalPlane nodalPlane = new NodalPlane();

            if (strike != null) {
                nodalPlane.setStrike(new RealQuantity(strike, strikeUncertainty));
            }

            if (dip != null) {
                nodalPlane.setDip(new RealQuantity(dip, dipUncertainty));
            }

            if (rake != null) {
                nodalPlane.setRake(new RealQuantity(rake, rakeUncertainty));
            }

            // Only using NodalPlane1 with values from Seisan
            fMech.getNodalPlanes().setNodalPlane1(nodalPlane);
        }
    }
}
