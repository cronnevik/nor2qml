package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.utils.AmplitudeUnitConverter;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.NodalPlane;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.NodalPlanes;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineF;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class MappingEntityTests {
    @Autowired
    NordicToQml nordicToQml;

    @Autowired
    QmlToSfile qmlToSfile;

    @Test
    public void checkAmplitudeMappingNorOne() {

    }

    @Test
    public void checkAmplitudeMappingNorTwo() {
        String path = "src/test/resources/sfile/12-1547-47L.S201906";

        File sFile = new File(path);
        List<Sfile> sfiles = null;

        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            List<Line4Dto> line4s = sfiles.get(0).getData().getLine4s();

            // Nordic ==> QuakeML conversion
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);
            List<Event> eventList = events.getEvents();

            // QuakeMl ==> Nordic conversion
            SfileOverview sfileOverview = qmlToSfile.convertToNordic(eventList, CallerType.CONVERTER, NordicFormatVersion.VERSION2);
            List<Sfile> sfsBack = sfileOverview.getSfiles();

            // Only 1 event in list
            Event e = eventList.get(0);
            List<Amplitude> amplitudeList = e.getAmplitude();

            // Sfile and type line 4s back from QuakeMl ==> Nordic conversion
            Sfile sfileBack = sfsBack.get(0);
            List<Line4Dto> l4sBack = sfileBack.getData().getLine4s();

            // Check that all Line4s were read
            assertEquals(98, line4s.size());

            // Check that all Amplitude lines are converted (Phase ID starts with "A" or "IA" or "IV)
            assertEquals(30, amplitudeList.size());

            List<Line4Dto> amplitudeLines = new ArrayList<>();
            for (Line4Dto l4: line4s) {
                if (l4.getPhaseID().equals("IAML")) {
                    amplitudeLines.add(l4);
                }
            }

            System.out.println(amplitudeLines.size());

            List<Line4Dto> amplitudeLinesBack = new ArrayList<>();
            for (Line4Dto l4: l4sBack) {
                if (l4.getPhaseID().equals("IAML")) {
                    amplitudeLinesBack.add(l4);
                }
            }

            System.out.println(amplitudeLinesBack.size());

            System.out.println(amplitudeList.size());
            for (int i = 0; i < amplitudeList.size(); i++) {
                Amplitude amplitude = amplitudeList.get(i);
                String amplitudeValue = AmplitudeUnitConverter.fromMToNm(amplitude.getGenericAmplitude().getValue().toString());
                //assertEquals(amplitudeValue, amplitudeLines.get(i).getParameterOne());
                //assertEquals(amplitudeLines.get(i).getParameterOne(), amplitudeLinesBack.get(i).getParameterOne());
                System.out.println(amplitudeValue + " " + amplitudeLines.get(i).getParameterOne() + " " +  amplitudeLinesBack.get(i).getParameterOne());
            }


        } catch (Exception e) {

        }
    }

    @Test
    public void checkFocalMechanismMapping() {
        String path = "src/test/resources/sfile/12-1547-47L.S201906";

        File sFile = new File(path);
        List<Sfile> sfiles = null;

        try {
            InputStream stream = new FileInputStream(sFile);
            sfiles = nordicToQml.readSfile(stream, sFile.getName(), CallerType.CONVERTER);
            List<LineF> lfs = sfiles.get(0).getData().getLineFs();
            LineF lf1 = lfs.get(0);
            LineF lf2 = lfs.get(1);

            // Nordic ==> QuakeML conversion
            ConverterOptions options = new ConverterOptions(
                    "report",
                    CallerType.CONVERTER,
                    null,
                    "",
                    "earthquake",
                    "suspected"
            );
            EventOverview events = nordicToQml.convertToQuakeml(sfiles, options);
            List<Event> eventList = events.getEvents();

            // QuakeMl ==> Nordic conversion
            SfileOverview sfileOverview = qmlToSfile.convertToNordic(eventList, CallerType.CONVERTER, NordicFormatVersion.VERSION2);
            List<Sfile> sfsBack = sfileOverview.getSfiles();

            // Only 1 event in list
            Event e = eventList.get(0);
            List<FocalMechanism> focalMechanism = e.getFocalMechanism();

            // Sfile and type line Fs back from QuakeMl ==> Nordic conversion
            Sfile sfileBack = sfsBack.get(0);
            List<LineF> lfsBack = sfileBack.getData().getLineFs();

            // Check that all Fochamechanism lines are converted
            assertEquals(2, focalMechanism.size());
            assertEquals(2, lfsBack.size());

            FocalMechanism f1 = focalMechanism.get(0);
            FocalMechanism f2 = focalMechanism.get(1);
            LineF lfBack1 = lfsBack.get(0);
            LineF lfBack2 = lfsBack.get(1);

            // Check that ID exists
            assertFalse(f1.getPublicID().isEmpty());
            assertFalse(f2.getPublicID().isEmpty());

            // Check nodalPlanes - Nordic ==> QuakeML
            NodalPlanes nodalPlanes = f1.getNodalPlanes();
            assertNotNull(nodalPlanes);
            NodalPlane nodalPlane1 = nodalPlanes.getNodalPlane1();
            assertNotNull(nodalPlane1);

            // Check Strike - Nordic ==> QuakeML
            RealQuantity strike = nodalPlane1.getStrike();
            assertNotNull(strike);
            assertEquals(strike.getValue(), Double.valueOf(lf1.getStrike()));
            assertEquals(strike.getUncertainty(), Double.valueOf(lf1.getErrorStrike()));
            // Check Strike - QuakeML ==> Nordic against original Nordic value
            assertEquals(Double.valueOf(lfBack1.getStrike()), Double.valueOf(lf1.getStrike()));
            assertEquals(Double.valueOf(lfBack1.getErrorStrike()), Double.valueOf(lf1.getErrorStrike()));

            // Check Dip - Nordic ==> QuakeML
            RealQuantity dip = nodalPlane1.getDip();
            assertNotNull(dip);
            assertEquals(dip.getValue(), Double.valueOf(lf1.getDip()));
            assertEquals(dip.getUncertainty(), Double.valueOf(lf1.getErrorDip()));
            // Check Dip - QuakeML ==> Nordic against original Nordic value
            assertEquals(Double.valueOf(lfBack1.getDip()), Double.valueOf(lf1.getDip()));
            assertEquals(Double.valueOf(lfBack1.getErrorDip()), Double.valueOf(lf1.getErrorDip()));

            // Check Rake - Nordic ==> QuakeML
            RealQuantity rake = nodalPlane1.getRake();
            assertNotNull(rake);
            assertEquals(rake.getValue(), Double.valueOf(lf1.getRake()));
            assertEquals(rake.getUncertainty(), Double.valueOf(lf1.getErrorRake()));
            // Check Rake - QuakeML ==> Nordic against original Nordic value
            assertEquals(Double.valueOf(lfBack1.getRake()), Double.valueOf(lf1.getRake()));
            assertEquals(Double.valueOf(lfBack1.getErrorRake()), Double.valueOf(lf1.getErrorRake()));

            // Check program used - Nordic ==> QuakeML
            assertEquals(f1.getMethodID(), lf1.getProgramUsed());
            // Check program used - QuakeML ==> Nordic against original Nordic value
            assertEquals(lfBack1.getProgramUsed(), lf1.getProgramUsed());

            // Check Station Distribution Ratio - Nordic ==> QuakeML
            assertEquals(f1.getStationDistributionRatio(), Double.valueOf(lf1.getStationDistRatio()));
            // Check Station Distribution Ratio - QuakeML ==> Nordic against original Nordic value
            assertEquals(lfBack1.getStationDistRatio(), lf1.getStationDistRatio());

            // Check Agency code - Nordic ==> QuakeML
            assertEquals(f1.getCreationInfo().getAgencyID(), lf1.getAgencyCode());
            // Check Agency code - QuakeML ==> Nordic against original Nordic value
            assertEquals(lfBack1.getAgencyCode(), lf1.getAgencyCode());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
