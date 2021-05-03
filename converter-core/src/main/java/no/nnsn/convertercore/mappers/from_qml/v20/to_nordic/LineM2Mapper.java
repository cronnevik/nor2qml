package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.MtHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.MomentTensor;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM2;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class}
)
public abstract class LineM2Mapper {

    public static LineM2Mapper INSTANCE = Mappers.getMapper(LineM2Mapper.class);

    @Mappings({
            @Mapping(target = "orgID", source = "momentTensor.derivedOriginID"),
            @Mapping(target = "mrr", source = "momentTensor.tensor.mrr.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "mtt", source = "momentTensor.tensor.mtt.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "mpp", source = "momentTensor.tensor.mpp.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "mrt", source = "momentTensor.tensor.mrt.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "mrp", source = "momentTensor.tensor.mrp.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "mtp", source = "momentTensor.tensor.mtp.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "scalarMoment", source = "momentTensor.scalarMoment.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "methodUsed", source = "momentTensor.methodID"),
            @Mapping(target = "reportingAgency", source = "momentTensor.creationInfo.agencyID"),
    })
    public abstract LineM2 mapLineM2(MomentTensor momentTensor);

    @AfterMapping
    protected void scaleMtValues(@MappingTarget LineM2 lineM2, MomentTensor momentTensor) {
        // The nordic format has a dedicated field to store the exponential factor
        String scalar = lineM2.getScalarMoment();
        String mrr = lineM2.getMrr();
        String mtt = lineM2.getMtt();
        String mpp = lineM2.getMpp();
        String mrt = lineM2.getMrt();
        String mrp = lineM2.getMrp();
        String mtp = lineM2.getMtp();

        // Get largest exponent
        MtHelper mtHelper = new MtHelper(scalar, mrr, mtt, mpp, mrt, mrp, mtp);
        int exponent = mtHelper.getExponent();
        lineM2.setExponential(String.valueOf(exponent));

        // Rescale values
        lineM2.setScalarMoment(mtHelper.getScalar().getScaledValue(exponent));
        lineM2.setMrr(mtHelper.getMrr().getScaledValue(exponent));
        lineM2.setMtt(mtHelper.getMtt().getScaledValue(exponent));
        lineM2.setMpp(mtHelper.getMpp().getScaledValue(exponent));
        lineM2.setMrt(mtHelper.getMrt().getScaledValue(exponent));
        lineM2.setMrp(mtHelper.getMrp().getScaledValue(exponent));
        lineM2.setMtp(mtHelper.getMtp().getScaledValue(exponent));
    }
}
