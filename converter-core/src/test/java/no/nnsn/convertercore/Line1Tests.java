package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.*;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class Line1Tests {
    @Autowired
    NordicMapper nordicMapper;

    @Autowired
    QmlMapper qmlMapper;

    @Autowired
    NordicToQml nordicToQml;

    @Autowired
    QmlToSfile qmlToSfile;

    @Test
    public void checkUnitConversions() {
        String line1Text = " 2002  217 1304 60.0 D  35.545  48.362 33.0F BER 12 0.5 4.7sBER 5.7bBER 5.6BPDE1";

        // Set Line 1
        Line1 line1Original = new Line1(line1Text, 1);

        // Map Origin
        Object orgObj = qmlMapper.mapOrigin(line1Original,null, "report");
        Origin origin = null;
        if (orgObj instanceof Origin) {
            origin = (Origin) orgObj;
        }

        List<Origin> origins = new ArrayList<>();
        origins.add(origin);
        Event ev = new Event();
        ev.setOrigin(origins);

        // Map back to Nordic Line1
        List<Line1> backLine1s = (List<Line1>)(Object) nordicMapper.mapLine1s(ev);
        Line1 line1Back = backLine1s.get(0);

        // Depth
        assert origin.getDepth().getValue() == (Double.parseDouble(line1Original.getDepth()) * 1000); // km --> m
        assert Double.parseDouble(line1Back.getDepth()) == (origin.getDepth().getValue() / 1000); // m --> km

    }

    @Test
    public void checkPreferredOriginAsFirstLine1() throws Exception {
        String line1Text = " 2002  217 1304 60.0 D  35.545  48.362 33.0F BER 12 0.5 4.7sBER 5.7bBER 5.6BPDE1";
        String line2Text = " 2002  217 1304 60.0 D  35.545  48.362 33.0F BER                                ";

        List<Sfile> sfiles = new ArrayList<>();

        // Set Line 1s
        Line1 firstL1 = new Line1(line1Text, 1);
        Line1 secondL1 = new Line1(line2Text, 2);

        // Construct Sfile object and add to sfiles list
        Sfile sfile = new Sfile();
        SfileData sfileData = new SfileDataImpl();
        sfileData.addLine1(firstL1);
        sfileData.addLine1(secondL1);
        sfile.setData(sfileData);
        sfiles.add(sfile);

        // Convert to QuakeML
        ConverterOptions options = new ConverterOptions(
                "report",
                CallerType.CONVERTER,
                null,
                "",
                "earthquake",
                "suspected"
        );
        EventOverview eventOverview = nordicToQml.convertToQuakeml(sfiles, options);
        List<Event> events = eventOverview.getEvents();
        Event event = events.get(0);

        assert event.getOrigin().size() == 2;
        assert event.getPreferredOriginID() != null;
        assert event.getOrigin().get(0).getPublicID().equals(event.getPreferredOriginID());

        // Swap Origin list to check that preferredOrigin is set as first line type 1
        // (in this case, the original fist line 1)
        List<Origin> tempOrigin = event.getOrigin();
        Collections.swap(tempOrigin, 0, 1);
        event.setOrigin(tempOrigin);

        SfileOverview converter = qmlToSfile.convertToNordic(events, CallerType.CONVERTER, NordicFormatVersion.VERSION1);
        String sfiletext = converter.getSfiletext();
        System.out.println(sfiletext);

    }

    @Test
    public void checkNordicSixitySecondsBug() {
        // Check conversion handling of s-files having 60.0 seconds in line type 1
        String line1Text = " 2002  217 1304 60.0 D  35.545  48.362 33.0F BER 12 0.5 4.7sBER 5.7bBER 5.6BPDE1";

        // Set Line 1
        Line1 line1Original = new Line1(line1Text, 1);

        // Map Origin
        Object orgObj = qmlMapper.mapOrigin(line1Original,null, "report");
        Origin origin = null;
        if (orgObj instanceof Origin) {
            origin = (Origin) orgObj;
        }

        // Make Event object and attach origin. Excludes magnitudes in this test
        List<Origin> origins = new ArrayList<>();
        origins.add(origin);
        Event ev = new Event();
        ev.setOrigin(origins);

        // Map back to Nordic Line1
        List<Line1> backLine1s = (List<Line1>)(Object) nordicMapper.mapLine1s(ev);
        Line1 line1Back = backLine1s.get(0);

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Original:");
        System.out.println(line1Text);
        System.out.println("Converted:");
        System.out.println(line1Back.createLine());
        System.out.println("------------------------------------------------------------------------");

        assert line1Back.getSeconds().equals("0.0");
        assert line1Back.getMinutes().equals("5");
    }
}
