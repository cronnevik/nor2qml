package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM1;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class}
)
public abstract class LineM1Mapper {
    public static LineM1Mapper INSTANCE = Mappers.getMapper(LineM1Mapper.class);

    @Mappings({
            @Mapping(target = "orgID", source = "origin.publicID"),
            @Mapping(target = "year", ignore = true),
            @Mapping(target = "month", ignore = true),
            @Mapping(target = "day", ignore = true),
            @Mapping(target = "hour", ignore = true),
            @Mapping(target = "minutes", ignore = true),
            @Mapping(target = "seconds", ignore = true),
            @Mapping(target = "latitude", source = "origin.latitude.value"),
            @Mapping(target = "longitude", source = "origin.longitude.value"),
            @Mapping(target = "depth", source = "origin.depth.value"),
            @Mapping(target = "reportingAgency", source = "origin.creationInfo.agencyID"),

            @Mapping(target = "magnitude", source = "magnitude.mag.value"),
            @Mapping(target = "magnitudeType", source = "magnitude.type"),
            @Mapping(target = "magnitudeRepAgency", source = "magnitude.creationInfo.agencyID")
    })
    public abstract LineM1 mapLineM1Full(Origin origin, Magnitude magnitude);

    @Mappings({
            @Mapping(target = "orgID", source = "origin.publicID"),
            @Mapping(target = "year", ignore = true),
            @Mapping(target = "month", ignore = true),
            @Mapping(target = "day", ignore = true),
            @Mapping(target = "hour", ignore = true),
            @Mapping(target = "minutes", ignore = true),
            @Mapping(target = "seconds", ignore = true),
            @Mapping(target = "latitude", source = "origin.latitude.value"),
            @Mapping(target = "longitude", source = "origin.longitude.value"),
            @Mapping(target = "depth", source = "origin.depth.value"),
            @Mapping(target = "reportingAgency", source = "origin.creationInfo.agencyID"),

    })
    public abstract LineM1 mapLineM1Org(Origin origin);

    @AfterMapping
    protected void setTime(@MappingTarget LineM1 lineM1, Origin org) {
        if (lineM1.getYear() == null) {

            // Always check for CompositeObject first to get the origin time
            if (org.getCompositeTime() != null) {
                List<CompositeTime> times = org.getCompositeTime();
                // Use the first CompositeTime object
                CompositeTime time = times.get(0);
                // Assuming that year is always present if CompositeTime object exists
                lineM1.setYear(time.getYear().getValue().toString());
                if (time.getMonth() != null) {
                    lineM1.setMonth(time.getMonth().getValue().toString());
                }
                if (time.getDay() != null) {
                    lineM1.setDay(time.getDay().getValue().toString());
                }
                if (time.getHour() != null) {
                    lineM1.setHour(time.getHour().getValue().toString());
                }
                if (time.getMinute() != null) {
                    lineM1.setMinutes(time.getMinute().getValue().toString());
                }

                if (time.getSecond() != null) {
                    Double seconds = time.getSecond().getValue();
                    lineM1.setSeconds(seconds.toString());
                }
            } else {
                String timeString = org.getTime().getValue();

                // Zoned DateTime (e.g. 2015-11-30T00:00:15.50Z)
                if(timeString.substring(timeString.length() -1 ).equals("Z")) {
                    ZonedDateTime timeZoned = ZonedDateTime.parse(timeString);
                    lineM1.setYear(String.valueOf(timeZoned.getYear()));
                    lineM1.setMonth(String.valueOf(timeZoned.getMonthValue()));
                    lineM1.setDay(String.valueOf(timeZoned.getDayOfMonth()));
                    lineM1.setHour(String.valueOf(timeZoned.getHour()));
                    lineM1.setMinutes(String.valueOf(timeZoned.getMinute()));

                    Double secAndMillSec = Double.valueOf(timeZoned.getSecond()) + (Double.valueOf(timeZoned.getNano()) / Math.pow(10,9));
                    lineM1.setSeconds(secAndMillSec.toString());
                }
                // Default DateTime (e.g. 2012-05-29T23:58:58.770000)
                else {
                    LocalDateTime time = LocalDateTime.parse(timeString);
                    lineM1.setYear(String.valueOf(time.getYear()));
                    lineM1.setMonth(String.valueOf(time.getMonthValue()));
                    lineM1.setDay(String.valueOf(time.getDayOfMonth()));
                    lineM1.setHour(String.valueOf(time.getHour()));
                    lineM1.setMinutes(String.valueOf(time.getMinute()));

                    Double secAndMillSec = Double.valueOf(time.getSecond()) + (Double.valueOf(time.getNano()) / Math.pow(10,9));
                    lineM1.setSeconds(secAndMillSec.toString());
                }
            }
        }
    }

    @AfterMapping
    protected void depthConverion(@MappingTarget LineM1 lineM1) {
        if (lineM1.getDepth() != null) {
            Double depth = Double.parseDouble(lineM1.getDepth());
            Double convDepth = depth/1000; // to km
            lineM1.setDepth(convDepth.toString());
        }
    }

    @AfterMapping
    protected void setProperAgencyID(@MappingTarget LineM1 lineM1, Magnitude magnitude) {
        if ( lineM1 != null && magnitude != null) {
            String agencyID =  magnitude.getCreationInfo().getAgencyID();
            // ISC format give the agencyID within author
            String author = magnitude.getCreationInfo().getAuthor();
            lineM1.setMagnitudeRepAgency(LineHelper.setMagAgencies(agencyID, author));
        }
    }

}
