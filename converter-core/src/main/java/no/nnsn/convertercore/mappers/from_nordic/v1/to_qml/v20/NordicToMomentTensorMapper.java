package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.MomentTensor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.Tensor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.TimeQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM2;
import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Nordic format to MomentTensor Mapper - Mapping of the Nordic format to MomentTensor entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralHelper.class})
public abstract class NordicToMomentTensorMapper {

    /** Mapper instance. */
    public static NordicToMomentTensorMapper INSTANCE = Mappers.getMapper(NordicToMomentTensorMapper.class);

    /**
     * Mapping of properties for the Origin entity from the type M1 line (1st moment tensor line) within Nordic format to QuakeML version 2.0.
     *
     * @param lineM1 LineM1 object (1st moment tensor line)
     * @return Origin
     */
    @Mappings({
            // String (ResourceReference) - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // TimeQuantity (Object) - Set in AfterMapping
            @Mapping(target = "time", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "latitude.value", source="lineM1.latitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "longitude.value", source="lineM1.longitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // CompositeTime (List) - Set in AfterMapping
            @Mapping(target = "compositeTime", ignore = true),
            // RealQuantity (Object)
            @Mapping(target = "depth.value", source="lineM1.depth",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // OriginType (Enum) - Set om AfterMapping
            @Mapping(target = "type", ignore = true),

            // EvaluationMode (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "evaluationMode", ignore = true),
            // EvaluationStatus (Enum)- NO MAPPING DETERMINED
            @Mapping(target = "evaluationStatus", ignore = true),
            // CreationInfo (Object) - Set in AfterMapping
            @Mapping(target = "creationInfo", ignore = true),

            // Following attributes not applicable for line M2 (only for line 1):

            // OriginDepthType (Enum)
            @Mapping(target = "depthType", ignore = true),
            // Boolean
            @Mapping(target = "epicenterFixed", ignore = true),
            // Boolean
            @Mapping(target = "timeFixed", ignore = true),
            // String
            @Mapping(target = "methodID", ignore = true),
            // String
            @Mapping(target = "earthModelID", ignore = true),
            // String
            @Mapping(target = "referenceSystemID", ignore = true),
            // Quality (Object)
            @Mapping(target = "quality", ignore = true),
            // String
            @Mapping(target = "region", ignore = true),
    })
    public abstract Origin mapLineM1Origin(LineM1 lineM1);

    @AfterMapping
    protected void buildOriginPublicID(@MappingTarget Origin org, LineM1 lineM1) {
        org.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
            lineM1.getYear(),
            lineM1.getReportingAgency(),
            Origin.class)
        );
    }

    @AfterMapping
    protected void setOriginTime(@MappingTarget Origin org, LineM1 lineM1) {
        // year, month and day cannot be 0
        int year = lineM1.getYear() != null && !lineM1.getYear().isEmpty() ? Integer.parseInt(lineM1.getYear()) : 1111;
        int month = lineM1.getMonth() != null && !lineM1.getMonth().isEmpty() ? Integer.parseInt(lineM1.getMonth()) : 1;
        int day = lineM1.getDay() != null && !lineM1.getDay().isEmpty() && !lineM1.getDay().equals("0") ? Integer.parseInt(lineM1.getDay()) : 1;
        int hour = lineM1.getHour() != null && !lineM1.getHour().isEmpty() ? Integer.parseInt(lineM1.getHour()) : 0;
        int minute = lineM1.getMinutes() != null && !lineM1.getMinutes().isEmpty() ? Integer.parseInt(lineM1.getMinutes()) : 0;
        int second = 0;
        int nanoSec = 0;

        String secTemp = lineM1.getSeconds();
        if (secTemp != null && !secTemp.isEmpty()) {
            Double dSec = Double.parseDouble(secTemp);
            second = dSec.intValue();

            String[] secSplit = String.valueOf(dSec).split("\\.");
            if (secSplit.length == 2) {
                int[] secNums = new int[2];
                secNums[0] = Integer.parseInt(secSplit[0]);
                secNums[1] = Integer.parseInt(secSplit[1]);

                Double nanoSecTemp = secNums[1] * Math.pow(10,7);
                nanoSec =nanoSecTemp.intValue();
            }
        }

        String timeString = LocalDateTime.of(year, month, day, hour, minute, second, nanoSec).format(DateTimeFormatter.ISO_DATE_TIME);
        TimeQuantity timeQuantity = new TimeQuantity();
        timeQuantity.setValue(timeString);
        org.setTime(timeQuantity);
    }

    @AfterMapping
    protected void setOriginCompositeTime(@MappingTarget Origin org, LineM1 lineM1) {
        List<CompositeTime> times = new ArrayList<>();
        CompositeTime compositeTime = NordicToCompositeTimeMapper.INSTANCE.mapMomentTensorCompositeTime(lineM1);
        if (compositeTime != null) {
            // Check if the mapped object has a year attribute, and only add the object to Origin if true
            if (compositeTime.getYear() != null) {
                times.add(compositeTime);
                org.setCompositeTime(times);
            }
        }
    }

    @AfterMapping
    protected void setOriginCreationInfo(@MappingTarget Origin org, LineM1 lineM1) {
        if (lineM1.getReportingAgency() != null) {
            CreationInfo creationInfo = new CreationInfo();
            creationInfo.setAgencyID(lineM1.getReportingAgency());
            org.setCreationInfo(creationInfo);
        }
    }

    @AfterMapping
    protected void setOriginType(@MappingTarget Origin org) {
        org.setType(OriginType.HYPOCENTER);
    }



    /**
     * Mapping of properties for the Magnitude entity from the type M1 line (1st moment tensor line) within Nordic format to QuakeML version 2.0.
     *
     * @param lineM1 LineM1 object (1st moment tensor line)
     * @return Magnitude
     */
    @Mappings({
            // String (ResourceReference) - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            @Mapping(target = "mag.value", source = "lineM1.magnitude",  qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "type", source = "lineM1.magnitudeType"),
            @Mapping(target = "creationInfo.agencyID", source = "lineM1.magnitudeRepAgency"),
    })
    public abstract Magnitude mapLineM1Magnitude(LineM1 lineM1);




    /**
     * Mapping of properties for the MomentTensor entity from the type M2 line (2nd moment tensor line) within Nordic format to QuakeML version 2.0.
     * The origin object from QuakeML v2.0 itself is needed to link the originID to the MomentTensor object.
     *
     * @param lineM2 LineM2 object (2nd moment tensor line)
     * @param line1 Line1 object
     * @param origin Origin object
     * @return MomentTensor
     */
    @Mappings({
            // String (ResourceReference) - ID generated in AfterMapping
            @Mapping(target = "publicID", ignore = true),
            // String (ResourceReference) - Pointer to Origin publicID
            @Mapping(target = "derivedOriginID", source = "line1.orgID"),
            // Comment (List) - NO MAPPING DETERMINED
            @Mapping(target = "comment", ignore = true),
            // DataUsed (List) - NO MAPPING DETERMINED
            @Mapping(target = "dataUsed", ignore = true),
            // String (ResourceReference) - Pointer to Magnitude publicID - NO MAPPING DETERMINED
            @Mapping(target = "momentMagnitudeID", ignore = true),
            // Tensor (Object)
            @Mapping(target = "tensor.mrr.value", source = "lineM2.mrr", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "tensor.mtt.value", source = "lineM2.mtt", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "tensor.mpp.value", source = "lineM2.mpp", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "tensor.mrt.value", source = "lineM2.mrt", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "tensor.mrp.value", source = "lineM2.mrp", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            @Mapping(target = "tensor.mtp.value", source = "lineM2.mtp", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object)
            @Mapping(target = "scalarMoment.value", source = "lineM2.scalarMoment", qualifiedBy = GeneralQualifiers.StringToDouble.class),
            // RealQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "variance", ignore = true),
            // RealQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "varianceReduction", ignore = true),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "filterID", ignore = true),
            // String (ResourceReference) - NO MAPPING DETERMINED
            @Mapping(target = "greensFunctionID", ignore = true),
            // String (ResourceReference)
            @Mapping(target = "methodID", source = "lineM2.methodUsed"),
            // MomentTensorCategory (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "category", ignore = true),
            // RealQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "clvd", ignore = true),
            // RealQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "doubleCouple", ignore = true),
            // MTInversionType (Enum) - NO MAPPING DETERMINED
            @Mapping(target = "inversionType", ignore = true),
            // RealQuantity (Object) - NO MAPPING DETERMINED
            @Mapping(target = "iso", ignore = true),
            // SourceTimeFunction (Object) - NO MAPPING DETERMINED
            @Mapping(target = "sourceTimeFunction", ignore = true),
            // CreationInfo (Object) - Set in AfterMapping
            @Mapping(target = "creationInfo", ignore = true)
    })
    public abstract MomentTensor mapLineM2(LineM2 lineM2, Line1 line1);

    /*
     * AfterMappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    /**
     * AfterMapping - Building the publicID for the momentTensor object. The ID is build with the {@link IdGenerator#genTypicalPublicID} method,
     * which takes the year value from line, station name or reporting agency name and the class type as argument for constructing the ID.
     *
     * @param mT The MomentTensor object that were build in the initial mapping.
     * @param line1 Line1 object passed to the mapper.
     */
    @AfterMapping
    protected void buildMomentTensorPublicID(@MappingTarget MomentTensor mT, Line1 line1) {
        mT.setPublicID(IdGenerator.getInstance().genTypicalPublicID(
                line1.getYear(),
                line1.getHypoCenterRepAgency(),
                MomentTensor.class));
    }

    @AfterMapping
    protected void setMomentTensorCreationInfo(@MappingTarget MomentTensor mT, LineM2 lineM2) {
        if (StringUtils.isNotBlank(lineM2.getReportingAgency())) {
            CreationInfo creationInfo = new CreationInfo();
            creationInfo.setAgencyID(lineM2.getReportingAgency());
            mT.setCreationInfo(creationInfo);
        }
    }

    @AfterMapping
    protected void scaleTensorValuesToExponent(@MappingTarget MomentTensor mT, LineM2 lineM2) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        DecimalFormat df = (DecimalFormat)nf;
        String pattern = "0.000E0";
        df.applyPattern(pattern);

        String exponent = lineM2.getExponential();
        if (!StringUtils.isBlank(exponent)) {
            Double exponentValue = Double.parseDouble(exponent);
            final Tensor tensor = mT.getTensor();
            if (tensor != null) {
                // Mrr
                final RealQuantity mrr = tensor.getMrr();
                if (mrr != null) {
                    Double mrrValue = mrr.getValue();
                    if (mrrValue != null) {
                        Double mrrValueScaled = mrrValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMrr().setValue(Double.parseDouble(df.format(mrrValueScaled)));
                    }
                }

                // Mtt
                final RealQuantity mtt = tensor.getMtt();
                if (mtt != null) {
                    Double mttValue = mtt.getValue();
                    if (mttValue != null) {
                        Double mttValueScaled = mttValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMtt().setValue(Double.parseDouble(df.format(mttValueScaled)));
                    }
                }

                // Mpp
                final RealQuantity mpp = tensor.getMpp();
                if (mpp != null) {
                    Double mppValue = mpp.getValue();
                    if (mppValue != null) {
                        Double mppValueScaled = mppValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMpp().setValue(Double.parseDouble(df.format(mppValueScaled)));
                    }
                }

                // Mrt
                final RealQuantity mrt = tensor.getMrt();
                if (mrt != null) {
                    Double mrtValue = mrt.getValue();
                    if (mrtValue != null) {
                        Double mrtValueScaled = mrtValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMrt().setValue(Double.parseDouble(df.format(mrtValueScaled)));
                    }
                }

                // Mrp
                final RealQuantity mrp = tensor.getMrp();
                if (mrp != null) {
                    Double mrpValue = mrp.getValue();
                    if (mrpValue != null) {
                        Double mrpValueScaled = mrpValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMrp().setValue(Double.parseDouble(df.format(mrpValueScaled)));
                    }
                }

                // Mtp
                final RealQuantity mtp = tensor.getMtp();
                if (mtp != null) {
                    Double mtpValue = mtp.getValue();
                    if (mtpValue != null) {
                        Double mtpValueScaled = mtpValue * Math.pow(10, exponentValue);
                        mT.getTensor().getMtp().setValue(Double.parseDouble(df.format(mtpValueScaled)));
                    }
                }
            }
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity}.
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param mt The MomentTensor object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setMomentTensorChildToNull(@MappingTarget MomentTensor mt) {
        // scalarMoment (RealQuantity)
        if (ChildChecker.isRealQuantityNull(mt.getScalarMoment())) {
            mt.setScalarMoment(null);
        }
        // varianceReduction (RealQuantity)
        if (ChildChecker.isRealQuantityNull(mt.getVarianceReduction())) {
            mt.setVarianceReduction(null);
        }
        // clvd (RealQuantity)
        if (ChildChecker.isRealQuantityNull(mt.getClvd())) {
            mt.setClvd(null);
        }
        // doubleCouple (RealQuantity)
        if (ChildChecker.isRealQuantityNull(mt.getDoubleCouple())) {
            mt.setDoubleCouple(null);
        }
    }
}
