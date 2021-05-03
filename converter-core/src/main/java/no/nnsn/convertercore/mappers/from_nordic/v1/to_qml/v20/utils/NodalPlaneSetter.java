package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.NodalPlane;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineF;

public class NodalPlaneSetter {
    public static void setNodalPlane(FocalMechanism fMech, LineF lineF) {
        Double strike =
                (lineF.getStrike() != null && !lineF.getStrike().isEmpty()) ? Double.valueOf(lineF.getStrike()) : null;
        Double dip =
                (lineF.getDip() != null && !lineF.getDip().isEmpty()) ? Double.valueOf(lineF.getDip()) : null;
        Double rake =
                (lineF.getRake() != null && !lineF.getRake().isEmpty()) ? Double.valueOf(lineF.getRake()) : null;

        NodalPlane nodalPlane = new NodalPlane(
                new RealQuantity(strike),
                new RealQuantity(dip),
                new RealQuantity(rake)
        );

        // Only using NodalPlane1 with values from Seisan
        fMech.getNodalPlanes().setNodalPlane1(nodalPlane);
    }
}
