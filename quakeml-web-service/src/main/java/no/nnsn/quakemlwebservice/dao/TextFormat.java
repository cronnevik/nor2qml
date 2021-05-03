package no.nnsn.quakemlwebservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextFormat {
    String eventID;
    String time;
    String latitude;
    String longitude;
    String depth;
    String author;
    String catalog;
    String contributor;
    String contributorID;
    String magType;
    String magnitude;
    String magAuthor;
    String eventLocationName;
    String eventType;
}
