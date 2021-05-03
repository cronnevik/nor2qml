package no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20;

import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.annotations.GeneralQualifiers;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.helpers.GeneralHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.helpers.EnumsHelper;
import no.nnsn.convertercore.mappers.from_nordic.v1.to_qml.v20.utils.ChildChecker;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM1;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

/**
 * Nordic format to CompositeTime Mapper - Mapping of the Nordic format to CompositeTime entity in QuakeML 2.0.
 *
 * @author Christian Rønnevik
 */
@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {
                GeneralHelper.class,
                EnumsHelper.class
        })
public abstract class NordicToCompositeTimeMapper {

    /** Mapper instance. */
    public static NordicToCompositeTimeMapper INSTANCE = Mappers.getMapper(NordicToCompositeTimeMapper.class);

    /**
     * Mapping of properties for the Comment entity from the type 1 lines within Nordic format to QuakeML version 2.0.
     *
     * @param line1 Line1 object
     * @return CompositeTime
     */
    @Mappings({
            // Integer
            // Internal ID --> ignore
            @Mapping(target = "compositeTimeID", ignore = true),

            // IntegerQuantity (Object)
            @Mapping(target = "year.value", source = "line1.year", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "month.value", source = "line1.month", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "day.value", source="line1.day",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "hour.value", source="line1.hour",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "minute.value", source="line1.minutes",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // RealQuantity (Object)
            @Mapping(target = "second.value", source="line1.seconds",  qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // String
            // NO MAPPING DETERMINED --> ignore
            @Mapping(target = "calendar", ignore = true)
    })
    public abstract CompositeTime mapOriginCompositeTime(Line1 line1);

    @Mappings({
            // Integer
            // Internal ID --> ignore
            @Mapping(target = "compositeTimeID", ignore = true),

            // IntegerQuantity (Object)
            @Mapping(target = "year.value", source = "lineM1.year", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "month.value", source = "lineM1.month", qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "day.value", source="lineM1.day",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "hour.value", source="lineM1.hour",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // IntegerQuantity (Object)
            @Mapping(target = "minute.value", source="lineM1.minutes",  qualifiedBy = GeneralQualifiers.StringToInteger.class),
            // RealQuantity (Object)
            @Mapping(target = "second.value", source="lineM1.seconds",  qualifiedBy = GeneralQualifiers.StringToDouble.class),

            // String
            // NO MAPPING DETERMINED --> ignore
            @Mapping(target = "calendar", ignore = true)
    })
    public abstract CompositeTime mapMomentTensorCompositeTime(LineM1 lineM1);

    /*
     * After Mappings - Handle conversion of properties that cannot be mapped directly or need modifications.
     *
     */

    @AfterMapping
    protected void fixNordicSecondsBug(@MappingTarget CompositeTime cTime, Line1 line1) {
        if (cTime != null) {
            if (cTime.getSecond() != null) {
                if (cTime.getSecond().getValue() != null) {
                    if (cTime.getSecond().getValue() == 60.0) {

                        int year = cTime.getYear().getValue();
                        int month = cTime.getMonth().getValue();
                        int day = cTime.getDay().getValue();
                        int hour = cTime.getHour().getValue();
                        int minute = cTime.getMinute().getValue();
                        int second = 0;
                        int nanoSec = 0;

                        final LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second, nanoSec).plusMinutes(1);
                        cTime.getYear().setValue(localDateTime.getYear());
                        cTime.getMonth().setValue(localDateTime.getMonthValue());
                        cTime.getDay().setValue(localDateTime.getDayOfMonth());
                        cTime.getHour().setValue(localDateTime.getHour());
                        cTime.getMinute().setValue(localDateTime.getMinute());

                        double secAndMillSec = Double.valueOf(localDateTime.getSecond()) + (Double.valueOf(localDateTime.getNano()) / Math.pow(10,8));
                        cTime.getSecond().setValue(secAndMillSec);
                    }
                }
            }
        }
    }

    /**
     * AfterMapping - When using AfterMapping, null checking will have to be done manually on relating custom objects.
     * This include objects like
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.IntegerQuantity} and
     * {@link no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity}
     * Methods for respective object is specified within {@link ChildChecker}.
     *
     * @param ctime The CompositeTime object that were build in the initial mapping.
     */
    @AfterMapping
    protected void setChildToNull(@MappingTarget CompositeTime ctime) {
        // year (IntegerQuantity)
        if (ChildChecker.isIntegerQuantityNull(ctime.getYear())){
            ctime.setYear(null);
        }
        // month (IntegerQuantity)
        if (ChildChecker.isIntegerQuantityNull(ctime.getMonth())){
            ctime.setMonth(null);
        }
        // day (IntegerQuantity)
        if (ChildChecker.isIntegerQuantityNull(ctime.getDay())){
            ctime.setDay(null);
        }
        // hour (IntegerQuantity)
        if (ChildChecker.isIntegerQuantityNull(ctime.getHour())){
            ctime.setHour(null);
        }
        // minute (IntegerQuantity)
        if (ChildChecker.isIntegerQuantityNull(ctime.getMinute())){
            ctime.setMinute(null);
        }
        // second (RealQuantity)
        if (ChildChecker.isRealQuantityNull(ctime.getSecond())){
            ctime.setSecond(null);
        }
    }

}
