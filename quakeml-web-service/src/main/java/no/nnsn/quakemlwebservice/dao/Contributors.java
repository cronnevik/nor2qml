package no.nnsn.quakemlwebservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "Contributors")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contributors {
    @XmlElement(name = "Contributor")
    List<String> contributors;
}
