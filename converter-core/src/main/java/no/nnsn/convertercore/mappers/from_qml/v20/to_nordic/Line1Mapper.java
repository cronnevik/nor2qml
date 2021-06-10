package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.EnumLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.CompositeTime;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventTypeCertainty;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.OriginDepthType;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Mapper for QuakeML version 2.0 {@literal=>} Nordic Format (Seisan) type 1 Line
 *
 * @author Christian Rønnevik
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, uses = {GeneralLineHelper.class, EnumLineHelper.class})
public abstract class Line1Mapper {

    public static Line1Mapper INSTANCE = Mappers.getMapper(Line1Mapper.class);

    /**
     * Mapping of properties for type 1 Line for the Nordic Format from QuakeML 2.0 entities.
     *
     * @param magID Preferred magnitude id (Resource identifier)
     * @param origin Single Origin object
     * @param magnitudes List of magnitudes
     *
     * @return Line1 Entity.
     */
    @Mappings({
            // link the Origin ID to Line1 for row ordering of other lines (such as LineF)
            @Mapping(target = "orgID", source = "origin.publicID"),

            // Set in AfterMapping - Time obj used if compositeTime is not present
            @Mapping(target = "time", source = "origin.time", ignore=true),
            @Mapping(target = "latitude", source="origin.latitude.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "longitude", source="origin.longitude.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),
            @Mapping(target = "depth", source="origin.depth.value", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),

            // Set in AfterMapping
            @Mapping(target = "depthIndicator", source = "origin.depthType", ignore = true),

            // Set in AfterMapping - CompositeTime Object mapped to individual if not Time Obj was present
            @Mapping(target = "year", ignore = true),
            @Mapping(target = "month", ignore = true),
            @Mapping(target = "day", ignore = true),
            @Mapping(target = "hour", ignore = true),
            @Mapping(target = "minutes", ignore = true),
            @Mapping(target = "seconds", ignore = true),

            @Mapping(target = "eventID", ignore = true),

            @Mapping(target = "numStationsUsed", source = "origin.quality.associatedStationCount", qualifiedBy = GeneralLineQualifiers.IntegerToString.class),
            @Mapping(target = "rmsTimeResiduals", source = "origin.quality.standardError", qualifiedBy = GeneralLineQualifiers.DoubleToString.class),

            @Mapping(target = "hypoCenterRepAgency", source = "origin.creationInfo.agencyID")
    })
    public abstract Line1 mapLine1(String magID, Origin origin, List<Magnitude> magnitudes, EventType eventType, EventTypeCertainty typeCertainty);

    /*
     * After Mappings
     *
     */

    @AfterMapping
    protected void setTime(@MappingTarget Line1 line1, Origin org) {
        if (line1.getYear() == null) {

            // Always check for CompositeObject first to get the origin time
            if (org.getCompositeTime() != null) {
                List<CompositeTime> times = org.getCompositeTime();
                // Use the first CompositeTime object
                CompositeTime time = times.get(0);
                // Assuming that year is always present if CompositeTime object exists
                line1.setYear(time.getYear().getValue().toString());
                if (time.getMonth() != null) {
                    line1.setMonth(time.getMonth().getValue().toString());
                }
                if (time.getDay() != null) {
                    line1.setDay(time.getDay().getValue().toString());
                }
                if (time.getHour() != null) {
                    line1.setHour(time.getHour().getValue().toString());
                }
                if (time.getMinute() != null) {
                    line1.setMinutes(time.getMinute().getValue().toString());
                }

                if (time.getSecond() != null) {
                    Double seconds = time.getSecond().getValue();
                    line1.setSeconds(seconds.toString());
                }
            } else {
                String timeString = org.getTime().getValue();

                // Zoned DateTime (e.g. 2015-11-30T00:00:15.50Z)
                if(timeString.substring(timeString.length() -1 ).equals("Z")) {
                    ZonedDateTime timeZoned = ZonedDateTime.parse(timeString);
                    line1.setYear(String.valueOf(timeZoned.getYear()));
                    line1.setMonth(String.valueOf(timeZoned.getMonthValue()));
                    line1.setDay(String.valueOf(timeZoned.getDayOfMonth()));
                    line1.setHour(String.valueOf(timeZoned.getHour()));
                    line1.setMinutes(String.valueOf(timeZoned.getMinute()));

                    Double secAndMillSec = Double.valueOf(timeZoned.getSecond()) + (Double.valueOf(timeZoned.getNano()) / Math.pow(10,9));
                    line1.setSeconds(secAndMillSec.toString());
                }
                // Default DateTime (e.g. 2012-05-29T23:58:58.770000)
                else {
                    LocalDateTime time = LocalDateTime.parse(timeString);
                    line1.setYear(String.valueOf(time.getYear()));
                    line1.setMonth(String.valueOf(time.getMonthValue()));
                    line1.setDay(String.valueOf(time.getDayOfMonth()));
                    line1.setHour(String.valueOf(time.getHour()));
                    line1.setMinutes(String.valueOf(time.getMinute()));

                    Double secAndMillSec = Double.valueOf(time.getSecond()) + (Double.valueOf(time.getNano()) / Math.pow(10,9));
                    line1.setSeconds(secAndMillSec.toString());
                }
            }
        }
    }

    @AfterMapping
    protected void setEventID(@MappingTarget Line1 line1, EventType eventType, EventTypeCertainty typeCertainty) {
        if (eventType != null)
        switch (eventType) {
            case EARTHQUAKE:
                if (typeCertainty != null) {
                    if (typeCertainty.equals(EventTypeCertainty.KNOWN)) {
                        line1.setEventID("Q");
                    } else { // known
                        line1.setEventID("");
                    }
                }
                break;
            case EXPLOSION:
                if (typeCertainty != null) {
                    if (typeCertainty.equals(EventTypeCertainty.KNOWN)) {
                        line1.setEventID("E");
                    }
                }
                else { // Suspected
                    line1.setEventID("P");
                }
                break;
            case INDUCED_OR_TRIGGERED_EVENT:
                line1.setEventID("I");
                break;
            case OTHER_EVENT:
                line1.setEventID("O");
                break;
            case SONIC_BOOM:
                line1.setEventID("S");
                break;
            case ICE_QUAKE:
                line1.setEventID("G");
                break;
            case LANDSLIDE:
                line1.setEventID("L");
                break;
            case VOLCANIC_ERUPTION:
                line1.setEventID("V");
                break;
        }
    }

    @AfterMapping
    protected void depthConverion(@MappingTarget Line1 line1) {
        if (line1.getDepth() != null) {
            Double depth = Double.parseDouble(line1.getDepth());
            Double convDepth = depth / 1000; // m --> km
            line1.setDepth(convDepth.toString());
        }
    }


    @AfterMapping
    protected void setMagnitudes(@MappingTarget Line1 line1, String magID, List<Magnitude> magnitudes) {
        if (magnitudes != null) {
            String prefMagID = magID;
            // TODO - handle position of preferred magnitude id in Line1

            // loop through magnitudes, which should maximum be a list of 3 elements
            for (int i = 0; i < magnitudes.size() ; i++) {
                String agencyID;
                String author;

                switch (i) {
                    case 0:
                        line1.setMagOneNo(magnitudes.get(0).getMag().getValue().toString());

                        if (magnitudes.get(0).getType() != null) {
                            String qmlMagType = magnitudes.get(0).getType();
                            line1.setMagOneType(LineHelper.convertMagTypeToNordic(qmlMagType));
                        }

                        // Check and correct for AgencyID longer than 3 chars (Sfile cannot contain longer than 3)
                        if (magnitudes.get(0).getCreationInfo() != null) {
                            agencyID =  magnitudes.get(0).getCreationInfo().getAgencyID();
                            // ISC format give the agencyID within author
                            author = magnitudes.get(0).getCreationInfo().getAuthor();
                            line1.setMagOneRepAgency(LineHelper.setMagAgencies(agencyID, author));
                        }

                        break;

                    case 1:
                        line1.setMagTwoNo(magnitudes.get(1).getMag().getValue().toString());

                        if (magnitudes.get(1).getType() != null) {
                            String qmlMagType = magnitudes.get(1).getType();
                            line1.setMagTwoType(LineHelper.convertMagTypeToNordic(qmlMagType));
                        }

                        if (magnitudes.get(1).getCreationInfo() != null) {
                            // Check and correct for AgencyID longer than 3 chars (Sfile cannot contain longer than 3)
                            agencyID =  magnitudes.get(1).getCreationInfo().getAgencyID();
                            // ISC format give the agencyID within author
                            author = magnitudes.get(1).getCreationInfo().getAuthor();
                            line1.setMagTwoRepAgency(LineHelper.setMagAgencies(agencyID, author));
                        }

                        break;

                    case 2:
                        line1.setMagThreeNo(magnitudes.get(2).getMag().getValue().toString());

                        if (magnitudes.get(2).getType() != null) {
                            String qmlMagType = magnitudes.get(2).getType();
                            line1.setMagThreeType(LineHelper.convertMagTypeToNordic(qmlMagType));
                        }

                        if (magnitudes.get(2).getCreationInfo() != null) {
                            // Check and correct for AgencyID longer than 3 chars (Sfile cannot contain longer than 3)
                            agencyID =  magnitudes.get(2).getCreationInfo().getAgencyID();
                            // ISC format give the agencyID within author
                            author = magnitudes.get(2).getCreationInfo().getAuthor();
                            line1.setMagThreeRepAgency(LineHelper.setMagAgencies(agencyID, author));
                        }

                        break;
                }
            }
        }
    }

    @AfterMapping
    protected void setDepthIndLocatingIndAndFixOfTime(@MappingTarget Line1 line1, Origin origin) {
        final OriginDepthType depthType = origin.getDepthType();
        final Boolean epicenterFixed = origin.getEpicenterFixed();
        final Boolean timeFixed = origin.getTimeFixed();

        if (depthType != null && epicenterFixed != null && timeFixed != null) {
            if (
                    depthType.equals(OriginDepthType.OPERATOR_ASSIGNED)
                    && timeFixed.equals(true)
                    && epicenterFixed.equals(true)
            ) {
                line1.setLocIndicator("*");
            } else {
                // Set Depth Indicator
                if (depthType.equals(OriginDepthType.OPERATOR_ASSIGNED)) {
                    line1.setDepthIndicator("F");
                } else if (depthType.equals(OriginDepthType.FROM_LOCATION)) {
                    line1.setDepthIndicator("S");
                }

                // Set Locating indicator
                if (epicenterFixed) {
                    line1.setLocIndicator("F");
                } // else blank

                if (timeFixed) {
                    line1.setFixOfTime("F");
                } // else blank
            }
        }

        // If location indicator is not null or set to *
        if (line1.getLocIndicator() == null || !line1.getLocIndicator().equals("*")) {
            if (depthType != null) {
                if (depthType.equals(OriginDepthType.OPERATOR_ASSIGNED)) {
                    line1.setDepthIndicator("F");
                } else if (depthType.equals(OriginDepthType.FROM_LOCATION)) {
                    line1.setDepthIndicator("S");
                }
            }

            if (epicenterFixed != null) {
                if (epicenterFixed) {
                    line1.setLocIndicator("F");
                } // else blank
            }

            if (timeFixed != null) {
                if (timeFixed) {
                    line1.setFixOfTime("F");
                } // else blank
            }
        }

    }

    @AfterMapping
    protected void setDistanceIndicator(@MappingTarget Line1 line1, Origin origin) {

        if (origin.getEarthModelID() != null) {
            switch (origin.getEarthModelID()) {
                case "IASP91":
                    line1.setDistanceIndicator("D");
                    break;
                case "Local model":
                    line1.setDistanceIndicator("L");
                    break;
                default:
                    line1.setDistanceIndicator("R");
                    break;

            }
        } else {
            // Set default distance indicator in Sfile -> 'R'
            line1.setDistanceIndicator("R");
        }

    }

    @AfterMapping
    protected void setTimeIfCompositeTimeIsNull() {
        // TODO - implement
    }

    @AfterMapping
    protected void alternativeSourceOfHypoCenterRepAgency(@MappingTarget Line1 line1, Origin origin) {
        if (StringUtils.isBlank(line1.getHypoCenterRepAgency())) {
            if (origin.getCreationInfo() != null) {
                CreationInfo creationInfo = origin.getCreationInfo();
                if (StringUtils.isNotBlank(creationInfo.getAuthor())) {
                    line1.setHypoCenterRepAgency(creationInfo.getAuthor());
                }
            }
        }
    }

    @AfterMapping
    protected void fixHypoCenterRepAgency(@MappingTarget Line1 line1) {
        String hypCentRepAgency = line1.getHypoCenterRepAgency();
        if (hypCentRepAgency != null) {
            if (line1.getHypoCenterRepAgency().length() > 3) {
                String agencyID =  line1.getHypoCenterRepAgency();
                String newAgencyID = agencyID.substring(0, agencyID.length() -1);
                line1.setHypoCenterRepAgency(newAgencyID);
            }
        }
    }

}
