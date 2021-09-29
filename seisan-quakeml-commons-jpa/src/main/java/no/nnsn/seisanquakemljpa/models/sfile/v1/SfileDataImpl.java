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
    private List<Line4> line4s;

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
    public void clearLine4Lists() {
        line4s = new ArrayList<>();
    }

}
