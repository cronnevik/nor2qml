package no.nnsn.quakemlwebservice.dao;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;

@Data
public class SfileCheckWrapper {
    SfileCheck sfileCheck;
    Catalog catalog;
}
