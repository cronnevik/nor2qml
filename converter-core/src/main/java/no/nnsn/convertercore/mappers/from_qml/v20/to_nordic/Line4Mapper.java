package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.helpers.Line4Entities;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.annotations.GeneralLineQualifiers;
import no.nnsn.convertercore.mappers.utils.AmplitudeUnitConverter;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeUnit;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickOnset;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickPolarity;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Mapper for QuakeML {@literal=>} Seisan Line type 4
 *
 * @author Christian Rønnevik
 */

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class})
public abstract class Line4Mapper {
    public static Line4Mapper INSTANCE = Mappers.getMapper(Line4Mapper.class);

    @Mappings({
            // Pick Entity Mappings
            @Mapping(target = "stationName", ignore = true), // AfterMapping
            @Mapping(target = "instrumentType", ignore = true), // AfterMapping
            @Mapping(target = "component", ignore = true), // AfterMapping
            @Mapping(target = "firstMotion", ignore = true), // AfterMapping
            @Mapping(target = "hour", ignore = true), // AfterMapping
            @Mapping(target = "minutes", ignore = true), // AfterMapping
            @Mapping(target = "seconds", ignore = true), // AfterMapping

            @Mapping(target = "directionDegrees", ignore = true), // AfterMapping
            @Mapping(target = "phaseVelocity", ignore = true),  // AfterMapping
            @Mapping(target = "qualityIndicator", ignore = true), // AfterMapping
            @Mapping(target = "phaseID", ignore = true), // AfterMapping

            // Amplitude Entity Mappings
            @Mapping(target = "amplitude", ignore = true),
            @Mapping(target = "periodSeconds", ignore = true),

            // Arrival Entity Mappings
            @Mapping(target = "weightingIndicator", ignore = true ), // AfterMapping
            @Mapping(target = "angleOfIncidence", ignore = true),
            @Mapping(target = "backAzimuthResidual", ignore = true), // AfterMapping
            @Mapping(target = "travelTimeResidual", ignore = true),

            @Mapping(target = "epicentralDistance", ignore = true),
            @Mapping(target = "azimuthAtSource", ignore = true),

    })
    public abstract Line4 mapLine4(Line4Entities l4Entities);
    /*
     * Pick AfterMappings
     *
     */

    @AfterMapping
    protected void setStationName(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Pick pick = l4Entities.getPick();
        if (pick != null) {
            WaveformStreamID waveformID = pick.getWaveformID();
            if (waveformID != null) {
                String stationCode = waveformID.getStationCode();
                if (stationCode != null) {
                    line4.setStationName(stationCode);
                }
            }
        }
    }

    // Instrument and Component
    @AfterMapping
    protected void setInstrumentAndComponent(@MappingTarget Line4 line4, Line4Entities line4Entities) {

        WaveformStreamID waveformID = null;

        // Check if pick element contains the values
        if (line4Entities.getPick() != null) {
            Pick pick = line4Entities.getPick();
            if (pick.getWaveformID() != null) {
                waveformID = pick.getWaveformID();
            }
            // If not, then check if Amplitude Entity contains the values
            else if (line4Entities.getAmplitude() != null) {
                Amplitude amp = line4Entities.getAmplitude();
                if (amp.getWaveformID() != null) {
                    waveformID = amp.getWaveformID();
                }
            }
        } // If not, then check if Amplitude Entity contains the values
        else if (line4Entities.getAmplitude() != null) {
            Amplitude amp = line4Entities.getAmplitude();
            if (amp.getWaveformID() != null) {
                waveformID = amp.getWaveformID();
            }
        }

        if (waveformID != null) {
            if (waveformID.getChannelCode() != null) {
                String channelCode = waveformID.getChannelCode();

                // Check if channelCode is at least 2 chars
                if (channelCode.length() >= 2) {

                    // Set instrument (first character of channel code)
                    String instrumentType = channelCode.substring(0, 1);
                    line4.setInstrumentType(instrumentType);

                    // Set component (last character of channel code)
                    String component = channelCode.substring(channelCode.length() - 1);
                    line4.setComponent(component);

                }
            }
        }

    }

    @AfterMapping
    protected void setHourMinSec(@MappingTarget Line4 line4, Line4Entities line4Entities) {
        if (line4Entities.getPick() != null) {
            Pick pick = line4Entities.getPick();
            if (pick.getTime() != null) {
                String timeString = line4Entities.getPick().getTime().getValue();
                // Zoned DateTime (e.g. 2015-11-30T00:00:15.50Z)
                if(timeString.substring(timeString.length() -1 ).equals("Z")) {
                    ZonedDateTime timeZoned = ZonedDateTime.parse(timeString);
                    if (pick.getTimeOverMidnight()) {
                        int hour = timeZoned.getHour();
                        line4.setHour(Integer.toString(hour + (24-hour)));
                    } else {
                        line4.setHour(Integer.toString(timeZoned.getHour()));
                    }

                    line4.setMinutes(Integer.toString(timeZoned.getMinute()));

                    Double secAndMillSec = Double.valueOf(timeZoned.getSecond()) + (Double.valueOf(timeZoned.getNano()) / Math.pow(10,9));
                    line4.setSeconds(secAndMillSec.toString());
                }
                // Default DateTime (e.g. 2012-05-29T23:58:58.770000)
                else {
                    LocalDateTime time = LocalDateTime.parse(timeString);
                    if (pick.getTimeOverMidnight()) {
                        int hour = time.getHour();
                        line4.setHour(Integer.toString(hour + (24-hour)));
                    } else {
                        line4.setHour(Integer.toString(time.getHour()));
                    }
                    line4.setMinutes(Integer.toString(time.getMinute()));

                    Double secAndMillSec = Double.valueOf(time.getSecond()) + (Double.valueOf(time.getNano()) / Math.pow(10,9));
                    line4.setSeconds(secAndMillSec.toString());
                }
            }
        }
    }

    @AfterMapping
    protected void setDirectionDegrees(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Pick pick = l4Entities.getPick();
        if (pick != null) {
            String value = LineHelper.getRealQuantityValue(pick.getBackazimuth());
            if (value != null) {
                line4.setDirectionDegrees(value);
            }
        }
    }

    @AfterMapping
    protected void setPhaseVelocity(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Pick pick = l4Entities.getPick();
        if (pick != null) {
            String value = LineHelper.getRealQuantityValue(pick.getHorizontalSlowness());
            if (value != null) {
                line4.setPhaseVelocity(value);
            }
        }
    }

    @AfterMapping
    protected void convPhaseVelocity(@MappingTarget Line4 line4) {
        if (StringUtils.isNotBlank(line4.getPhaseVelocity())) {
            String phaseVelString = line4.getPhaseVelocity();
            Double phaseVelNum = Double.parseDouble(phaseVelString);
            Double newPhaseVelNum = 1 / (phaseVelNum / 111.2); // s/deg to km/s (1/x --> s/km to km/s)
            line4.setPhaseVelocity(newPhaseVelNum.toString());
        }
    }

    @AfterMapping
    protected void setQualityIndicator(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getPick() != null) {
            Pick pick = l4Entities.getPick();
            if (pick.getOnset() != null) {
                PickOnset onset = pick.getOnset();
                switch (onset) {
                    case IMPULSIVE:
                        line4.setQualityIndicator("I");
                        break;
                    case EMERGENT:
                        line4.setQualityIndicator("E");
                        break;
                    default:
                        line4.setQualityIndicator(" ");
                        break;
                }
            }
        }
    }

    @AfterMapping
    protected void setPhaseID(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Pick pick = l4Entities.getPick();
        if (pick != null) {
            String phaseHint = pick.getPhaseHint();
            if (phaseHint != null) {
                line4.setPhaseID(phaseHint);
            }
        }
    }

    @AfterMapping
    protected void setFirstMotion(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getPick() != null) {
            Pick pick = l4Entities.getPick();
            if (pick.getPolarity() != null) {
                PickPolarity polarity = pick.getPolarity();
                switch (polarity) {
                    case POSITIVE:
                        line4.setFirstMotion("C");
                        break;
                    case NEGATIVE:
                        line4.setFirstMotion("D");
                        break;
                    default:
                        line4.setFirstMotion(" ");
                        break;
                }
            }
        }
    }

    /*
     * Amplitude AfterMappings
     *
     */

    @AfterMapping
    protected void setAmplitude(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Amplitude amp = l4Entities.getAmplitude();
        if (amp != null) {
            String value = LineHelper.getRealQuantityValue(amp.getGenericAmplitude());
            if (value != null) {
                line4.setAmplitude(value);
            }
        }
    }

    @AfterMapping
    protected void convertAmpByUnit(@MappingTarget Line4 line4, Line4Entities l4Entities){
        if (line4.getAmplitude() != null && l4Entities.getAmplitude() != null) {
            Amplitude amp = l4Entities.getAmplitude();
            String ampStringValue = line4.getAmplitude();
            AmplitudeUnit ampUnit = amp.getUnit();
            if (ampUnit != null) {
                switch (ampUnit) {
                    case Meter:
                        line4.setAmplitude(AmplitudeUnitConverter.fromMToNm(ampStringValue));
                        break;
                    case Seconds:
                        break;
                    case METERS_PER_SECOND:
                        line4.setAmplitude(AmplitudeUnitConverter.fromMsToNms(ampStringValue));
                        break;
                    case METERS_PER_SECOND_SQUARED:
                        break;
                    case METER_SECONDS:
                        break;
                }
            }
        }
    }

    @AfterMapping
    protected void setPeriodSeconds(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Amplitude amp = l4Entities.getAmplitude();
        if (amp != null) {
            String value = LineHelper.getRealQuantityValue(amp.getPeriod());
            if (value != null) {
                line4.setPeriodSeconds(value);
            }
        }
    }

    @AfterMapping
    protected void setAmpPhaseID(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getAmplitude() != null) {
            Amplitude amp = l4Entities.getAmplitude();

            // Set PhaseID if it is not already set by Pick entity
            if (line4.getPhaseID() == null && amp.getType() != null) {
                line4.setPhaseID(amp.getType());
            }
        }
    }

    @AfterMapping
    protected void SetAmpInstrumentAndComponent(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getAmplitude() != null) {
            Amplitude amp = l4Entities.getAmplitude();

            // Set Instrument and component if it is not already set by Pick entity
            // TODO - Compare with Pick entity and check
        }
    }

    /*
     * Arrival AfterMappings
     *
     */

    @AfterMapping
    protected void setAngleOfIncidence(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Arrival arr = l4Entities.getArrival();
        if (arr != null) {
            String value = LineHelper.getRealQuantityValue(arr.getTakeoffAngle());
            if (value != null) {
                line4.setAngleOfIncidence(value);
            }
        }
    }

    @AfterMapping
    protected void setWeightingIndicator(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getArrival() != null) {

            Arrival arr = l4Entities.getArrival();
            if (arr.getTimeWeight() != null) {
                // muliply by 100 for the convertion to int, as double cant be switched
                Double timeWeight = arr.getTimeWeight() * 100;
                int switchNum = timeWeight.intValue();
                switch (switchNum) {
                    case 0:
                        line4.setWeightingIndicator("4");
                        break;
                    case 25:
                        line4.setWeightingIndicator("3");
                        break;
                    case 50:
                        line4.setWeightingIndicator("2");
                        break;
                    case 750:
                        line4.setWeightingIndicator("1");
                        break;
                    case 100:
                        line4.setWeightingIndicator("0");
                        break;
                    default:
                        line4.setWeightingIndicator(" ");
                        break;
                }

                // Check for long phase name and set weight accordingly to column 9 of line4
                String phaseID = arr.getPhase();
                if (phaseID != null) {
                    if (phaseID.length() > 5) {
                        line4.setFreeOrWeight(line4.getWeightingIndicator());
                    }
                }
            }
        }

    }
    
    @AfterMapping
    protected void setBackAzimuthResidual(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        if (l4Entities.getArrival() != null) {
            Arrival arrival = l4Entities.getArrival();
            if (arrival.getBackazimuthResidual() != null) {
                Double bResDouble = arrival.getBackazimuthResidual();
                Integer bResInt = (int) Math.round(bResDouble);
                line4.setBackAzimuthResidual(bResInt.toString());
            }
        }
    }

    @AfterMapping
    protected void setTravelTimeResidual(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Arrival arr = l4Entities.getArrival();
        if (arr != null) {
            Double value = arr.getTimeResidual();
            if (value != null) {
                if (!value.isNaN()) {
                    line4.setTravelTimeResidual(Double.toString(value));
                }
            }
        }
    }


    @AfterMapping
    protected void setEpicentralDistanceToKm(@MappingTarget Line4 line4, Line4Entities l4Entities) {

        if (l4Entities.getArrival() != null) {
            Arrival arrival = l4Entities.getArrival();
            if (arrival.getDistance() != null) {
                Double epDist = arrival.getDistance();
                // Convert from degrees to km
                Double newDist = epDist * 111.2;
                line4.setEpicentralDistance(newDist.toString());
            }
        }

    }

    @AfterMapping
    protected void setAzimuthAtSourceFromDoubleToInt(@MappingTarget Line4 line4, Line4Entities l4Entities) {
        Arrival arrival = l4Entities.getArrival();
        if (arrival != null) {
            Double value = arrival.getAzimuth();
            line4.setAzimuthAtSource(value != null && !value.isNaN() ? Double.toString(value) : null);

            if (line4.getAzimuthAtSource().contains(".")) {
                Double d = Double.parseDouble(line4.getAzimuthAtSource());
                Integer intValue = (int) Math.round(d);
                line4.setAzimuthAtSource(intValue.toString());
            }
        }
    }

}
