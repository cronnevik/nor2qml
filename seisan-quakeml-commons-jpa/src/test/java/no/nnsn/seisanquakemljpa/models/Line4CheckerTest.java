package no.nnsn.seisanquakemljpa.models;

import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers.Line4Checker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { ModelsAutoConfigure.class })
public class Line4CheckerTest {

    @Test
    public void industrumentAndComponentChecker() {
        String line4Text = " TXAR ??  P        0 7  0.98        0.10 0.33 142.6 9.75         1.6   2066 324 ";
        Line4 line4 = new Line4(line4Text, 1);

        System.out.println("Before:");
        System.out.println(line4.createLine());

        Line4Checker.checkLine4Values(line4);

        System.out.println("After");
        System.out.println(line4.createLine());
        assert true;
    }
}
