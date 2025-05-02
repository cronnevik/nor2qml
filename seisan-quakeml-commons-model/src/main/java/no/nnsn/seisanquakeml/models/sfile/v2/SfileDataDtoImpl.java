package no.nnsn.seisanquakeml.models.sfile.v2;

import lombok.NoArgsConstructor;
import lombok.Setter;
import no.nnsn.seisanquakeml.models.sfile.SfileData;
import no.nnsn.seisanquakeml.models.sfile.v2.lines.Line4Dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;

@Setter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SfileDataDtoImpl extends SfileData {
    private List<Line4Dto> line4s;

    @Override
    public List getLine4s() {
        return this.line4s;
    }

    @Override
    public void addLine4(Object o) {
        if (o instanceof Line4Dto) {
            Line4Dto l4 = (Line4Dto) o;
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
