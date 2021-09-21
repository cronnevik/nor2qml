package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic;

import no.nnsn.convertercore.helpers.Line4Entities;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.GeneralLineHelper;
import no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers.LineHelper;
import no.nnsn.convertercore.mappers.utils.AmplitudeUnitConverter;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.Waveform.WaveformStreamID;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.AmplitudeUnit;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickOnset;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.PickPolarity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.Comment;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.resourcemetadata.CreationInfo;
import no.nnsn.seisanquakemljpa.models.sfile.v1.enums.PropertyIdType;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        uses = {GeneralLineHelper.class})
public abstract class Line4DtoMapper {

    public static Line4DtoMapper INSTANCE = Mappers.getMapper(Line4DtoMapper.class);

    // Pick Entity Mappings
    @Mappings({
            // Pick Entity Mappings
            @Mapping(target = "stationName", ignore = true), // AfterMapping
            @Mapping(target = "component", ignore = true), // AfterMapping
            @Mapping(target = "networkCode", ignore = true), // AfterMapping
            @Mapping(target = "location", ignore = true), // AfterMapping

            @Mapping(target = "qualityIndicator", ignore = true), // AfterMapping
            @Mapping(target = "phaseID", ignore = true), // AfterMapping
            @Mapping(target = "hour", ignore = true), // AfterMapping
            @Mapping(target = "minutes", ignore = true), // AfterMapping
            @Mapping(target = "seconds", ignore = true), // AfterMapping

            // Common
            @Mapping(target = "parameterOne", ignore = true), // AfterMapping
            @Mapping(target = "parameterTwo", ignore = true), // AfterMapping
            @Mapping(target = "agency", ignore = true), // AfterMapping
            @Mapping(target = "operator", ignore = true), // AfterMapping

            // Amplitude Entity Mappings - Currently part of common values

            // Arrival Entity Mappings
            @Mapping(target = "weightingIndicator", ignore = true ), // AfterMapping
            @Mapping(target = "angleOfIncidence", ignore = true), // AfterMapping
            @Mapping(target = "residual", ignore = true), // AfterMapping
            @Mapping(target = "epicentralDistance", ignore = true), // AfterMapping
            @Mapping(target = "azimuthAtSource", ignore = true), // AfterMapping
    })
    public abstract Line4Dto mapLine4Dto(Line4Entities l4Entities);

    /*
     * Pick AfterMappings
     *
     */

    @AfterMapping
    protected void setStationComponentNetworkLocation(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        String stationCode = null;
        String channelCode = null;
        String networkCode = null;
        String locationCode = null;

        WaveformStreamID waveformID = null;

        if (hasPick(l4Entities)) {
            waveformID = l4Entities.getPick().getWaveformID();
        } else if (l4Entities.getAmplitude() != null) {
            Amplitude amp = l4Entities.getAmplitude();
            if (amp.getWaveformID() != null) {
                waveformID = amp.getWaveformID();
            }
        }

        if (waveformID != null) {
            stationCode = waveformID.getStationCode();
            channelCode = waveformID.getChannelCode();
            networkCode = waveformID.getNetworkCode();
            locationCode = waveformID.getLocationCode();

            if (stationCode != null) {
                line4Dto.setStationName(stationCode);
            }
            if (channelCode != null) {
                line4Dto.setComponent(channelCode);
            }
            if (networkCode != null) {
                line4Dto.setNetworkCode(networkCode);
            }
            if (locationCode != null) {
                line4Dto.setLocation(locationCode);
            }

        }

    }

    @AfterMapping
    protected void setQualityIndicator(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasPick(l4Entities)) {
            Pick pick = l4Entities.getPick();
            if (pick.getOnset() != null) {
                PickOnset onset = pick.getOnset();
                switch (onset) {
                    case IMPULSIVE:
                        line4Dto.setQualityIndicator("I");
                        break;
                    case EMERGENT:
                        line4Dto.setQualityIndicator("E");
                        break;
                    default:
                        line4Dto.setQualityIndicator(" ");
                        break;
                }
            }
        }
    }

    @AfterMapping
    protected void setPhaseID(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        String phase = null;
        if (hasPick(l4Entities)) {
            Pick pick = l4Entities.getPick();
            if (pick.getPhaseHint() != null) {
                phase = pick.getPhaseHint();
            } else {
                Arrival arrival = l4Entities.getArrival();
                if (arrival != null) {
                    if (arrival.getPhase() != null) {
                        phase = arrival.getPhase();
                    }
                }
            }
        }

        if (phase != null) {
            line4Dto.setPhaseID(phase);
        }
    }

    @AfterMapping
    protected void setHourMinSec(@MappingTarget Line4Dto line4Dto, Line4Entities line4Entities) {
        if (hasPick(line4Entities)) {
            Pick pick = line4Entities.getPick();
            if (pick.getTime() != null) {
                String timeString = line4Entities.getPick().getTime().getValue();
                // Zoned DateTime (e.g. 2015-11-30T00:00:15.50Z)
                if(timeString.substring(timeString.length() -1 ).equals("Z")) {
                    ZonedDateTime timeZoned = ZonedDateTime.parse(timeString);
                    if (pick.getTimeOverMidnight()) {
                        int hour = timeZoned.getHour();
                        line4Dto.setHour(Integer.toString(hour + (24-hour)));
                    } else {
                        line4Dto.setHour(Integer.toString(timeZoned.getHour()));
                    }

                    line4Dto.setMinutes(Integer.toString(timeZoned.getMinute()));

                    Double secAndMillSec = Double.valueOf(timeZoned.getSecond()) + (Double.valueOf(timeZoned.getNano()) / Math.pow(10,9));
                    line4Dto.setSeconds(secAndMillSec.toString());
                }
                // Default DateTime (e.g. 2012-05-29T23:58:58.770000)
                else {
                    LocalDateTime time = LocalDateTime.parse(timeString);
                    if (pick.getTimeOverMidnight()) {
                        int hour = time.getHour();
                        line4Dto.setHour(Integer.toString(hour + (24-hour)));
                    } else {
                        line4Dto.setHour(Integer.toString(time.getHour()));
                    }
                    line4Dto.setMinutes(Integer.toString(time.getMinute()));

                    Double secAndMillSec = Double.valueOf(time.getSecond()) + (Double.valueOf(time.getNano()) / Math.pow(10,9));
                    line4Dto.setSeconds(secAndMillSec.toString());
                }
            }
        }
    }

    /*
     * Common AfterMappings
     *
     */

    @AfterMapping
    protected void setParameterOne(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        boolean pOneIsSet = false;
        if (hasPick(l4Entities)) {
            Pick pick = l4Entities.getPick();
            if (pick.getBackazimuth() != null) {
                RealQuantity backazimuth = pick.getBackazimuth();
                if (backazimuth.getValue() != null) {
                    Double backAzimuthValue = backazimuth.getValue();
                    line4Dto.setParameterOne(backAzimuthValue.toString());
                    pOneIsSet = true;
                }
            }

            if (pick.getPolarity() != null) {
                PickPolarity polarity = pick.getPolarity();
                if (polarity != null) {
                    if (pOneIsSet && (polarity != PickPolarity.UNDECIDABLE)) {
                        System.out.println("Found polarity, but parameter one is already set by backAzimuth");
                    } else if (polarity == PickPolarity.POSITIVE) {
                        line4Dto.setParameterOne("C");
                    } else if (polarity == PickPolarity.NEGATIVE) {
                        line4Dto.setParameterOne("D");
                    }
                }
            }
        }

        if (hasAmplitude(l4Entities)) {
            Amplitude amplitude = l4Entities.getAmplitude();
            if (amplitude.getGenericAmplitude() != null) {
                RealQuantity genericAmplitude = amplitude.getGenericAmplitude();
                if (genericAmplitude.getValue() != null) {

                    // Generic amplitude can be either Amplitude or Duration
                    // Use Amplitude category or phaseHint to identify Amplitude
                    ParameterOneType type = null;
                    if (amplitude.getType() != null) {
                        String ampType = amplitude.getType();
                        type = PhaseParameters.identifyParameterOneType(null, ampType);
                    } else {
                        if (l4Entities.getPick() != null) {
                            if (l4Entities.getPick().getPhaseHint() != null) {
                                String phaseHint = l4Entities.getPick().getPhaseHint();
                                type = PhaseParameters.identifyParameterOneType(null, phaseHint);
                            }
                        }
                    }

                    if (type == ParameterOneType.AMPLITUDE) {
                        String ampStringValue = genericAmplitude.getValue().toString();
                        AmplitudeUnit ampUnit = amplitude.getUnit();
                        // Set value if Unit and specified and convert to corresponding Nordic unit
                        if (ampUnit != null) {
                            switch (ampUnit) {
                                case Meter:
                                    line4Dto.setParameterOne(AmplitudeUnitConverter.fromMToNm(ampStringValue));
                                    break;
                                case METERS_PER_SECOND:
                                    line4Dto.setParameterOne(AmplitudeUnitConverter.fromMsToNms(ampStringValue));
                                    break;
                                default:
                                    break;
                            }
                        }
                    } else if (type == ParameterOneType.DURATION) {
                        String ampStringValue = genericAmplitude.getValue().toString();
                        line4Dto.setParameterOne(ampStringValue);
                    }
                }
            }

        }
    }

    @AfterMapping
    protected void setParameterTwo(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {

        Boolean hasParameterAmplitude = false;
        Boolean hasParameterBackAzimuth = false;

        // Check for amplitude
        if (hasAmplitude(l4Entities)) {
            Amplitude amplitude = l4Entities.getAmplitude();
            if (amplitude.getGenericAmplitude() != null) {
                RealQuantity genericAmplitude = amplitude.getGenericAmplitude();
                if (genericAmplitude.getValue() != null) {

                    // Generic amplitude can be either Amplitude or Duration
                    // Use Amplitude category or phaseHint to identify Amplitude
                    ParameterOneType type = null;
                    if (amplitude.getType() != null) {
                        String ampType = amplitude.getType();
                        type = PhaseParameters.identifyParameterOneType(null, ampType);
                    } else {
                        if (l4Entities.getPick() != null) {
                            if (l4Entities.getPick().getPhaseHint() != null) {
                                String phaseHint = l4Entities.getPick().getPhaseHint();
                                type = PhaseParameters.identifyParameterOneType(null, phaseHint);
                            }
                        }
                    }

                    if (type == ParameterOneType.AMPLITUDE) {
                        hasParameterAmplitude = true;
                    }
                }
            }
        }

        // Check for backAzimuth
        if (hasPick(l4Entities)) {
            if (l4Entities.getPick().getPhaseHint() != null) {
                String phaseHint = l4Entities.getPick().getPhaseHint();
                ParameterOneType type = PhaseParameters.identifyParameterOneType(null, phaseHint);
                if (type == ParameterOneType.BACK_AZIMUTH) {
                    hasParameterBackAzimuth = true;
                }
            }
        }

        // If Amplitude, paramTwo should be period
        if (hasParameterAmplitude) {
            if (hasAmplitude(l4Entities)) {
                Amplitude amplitude = l4Entities.getAmplitude();
                if (amplitude.getPeriod() != null) {
                    RealQuantity period = amplitude.getPeriod();
                    if (period.getValue() != null) {
                        line4Dto.setParameterTwo(period.getValue().toString());
                    }
                }
            }
        }

        // If Backazimuth, paramTwo should be apparent velocity(km/s)
        if (hasParameterBackAzimuth) {
            if (hasPick(l4Entities)) {
                Pick pick = l4Entities.getPick();
                if (pick.getHorizontalSlowness() != null) {
                    RealQuantity horizontalSlowness = pick.getHorizontalSlowness();
                    if (horizontalSlowness.getValue() != null) {
                        Double phaseVelNum = horizontalSlowness.getValue();
                        Double newPhaseVelNum = 1 / (phaseVelNum / 111.2); // s/deg to km/s (1/x --> s/km to km/s)
                        line4Dto.setParameterTwo(newPhaseVelNum.toString());
                    }
                }
            }
        }
    }

    @AfterMapping
    protected void setAgencyAndOperator(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        String agency = null;
        String operator = null;

        // Check if values are found in Pick object
        if (hasPick(l4Entities)) {
            if (l4Entities.getPick().getCreationInfo() != null) {
                CreationInfo creationInfo = l4Entities.getPick().getCreationInfo();
                if (creationInfo.getAgencyID() != null) {
                    agency = creationInfo.getAgencyID();
                }
                if (creationInfo.getAuthor() != null) {
                    operator = creationInfo.getAuthor();
                }
            }
        }

        // If not in Pick, check if values are found in Amplitude object
        if (hasAmplitude(l4Entities)) {
            if (l4Entities.getAmplitude().getCreationInfo() != null) {
                CreationInfo creationInfo = l4Entities.getAmplitude().getCreationInfo();
                if (agency == null && creationInfo.getAgencyID() != null) {
                    agency = creationInfo.getAgencyID();
                }
                if (operator == null && creationInfo.getAuthor() != null) {
                    operator = creationInfo.getAuthor();
                }
            }
        }

        // If not in Pick and Amplitude, check if values are found in Arrival object
        if (hasArrival(l4Entities)) {
            if (l4Entities.getArrival().getCreationInfo() != null) {
                CreationInfo creationInfo = l4Entities.getArrival().getCreationInfo();
                if (agency == null && creationInfo.getAgencyID() != null) {
                    agency = creationInfo.getAgencyID();
                }
                if (operator == null && creationInfo.getAuthor() != null) {
                    operator = creationInfo.getAuthor();
                }
            }
        }

       if (agency != null) {
           line4Dto.setAgency(agency);
       }
       if (operator != null) {
           line4Dto.setOperator(operator);
       }
    }

    /*
     * Arrival AfterMappings
     *
     */

    @AfterMapping
    protected void setWeightingIndicator(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            Arrival arrival = l4Entities.getArrival();
            if (arrival.getTimeWeight() != null) {
                Double timeWeight = arrival.getTimeWeight();
                // QuakeML (0.5) => Nordic (05)
                Double nordicTimeWeight = timeWeight * 10;
                line4Dto.setWeightingIndicator(nordicTimeWeight.toString());
            }
        }
    }

    @AfterMapping
    protected void setAngleOfIncidence(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            String value = LineHelper.getRealQuantityValue(l4Entities.getArrival().getTakeoffAngle());
            if (value != null) {
                line4Dto.setAngleOfIncidence(value);
            }
        }
    }

    @AfterMapping
    protected void setResidual(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            Arrival arrival = l4Entities.getArrival();

            // Get phaseID to identify which residual that should be converted
            String phaseID = null;

            // First check if phaseID is within Pick entity
            if (l4Entities.getPick() != null) {
                Pick pick = l4Entities.getPick();
                if (pick.getPhaseHint() != null) {
                    phaseID = pick.getPhaseHint();
                }
            }
            // If not, check within Arrival entity
            if (phaseID == null) {
                if (arrival.getPhase() != null) {
                    phaseID = arrival.getPhase();
                }
            }

            ParameterOneType pOneType = PhaseParameters.identifyParameterOneType(null, phaseID);
            Double residualValue = null;

            if (pOneType == ParameterOneType.BACK_AZIMUTH) {
                residualValue = arrival.getBackazimuthResidual();
            }

            // Magnitude - END phases might have a magnitude residual
            if (pOneType == ParameterOneType.DURATION) {
                // TODO
            }

            // Set travel time (Phases - not amplitude, BAZ or END)
            if (pOneType != ParameterOneType.AMPLITUDE || pOneType != ParameterOneType.BACK_AZIMUTH || pOneType != ParameterOneType.DURATION) {
                residualValue = arrival.getTimeResidual();
            }

            if (residualValue != null) {
                line4Dto.setResidual(residualValue.toString());
            }

        }
    }

    @AfterMapping
    protected void setWeight(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            if (l4Entities.getArrival().getComment() != null) {
                List<Comment> comments = l4Entities.getArrival().getComment();
                comments.forEach(comment -> {
                    if (PropertyIdType.PROPERTY_WEIGHT.equalValue(comment.getId())) {
                        String[] weightSplit = comment.getText().split("\\.");
                        line4Dto.setWeight(weightSplit[0] + weightSplit[1]);
                    };
                });
            }
        }
    }

    @AfterMapping
    protected void setEpicentralDistance(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            Arrival arrival = l4Entities.getArrival();
            if (arrival.getDistance() != null) {
                Double epDist = arrival.getDistance();
                // Convert from degrees to km
                Double newDist = epDist * 111.2;
                line4Dto.setEpicentralDistance(newDist.toString());
            }
        }
    }

    @AfterMapping
    protected void setAzimuthAtSource(@MappingTarget Line4Dto line4Dto, Line4Entities l4Entities) {
        if (hasArrival(l4Entities)) {
            Arrival arrival = l4Entities.getArrival();
            Double value = arrival.getAzimuth();
            Integer intValue = (int) Math.round(value);
            line4Dto.setAzimuthAtSource(intValue != null && !value.isNaN() ? intValue.toString() : null);
        }
    }

    private Boolean hasPick(Line4Entities l4Entities) {
        return l4Entities.getPick() != null ? true : false;
    }

    private Boolean hasAmplitude(Line4Entities l4Entities) {
        return l4Entities.getAmplitude() != null ? true : false;
    }

    private Boolean hasArrival(Line4Entities l4Entities) {
        return l4Entities.getArrival() != null ? true : false;
    }
}
