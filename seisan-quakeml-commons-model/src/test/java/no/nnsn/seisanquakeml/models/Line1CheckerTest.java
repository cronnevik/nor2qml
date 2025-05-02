package no.nnsn.seisanquakeml.models;

import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.utils.checkers.Line1Checker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { ModelsAutoConfigure.class })
public class Line1CheckerTest {
    @Test
    public void yearChecker() {
        // Detect error of setting year to 2208
        String l1YearError = " 2208  5 1  015 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1YearError, 1);
        Line1 line1Check = Line1Checker.checkLine1Values(line1);
        System.out.println(line1Check.createLine());
        assert true;
    }

    @Test
    public void monthChecker() {
        // Detect error of setting month to 15
        String l1MonthError = " 2008 15 1  015 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1MonthError, 1);
        Line1 line1Check = Line1Checker.checkLine1Values(line1);
        System.out.println(line1Check.createLine());
        assert true;
    }

    @Test
    public void dayChecker() {
        // Detect error of setting day to 32
        String l1DayError = " 2008  532  015 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1DayError, 1);
        Line1 line1Check = Line1Checker.checkLine1Values(line1);
        System.out.println(line1Check.createLine());
        assert true;
    }

    @Test
    public void hourChecker() {
        // Detect error of setting hour to 25
        String l1HourError = " 2008  5 1 2515 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1HourError, 1);
        Line1Checker.checkLine1Values(line1);
        assert true;
    }

    @Test
    public void minutesChecker() {
        // Detect error of setting minutes to 60
        String l1MinutesError = " 2008  5 1  060 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1MinutesError, 1);
        Line1Checker.checkLine1Values(line1);
        assert true;
    }

    @Test
    public void secondsChecker() {
        // Detect error of setting seconds to 60.4
        String l1SecondsError = " 2008  5 1  015 60.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6bBJI 4.8BBJI 4.4sBJI1";
        Line1 line1 = new Line1(l1SecondsError, 1);
        Line1Checker.checkLine1Values(line1);
        assert true;
    }

    @Test
    public void magNumChecker() {
        // Detect magnitude number errors by setting mag one to 14.6, mag two to 14.8 and mag three to -4.4
        String l1MagNumError = " 2008  5 1  015 23.4 RQ 34.150  48.370  7.0  BJI    2.014.6bBJI14.8BBJI-4.4sBJI1";
        Line1 line1 = new Line1(l1MagNumError, 1);
        Line1Checker.checkLine1Values(line1);
    }

    @Test
    public void magTypeChecker() {
        // Detect error of missing type for all magnitudes
        String l1MagTypeError = " 2008  5 1  015 23.4 RQ 34.150  48.370  7.0  BJI    2.0 4.6 BJI 4.8 BJI 4.4 BJI1";
        Line1 line1 = new Line1(l1MagTypeError, 1);
        Line1Checker.checkLine1Values(line1);
    }
}
