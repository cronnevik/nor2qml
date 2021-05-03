package no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers.helpers;

import lombok.Data;

@Data
public class FieldVerifier {
    NumberValue number;
    Boolean error;

    public FieldVerifier(NumberValue num, Boolean error) {
        this.number = num;
        this.error = error;
    }
}
