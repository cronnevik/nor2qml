package no.nnsn.seisanquakemljpa.models.quakeml.v20.macroseismic.v09;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.util.List;

@Data
@Entity
@Table(name = "qmlV20_msParameters")
@XmlAccessorType(XmlAccessType.FIELD)
public class MacroseismicParameters {

    @Id
    @XmlAttribute
    private String publicID;


    /*
     *   Relations
     */

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MacroseismicEvent> macroseismicEvent;

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MacroseismicMagnitude> macroseismicMagnitude;

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MacroseismicOrigin> macroseismicOrigin;

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MDP> mdp;

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<MDPSet> mdpSet;

    @OneToMany(mappedBy = "macroseismicParameters", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<Place> place;


    /*
     *   Custom setter methods for relations
     */

    public void setMacroseismicEvent(List<MacroseismicEvent> macroseismicEvents) {
        this.macroseismicEvent = macroseismicEvents;
        // Hibernate relationship specific
        if (macroseismicEvents != null) {
            for (MacroseismicEvent msEv: macroseismicEvents) {
                msEv.setMacroseismicParameters(this);
            }
        }
    }

    public void setMacroseismicMagnitude(List<MacroseismicMagnitude> macroseismicMagnitudes) {
        this.macroseismicMagnitude = macroseismicMagnitudes;
        // Hibernate relationship specific
        if (macroseismicMagnitudes != null) {
            for (MacroseismicMagnitude msMag: macroseismicMagnitudes) {
                msMag.setMacroseismicParameters(this);
            }
        }
    }

    public void setMacroseismicOrigin(List<MacroseismicOrigin> macroseismicOrigins) {
        this.macroseismicOrigin = macroseismicOrigins;
        // Hibernate relationship specific
        if (macroseismicOrigins != null) {
            for (MacroseismicOrigin magOrg: macroseismicOrigins) {
                magOrg.setMacroseismicParameters(this);
            }
        }
    }

    public void setMdp(List<MDP> mdps) {
        this.mdp = mdps;
        // Hibernate relationship specific
        if (mdps != null) {
            for (MDP m: mdps) {
                m.setMacroseismicParameters(this);
            }
        }
    }

    public void setMdpSet(List<MDPSet> mdpSets) {
        this.mdpSet = mdpSets;
        // Hibernate relationship specific
        if (mdpSet != null) {
            for (MDPSet mSet: mdpSets) {
                mSet.setMacroseismicParameters(this);
            }
        }
    }

    public void setPlace(List<Place> places) {
        this.place = places;
        // Hibernate relationship specific
        if (places != null) {
            for (Place p: places) {
                p.setMacroseismicParameters(this);
            }
        }
    }
}
