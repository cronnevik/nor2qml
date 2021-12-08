package no.nnsn.quakemlwebservice.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MapEvent {
    String eventID;
    String time;
    Double latitude;
    Double longitude;
    Double depth;
    Double magnitude;
    String eventType;
}
