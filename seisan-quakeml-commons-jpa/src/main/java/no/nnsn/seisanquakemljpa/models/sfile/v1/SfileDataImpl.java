package no.nnsn.seisanquakemljpa.models.sfile.v1;

import lombok.NoArgsConstructor;
import lombok.Setter;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SfileDataImpl extends SfileData {
    private List<Line1> line1s;
    private List<Line2> line2s;
    private List<Line3> line3s;
    private List<Line4> line4s;
    private List<Line5> line5s;
    private List<Line6> line6s;
    private List<LineE> lineEs;
    private List<LineF> lineFs;
    private List<LineH> lineHs;
    private List<LineI> lineIs;
    private List<LineM1> lineM1s;
    private List<LineM2> lineM2s;
    private List<LineS> lineSs;

    @Override
    public List getLine1s() {
        return this.line1s;
    }

    @Override
    public void addLine1(Object o) {
        if (o instanceof Line1) {
            Line1 l1 = (Line1) o;
            if (line1s == null) {
                line1s = new ArrayList<>();
            }
            line1s.add(l1);
        }
    }

    @Override
    public List getLine2s() {
        return this.line2s;
    }

    @Override
    public void addLine2(Object o) {
        if (o instanceof Line2) {
            Line2 l2 = (Line2) o;
            if (line2s == null) {
                line2s = new ArrayList<>();
            }
            line2s.add(l2);
        }
    }

    @Override
    public List getLine3s() {
        return this.line3s;
    }

    @Override
    public void addLine3(Object o) {
        if (o instanceof Line3) {
            Line3 l3 = (Line3) o;
            if (line3s == null) {
                line3s = new ArrayList<>();
            }
            line3s.add(l3);
        }
    }

    @Override
    public List getLine4s() {
        return this.line4s;
    }

    @Override
    public void addLine4(Object o) {
        if (o instanceof Line4) {
            Line4 l4 = (Line4) o;
            if (line4s == null) {
                line4s = new ArrayList<>();
            }
            line4s.add(l4);
        }
    }

    @Override
    public List getLine5s() {
        return this.line5s;
    }

    @Override
    public void addLine5(Object o) {
        if (o instanceof Line5) {
            Line5 l5 = (Line5) o;
            if (line5s == null) {
                line5s = new ArrayList<>();
            }
            line5s.add(l5);
        }
    }

    @Override
    public List getLine6s() {
        return this.line6s;
    }

    @Override
    public void addLine6(Object o) {
        if (o instanceof Line6) {
            Line6 l6 = (Line6) o;
            if (line6s == null) {
                line6s = new ArrayList<>();
            }
            line6s.add(l6);
        }
    }

    @Override
    public List getLineEs() {
        return this.lineEs;
    }

    @Override
    public void addLineE(Object o) {
        if (o instanceof LineE) {
            LineE lE = (LineE) o;
            if (lineEs == null) {
                lineEs = new ArrayList<>();
            }
            lineEs.add(lE);
        }
    }

    @Override
    public List getLineFs() {
        return this.lineFs;
    }

    @Override
    public void addLineF(Object o) {
        if (o instanceof LineF) {
            LineF lF = (LineF) o;
            if (lineFs == null) {
                lineFs = new ArrayList<>();
            }
            lineFs.add(lF);
        }
    }

    @Override
    public List getLineHs() {
        return this.lineHs;
    }

    @Override
    public void addLineH(Object o) {
        if (o instanceof LineH) {
            LineH lH = (LineH) o;
            if (lineHs == null) {
                lineHs = new ArrayList<>();
            }
            lineHs.add(lH);
        }
    }

    @Override
    public List getLineIs() {
        return this.lineIs;
    }

    @Override
    public void addLineI(Object o) {
        if (o instanceof LineI) {
            LineI lI = (LineI) o;
            if (lineIs == null) {
                lineIs = new ArrayList<>();
            }
            lineIs.add(lI);
        }
    }

    @Override
    public List getLineM1s() {
        return this.lineM1s;
    }

    @Override
    public void addLineM1(Object o) {
        if (o instanceof LineM1) {
            LineM1 lM1 = (LineM1) o;
            if (lineM1s == null) {
                lineM1s = new ArrayList<>();
            }
            lineM1s.add(lM1);
        }
    }

    @Override
    public List getLineM2s() {
        return this.lineM2s;
    }

    @Override
    public void addLineM2(Object o) {
        if (o instanceof LineM2) {
            LineM2 lM2 = (LineM2) o;
            if (lineM2s == null) {
                lineM2s = new ArrayList<>();
            }
            lineM2s.add(lM2);
        }
    }

    @Override
    public List getLineSs() {
        return this.lineSs;
    }

    @Override
    public void addLineS(Object o) {
        if (o instanceof LineS) {
            LineS lS = (LineS) o;
            if (lineSs == null) {
                lineSs = new ArrayList<>();
            }
            lineSs.add(lS);
        }
    }

    @Override
    public void clearLineLists() {
        line1s = new ArrayList<>();
        line2s = new ArrayList<>();
        line3s = new ArrayList<>();
        line4s = new ArrayList<>();
        line5s = new ArrayList<>();
        line6s = new ArrayList<>();
        lineEs = new ArrayList<>();
        lineFs = new ArrayList<>();
        lineIs = new ArrayList<>();
        lineM1s = new ArrayList<>();
        lineM2s = new ArrayList<>();
    }

}
