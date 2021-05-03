package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MtHelper {
    MtInfo scalar;
    MtInfo mrr;
    MtInfo mtt;
    MtInfo mpp;
    MtInfo mrt;
    MtInfo mrp;
    MtInfo mtp;
    int exponent;
    List<Integer> mtExponents;

    public MtHelper(String scalar, String mrr, String mtt, String mpp, String mrt, String mrp, String mtp) {
        mtExponents = new ArrayList<>();

        int scalarEx = checkExponent(scalar);
        this.scalar = new MtInfo(scalar, scalarEx);
        mtExponents.add(scalarEx);

        int mrrEx = checkExponent(mrr);
        this.mrr = new MtInfo(mrr, mrrEx);
        mtExponents.add(mrrEx);

        int mttEx = checkExponent(mtt);
        this.mtt = new MtInfo(mtt, mttEx);
        mtExponents.add(mttEx);

        int mppEx = checkExponent(mpp);
        this.mpp = new MtInfo(mpp, mppEx);
        mtExponents.add(mppEx);

        int mrtEx = checkExponent(mrt);
        this.mrt = new MtInfo(mrt, mrtEx);
        mtExponents.add(mrtEx);

        int mrpEx = checkExponent(mrp);
        this.mrp = new MtInfo(mrp, mrpEx);
        mtExponents.add(mrpEx);

        int mtpEx = checkExponent(mtp);
        this.mtp = new MtInfo(mtp, mtpEx);
        mtExponents.add(mtpEx);

        this.exponent = 0;

    }

    public int getExponent() {
        for (int ex: mtExponents) {
            if (ex > this.exponent ) {
                this.exponent = ex;
            }
        }
        return this.exponent;
    }

    private static int checkExponent(String value) {
        if (value != null) {
            String[] split = value.split("E");
            if (split[1] !=  null) {
                String exp = split[1];
                return Integer.parseInt(exp);
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }
}
