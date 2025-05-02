package no.nnsn.seisanquakeml.models.quakeml.v20.macroseismic.v09;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicParameters {

    @XmlAttribute
    private String publicID;

    private List<MacroseismicEvent> macroseismicEvent;
    private List<MacroseismicMagnitude> macroseismicMagnitude;
    private List<MacroseismicOrigin> macroseismicOrigin;
    private List<MDP> mdp;
    private List<MDPSet> mdpSet;
    private List<Place> place;
}
