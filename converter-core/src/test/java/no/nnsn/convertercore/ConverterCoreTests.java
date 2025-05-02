package no.nnsn.convertercore;

import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.LineM2Mapper;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.bedtypes.Tensor;
import no.nnsn.seisanquakeml.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineE;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.LineM2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class ConverterCoreTests {

    @Autowired
    NordicMapper nordicMapper;

    @Autowired
    QmlMapper qmlMapper;

    @Test
    public void mapOriginAndMagnitudes() {
        String line1Text = " 1996  6 2 0252  7.5 D   9.943 -42.242 10.0F BER 15 1.5 6.3bBER         6.1BPDE1";
        String lineEText = " GAP=343        3.49      39.5   139.6  0.0 -0.2963E+04 -0.3008E+03 -0.4425E+02E";

        // Set Line 1
        Line1 line1 = new Line1();
        line1.setLineInfo(line1Text, 1);
        line1.setValues();

        // Set Line E
        LineE lineE = new LineE();
        lineE.setLineInfo(lineEText, 2);
        lineE.setValues();
        List<LineE> lineES = new ArrayList<>();
        lineES.add(lineE);

        // Map Origin
        Object orgObj = qmlMapper.mapOrigin(line1,lineES, "report");
        Origin origin = null;
        if (orgObj instanceof Origin) {
            origin = (Origin) orgObj;
        }
        List<Object> mags = qmlMapper.mapLine1Magnitudes(line1, origin);
        System.out.println("mag size: " + mags.size());

        // Make Event object and attach origin
        List<Origin> origins = new ArrayList<>();
        origins.add(origin);
        Event ev = new Event();
        ev.setOrigin(origins);
        ev.setMagnitude((List<Magnitude>)(Object)mags);

        // Map back to Nordic Line1 and Line E
        List<Line1> backLine1s = (List<Line1>)(Object) nordicMapper.mapLine1s(ev);
        List<LineE> backLineEs = (List<LineE>)(Object) nordicMapper.mapLineEs(origins);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Original:");
        System.out.println(line1Text);
        System.out.println("Converted:");
        System.out.println(backLine1s.get(0).createLine());
        System.out.println("------------------------------------------------------------------------");
        assert origin != null;
    }

    @Test
    public void reportErrorLine1() {
        String line1Text = " 1996 0827 1436 39.1 D -6.933-12.724   10.0  PDE        5.2bPDE                1";
        String lineEText = " GAP=357       52.33     999.9   999.9  0.0 -0.1028E+09  0.5426E+02  0.1319E+02E";

        // Set Line 1
        Line1 line1 = new Line1();
        line1.setLineInfo(line1Text, 1);
        line1.setValues();

        // Set Line E
        LineE lineE = new LineE();
        lineE.setLineInfo(lineEText, 2);
        lineE.setValues();
        List<LineE> lineES = new ArrayList<>();
        lineES.add(lineE);

        // Map Origin
        Object obj = qmlMapper.mapOrigin(line1,lineES, "report");
        Origin origin = null;
        if (obj instanceof Origin) {
            origin = (Origin) obj;
        } else if (obj instanceof IgnoredLineError) {
            System.out.println("erros obj received");
            IgnoredLineError error = (IgnoredLineError) obj;
            System.out.println("row num: " + error.getRowNumber());
            System.out.println("msg: " + error.getMessage());
        }
        assert obj != null;
    }

    @Test
    public void mapOriginWithoutLatLon() {
        String line1Text = " 2014 12 9 1428      LE                 0.0  TIT                               1";

        // Set Line 1
        Line1 line1 = new Line1();
        line1.setLineInfo(line1Text, 1);
        line1.setValues();

        // Map Origin
        Object orgObj = qmlMapper.mapOrigin(line1, null, "report");
        Origin origin = null;
        if (orgObj instanceof Origin) {
            origin = (Origin) orgObj;
        } else if (orgObj instanceof IgnoredLineError) {
        System.out.println("erros obj received");
        IgnoredLineError error = (IgnoredLineError) orgObj;
        System.out.println("row num: " + error.getRowNumber());
        System.out.println("msg: " + error.getMessage());
    }

        assert origin != null;

    }

    @Test
    public void mapPhaseEntitiesWithLongPhaseName() {
        String line1Text = " 1996  6 2 0252  7.5 D   9.943 -42.242 10.0F BER 15 1.5 6.3bBER         6.1BPDE1";
        String line4Text = " BLS5 SZ3EPKPPKPPP 3 2 21.65                              21   -1.7510 6826  26 ";
        String line4Texts = " HSPB HZ  IAML     055  5.01         3.9 0.96                          66.2 247 ";
        String line4Textt = " SPA0 HZ EP        054 49.20                              91   -1.0410  111 341 ";

        // Set Line 1
        Line1 line1 = new Line1();
        line1.setLineInfo(line1Text, 1);
        line1.setValues();
        List<Line1> line1s = new ArrayList<>();
        line1s.add(line1);

        // Set Line 4
        Line4 line4 = new Line4();
        line4.setLineInfo(line4Text, 1);
        line4.setValues();

        // Map Phase objects
        Pick pick = (Pick) qmlMapper.mapPick(line4, null, line1s);
        Amplitude amplitude = (Amplitude) qmlMapper.mapAmplitude(line4, null, pick, line1s);
        Arrival arrival = (Arrival) qmlMapper.mapArrival(line4, null, pick, line1s);
        System.out.println("arr weight: " + arrival.getTimeWeight());
        System.out.println("arr phaseID: " + arrival.getPhase());

        // Make List of phase objects
        List<Pick> picks = new ArrayList<>();
        picks.add(pick);
        List<Amplitude> amplitudes = new ArrayList<>();
        amplitudes.add(amplitude);
        List<Arrival> arrivals = new ArrayList<>();
        arrivals.add(arrival);
        Origin origin = new Origin();
        origin.setArrival(arrivals);
        List<Origin> origins = new ArrayList<>();
        origins.add(origin);

        // Map back to Nordic Line4
        List<Line4> backLine4s = (List<Line4>)(Object) nordicMapper.mapLine4s(NordicFormatVersion.VERSION1, picks, amplitudes, origins);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Original line 4:");
        System.out.println(line4Text);
        System.out.println("Converted line 4:");
        System.out.println(backLine4s.get(0).getPhaseID().length());
        System.out.println(backLine4s.get(0).createLine());
        System.out.println("------------------------------------------------------------------------");

        assert pick != null;
    }

    @Test
    public void mapMomentTensorToLineM2() {
        MomentTensor mt = new MomentTensor();
        mt.setPublicID("smi:ISC/moment_tensor;fmid=600202715");

        RealQuantity scalarMoment = new RealQuantity();
        scalarMoment.setValue(2.399e+17);
        mt.setScalarMoment(scalarMoment);

        Tensor tensor = new Tensor();
            RealQuantity mrr = new RealQuantity();
            mrr.setValue(-2.530e+16);
            mrr.setUncertainty(4.700e+15);
            tensor.setMrr(mrr);

            RealQuantity mtt = new RealQuantity();
            mtt.setValue(2.470e+17);
            mtt.setUncertainty(4.400e+15);
            tensor.setMtt(mtt);

            RealQuantity mpp = new RealQuantity();
            mpp.setValue(-2.220e+17);
            mpp.setUncertainty(4.000e+15);
            tensor.setMpp(mpp);

            RealQuantity mrt = new RealQuantity();
            mrt.setValue(-1.800e+16);
            mrt.setUncertainty(5.800e+15);
            tensor.setMrt(mrt);

            RealQuantity mrp = new RealQuantity();
            mrp.setValue(3.540e+16);
            mrp.setUncertainty(6.000e+15);
            tensor.setMrp(mrp);

            RealQuantity mtp = new RealQuantity();
            mtp.setValue(-2.930e+16);
            mtp.setUncertainty(3.300e+15);
            tensor.setMtp(mtp);
        mt.setTensor(tensor);

        LineM2 lineM2 = LineM2Mapper.INSTANCE.mapLineM2(mt);
        System.out.println(lineM2.createLine());
        assert lineM2 != null;
    }

}
