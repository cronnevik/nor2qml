package no.nnsn.seisanquakemljpa.models.sfile;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;
import no.nnsn.seisanquakemljpa.models.sfile.v2.SfileDataDtoImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Sfile{
    @XmlTransient
    List<String> sfileLines;
    private String filename;
    private SfileData data;
    private String eventParamsId;
    private SfileVersionLine7 version;

    public Sfile(String filename, Object data, String eventParamsId, SfileVersionLine7 version) {
        this.filename = filename;
        this.eventParamsId = eventParamsId;
        this.version = version;
        if (version.equals(SfileVersionLine7.VERSION1)) {
            this.data = (SfileDataImpl) data;
        } else if (version.equals(SfileVersionLine7.VERSION2)) {
            this.data = (SfileDataDtoImpl) data;
        }
    }
}
