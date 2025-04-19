package no.nnsn.seisanquakemljpa.models.quakeml.v12.event.types;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Date;

@Data
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TimeWindowDto {
    private Date reference;
    private Double begin;
    private Double end;
}