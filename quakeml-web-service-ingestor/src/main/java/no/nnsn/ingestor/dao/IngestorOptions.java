package no.nnsn.ingestor.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import no.nnsn.convertercore.helpers.ConverterProfile;

import java.util.Date;

@Data
@NoArgsConstructor
public class IngestorOptions {
    String input;
    String sourceType;
    Date startDate;
    Date endData;
    ConverterProfile profile;
    Boolean clean;
    Integer chunck;
    String dbName;
    Boolean forceIngestion;
    String catalogName;

    String qmlPrefix;
    String qmlAgency;
}
