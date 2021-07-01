package no.nnsn.convertercore.mappers.interfaces;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.*;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;

import java.util.List;


public interface QmlMapper {
    Object mapOrigin(Line1 line1, List<LineE> lEs, String errorHandling);
    List<Object> mapLine1Magnitudes(Line1 line1, Origin org);
    Object mapLine1FocalMechanisms(LineF lineF);
    List<FocalMechanism> mapLine1FocalMechanismsOld(
            int iterator,
            List<LineF> lFs,
            List<FocalMechanism> fmechs,
            List<LineM2> lineM2s,
            Event event,
            List<Line1> l1s,
            Origin org
    );
    Object mapMomentTensor(LineM2 lineM2, Line1 line1);
    Object mapMomentTensorOrigin(LineM1 lineM1);
    Object mapMomentTensorMagnitude(LineM1 lineM1);
    Comment mapL3Comment(Line3 line3);
    Comment mapL5Comment(Line5 line5);
    Comment mapL6Comment(Line6 line6);
    Comment mapLIComment(LineI lineI);
    Comment mapLSComment(LineS lineS);
    Object mapPick(Line4 line4, Line4Dto line4Dto, List<Line1> l1s);
    Object mapArrival(Line4 line4, Line4Dto line4Dto, Pick pick, List<Line1> l1s);
    Object mapAmplitude(Line4 line4, Line4Dto line4Dto, Pick pick, List<Line1> l1s);
}
