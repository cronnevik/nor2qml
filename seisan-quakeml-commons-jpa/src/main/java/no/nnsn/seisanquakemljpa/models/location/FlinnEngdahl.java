package no.nnsn.seisanquakemljpa.models.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlinnEngdahl {
    private String name_s;
    private String name_m;
    private String name_l;
    private String name_h;

    public String getName_s() {
        return name_s;
    }

    public void setName_s(String name_s) {
        this.name_s = name_s;
    }

    public String getName_m() {
        return name_m;
    }

    public void setName_m(String name_m) {
        this.name_m = name_m;
    }

    public String getName_l() {
        return name_l;
    }

    public void setName_l(String name_l) {
        this.name_l = name_l;
    }

    public String getName_h() {
        return name_h;
    }

    public void setName_h(String name_h) {
        this.name_h = name_h;
    }
}
