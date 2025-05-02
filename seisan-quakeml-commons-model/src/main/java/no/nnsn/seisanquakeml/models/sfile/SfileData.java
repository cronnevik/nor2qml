package no.nnsn.seisanquakeml.models.sfile;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import no.nnsn.seisanquakeml.models.sfile.v1.SfileDataImpl;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.*;
import no.nnsn.seisanquakeml.models.sfile.v2.SfileDataDtoImpl;

import java.util.ArrayList;
import java.util.List;


@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = SfileDataImpl.class, name = "sfilenordic"),
        @JsonSubTypes.Type(value = SfileDataDtoImpl.class, name = "sfilnordic2") })
public abstract class SfileData {
    private List<Line1> line1s;
    private List<Line2> line2s;
    private List<Line3> line3s;
    private List<Line5> line5s;
    private List<Line6> line6s;
    private List<LineE> lineEs;
    private List<LineF> lineFs;
    private List<LineH> lineHs;
    private List<LineI> lineIs;
    private List<LineM1> lineM1s;
    private List<LineM2> lineM2s;
    private List<LineS> lineSs;
    private List<String> unmapped;

    public abstract List getLine4s();
    public abstract void addLine4(Object o);
    public abstract void clearLine4Lists();

    public void addLine1(Object o) {
        if (o instanceof Line1) {
            Line1 l1 = (Line1) o;
            if (line1s == null) {
                line1s = new ArrayList<>();
            }
            line1s.add(l1);
        }
    }

    public void addLine2(Object o) {
        if (o instanceof Line2) {
            Line2 l2 = (Line2) o;
            if (line2s == null) {
                line2s = new ArrayList<>();
            }
            line2s.add(l2);
        }
    }


    public void addLine3(Object o) {
        if (o instanceof Line3) {
            Line3 l3 = (Line3) o;
            if (line3s == null) {
                line3s = new ArrayList<>();
            }
            line3s.add(l3);
        }
    }

    public void addLine5(Object o) {
        if (o instanceof Line5) {
            Line5 l5 = (Line5) o;
            if (line5s == null) {
                line5s = new ArrayList<>();
            }
            line5s.add(l5);
        }
    }

    public void addLine6(Object o) {
        if (o instanceof Line6) {
            Line6 l6 = (Line6) o;
            if (line6s == null) {
                line6s = new ArrayList<>();
            }
            line6s.add(l6);
        }
    }

    public void addLineE(Object o) {
        if (o instanceof LineE) {
            LineE lE = (LineE) o;
            if (lineEs == null) {
                lineEs = new ArrayList<>();
            }
            lineEs.add(lE);
        }
    }

    public void addLineF(Object o) {
        if (o instanceof LineF) {
            LineF lF = (LineF) o;
            if (lineFs == null) {
                lineFs = new ArrayList<>();
            }
            lineFs.add(lF);
        }
    }

    public void addLineH(Object o) {
        if (o instanceof LineH) {
            LineH lH = (LineH) o;
            if (lineHs == null) {
                lineHs = new ArrayList<>();
            }
            lineHs.add(lH);
        }
    }

    public void addLineI(Object o) {
        if (o instanceof LineI) {
            LineI lI = (LineI) o;
            if (lineIs == null) {
                lineIs = new ArrayList<>();
            }
            lineIs.add(lI);
        }
    }

    public void addLineM1(Object o) {
        if (o instanceof LineM1) {
            LineM1 lM1 = (LineM1) o;
            if (lineM1s == null) {
                lineM1s = new ArrayList<>();
            }
            lineM1s.add(lM1);
        }
    }

    public void addLineM2(Object o) {
        if (o instanceof LineM2) {
            LineM2 lM2 = (LineM2) o;
            if (lineM2s == null) {
                lineM2s = new ArrayList<>();
            }
            lineM2s.add(lM2);
        }
    }

    public void addLineS(Object o) {
        if (o instanceof LineS) {
            LineS lS = (LineS) o;
            if (lineSs == null) {
                lineSs = new ArrayList<>();
            }
            lineSs.add(lS);
        }
    }

    public void addUnmapped(String line) {
        if (this.unmapped == null) {
            this.unmapped = new ArrayList<>();
        }
        this.unmapped.add(line);
    }

    public void clearUnmappedList() {this.unmapped = new ArrayList<>();}

    public void clearLineLists() {
        line1s = new ArrayList<>();
        line2s = new ArrayList<>();
        line3s = new ArrayList<>();
        line5s = new ArrayList<>();
        line6s = new ArrayList<>();
        lineEs = new ArrayList<>();
        lineFs = new ArrayList<>();
        lineIs = new ArrayList<>();
        lineM1s = new ArrayList<>();
        lineM2s = new ArrayList<>();
    }
}
