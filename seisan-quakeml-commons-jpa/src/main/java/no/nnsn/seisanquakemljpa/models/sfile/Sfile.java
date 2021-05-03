package no.nnsn.seisanquakemljpa.models.sfile;

import lombok.Data;
import lombok.NoArgsConstructor;

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
}
