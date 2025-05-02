package no.nnsn.seisanquakeml.models.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlinnEngdahl {
    private String name_s;
    private String name_m;
    private String name_l;
    private String name_h;

}
