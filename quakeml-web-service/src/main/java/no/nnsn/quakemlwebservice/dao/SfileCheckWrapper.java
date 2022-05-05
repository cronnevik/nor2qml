package no.nnsn.quakemlwebservice.dao;

import lombok.Data;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;

@Data
public class SfileCheckWrapper {
    SfileInformation sfileInformation;
    Catalog catalog;
}
