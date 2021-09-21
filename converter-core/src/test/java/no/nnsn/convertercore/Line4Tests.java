package no.nnsn.convertercore;

import no.nnsn.convertercore.helpers.Line4Entities;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.NordicToArrivalMapper;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.NordicDtoToArrivalMapper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.Line4DtoMapper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.Line4Mapper;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyIdType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers.Line4Checker;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConverterCoreConfiguration.class)
public class Line4Tests {
    @Autowired
    NordicMapper nordicMapper;

    @Autowired
    QmlMapper qmlMapper;

    @Test
    public void checkConversionOfLongPhaseNames() {
        String line1Text =  " 2016  3 1 0926 46.9 D -33.667-178.616 33.0F*NAO  1                     5.6BNAO1";
        String line4Text1 = " STEI HZ EPKPdf    946 16.56                               5   -0.751016046 352 ";
        String line4Text2 = " FAUS HZ EpPKPbc   946 19.26                             170   -1.101016104 351 ";
        String line4Text3 = " ARA0 HZ  IVMs_BB 1346 11.22      10.5e4 16.5                          9674 341 ";

        // Set Line1
        Line1 line1Org = new Line1(line1Text, 1);
        List<Line1> line1s = new ArrayList<>();
        line1s.add(line1Org);

        // Set Line4s
        Line4 l4org1 = new Line4(line4Text1, 4);
        Line4 l4org2 = new Line4(line4Text2, 5);

        // Map Phase objects for first line 4
        Pick pick1 = (Pick) qmlMapper.mapPick(l4org1, null, line1s);
        Amplitude amp1 = (Amplitude) qmlMapper.mapAmplitude(l4org1, null, pick1, line1s);
        Arrival arr1 = (Arrival) qmlMapper.mapArrival(l4org1, null, pick1, line1s);

        // Map Phase objects for second line 4
        Pick pick2 = (Pick) qmlMapper.mapPick(l4org2, null, line1s);
        Amplitude amp2 = (Amplitude) qmlMapper.mapAmplitude(l4org2, null, pick2, line1s);
        Arrival arr2 = (Arrival) qmlMapper.mapArrival(l4org2, null, pick2, line1s);

        // Construct lists of the phase objects to be fed to the nordic line4 mapper and Origin object
        List<Pick> picks = new ArrayList<>();
        picks.add(pick1);
        picks.add(pick2);
        List<Amplitude> amplitudes = new ArrayList<>();
        amplitudes.add(amp1);
        amplitudes.add(amp2);
        List<Arrival> arrivals = new ArrayList<>();
        arrivals.add(arr1);
        arrivals.add(arr2);

        // Map origin obj
        Origin origin = (Origin) qmlMapper.mapOrigin(line1Org, null, "Report");
        origin.setArrival(arrivals);

        // Create list of origins to be fed to the nordic line4 mapper
        List<Origin> origins = new ArrayList<>();
        origins.add(origin);

        // Map back to Nordic Line1
        final List<Line4> line4s = (List<Line4>)(Object) nordicMapper.mapLine4s(NordicFormatVersion.VERSION1, picks, amplitudes, origins);
        Line4 l4Conv1 = Line4Checker.checkLine4Values(line4s.get(0));
        Line4 l4Conv2 = Line4Checker.checkLine4Values(line4s.get(1));

        System.out.println("------------------------------------------------------------------------");
        System.out.println("Original:");
        System.out.println(line4Text1);
        System.out.println(line4Text2);
        System.out.println("Converted:");
        System.out.println(l4Conv1.createLine());
        System.out.println(l4Conv2.createLine());
        System.out.println("------------------------------------------------------------------------");

    }

    @Test
    public void checkWeightConversion() {
        String line1Text = " 2018 12 6 1356 45.4 LP 67.405  23.520  0.0F BER 24 1.1 1.6LBER         1.6LHEL1";
        String line4Text = " KLF  BZ  PG      1356 49.80                              90    0.0210 26.9 135 ";

        Arrival arrival = NordicToArrivalMapper.INSTANCE.mapLine4ToArrival(new Line4(line4Text, 2), new Line1(line1Text, 1));
        arrival.getComment().forEach(comment -> {
            if (comment.getId().equals(PropertyIdType.PROPERTY_WEIGHT))
                assertEquals(comment.getText(), "1.0");
        });
        Line4 line4 = Line4Mapper.INSTANCE.mapLine4(new Line4Entities(null, null, arrival, false));
        assertEquals(line4.getWeight(), "10");

        String line1DtoText = " 1996  6 7 1325 29.2 L  59.846   5.130 12.0F TES 12 .60 1.9LTES 2.2CTES 2.0LNAO1";
        String line4DtoText = " EGD  S Z      IP       4 1325 35.950      C       BER jh 120.0-1.131047.70   6 ";
        Arrival arrivalDto = NordicDtoToArrivalMapper.INSTANCE.mapLine4DtoToArrival(
                new Line4Dto(line4DtoText, 2), new Line1(line1DtoText, 1));
        arrivalDto.getComment().forEach(comment -> {
            if (comment.getId().equals(PropertyIdType.PROPERTY_WEIGHT))
                assertEquals(comment.getText(), "1.0");
        });

        Line4Dto line4Dto = Line4DtoMapper.INSTANCE.mapLine4Dto(new Line4Entities(null, null, arrivalDto, false));
        assertEquals(line4Dto.getWeight(), "10");
    }
}
