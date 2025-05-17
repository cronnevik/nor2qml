package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.ConverterSfileErrorLogging;
import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.Line4QuakemlEntities;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.ParameterOneType;
import no.nnsn.convertercore.mappers.from_nordic.v2.to_qml.v20.utils.PhaseParameters;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Amplitude;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Arrival;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Pick;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakeml.models.sfile.v1.lines.Line4;
import no.nnsn.seisanquakeml.models.sfile.v2.lines.Line4Dto;

import java.util.ArrayList;
import java.util.List;

public class Line4Converter {
    private final List<Line1> line1s;
    private final List<Line> line4s;
    private final SfileInfo sfileInfo;

    public Line4Converter(List<Line1> line1s, List<Line> line4s, SfileInfo sfileInfo) {
        this.line1s = line1s;
        this.line4s = line4s;
        this.sfileInfo = sfileInfo;
    }

    public Line4QuakemlEntities convert(QmlMapper mapper) {
        List<Arrival> arrivals = new ArrayList<>();
        List<Amplitude> amplitudes = new ArrayList<>();
        List<Pick> picks = new ArrayList<>();

        if (line4s != null) {
            for (Line line : line4s) {
                // Map Pick Entity and link its ID to Arrival and Amplitude entities
                Pick pick = null;
                Object pObj = null;
                if (line instanceof Line4) {
                    Line4 l4 = (Line4) line;
                    pObj = mapper.mapPick(l4, null, line1s);
                } else if (line instanceof Line4Dto) {
                    Line4Dto l4 = (Line4Dto) line;
                    pObj = mapper.mapPick(null, l4, line1s);
                }

                if (pObj instanceof Pick) {
                    pick = (Pick) pObj;
                    picks.add(pick);
                } else if (pObj instanceof IgnoredLineError) {
                    IgnoredLineError error = (IgnoredLineError) pObj;
                    error.setFilename(sfileInfo.getFilename());
                    error.setEventNumber(sfileInfo.getEventCount());
                    ConverterSfileErrorLogging.addError(error);
                    continue; // Pick is required fo the Arrival and Amplitude obj
                }

                // Map Arrival Entity
                Arrival arrival;
                Object arrObj = null;
                if (line instanceof Line4) {
                    Line4 l4 = (Line4) line;
                    arrObj = mapper.mapArrival(l4, null, pick, line1s);
                } else if (line instanceof Line4Dto) {
                    Line4Dto l4 = (Line4Dto) line;
                    arrObj = mapper.mapArrival(null, l4, pick, line1s);
                }

                if (arrObj instanceof Arrival) {
                    arrival = (Arrival) arrObj;
                    arrivals.add(arrival);
                } else if (arrObj instanceof IgnoredLineError) {
                    IgnoredLineError error = (IgnoredLineError) arrObj;
                    error.setFilename(sfileInfo.getFilename());
                    error.setEventNumber(sfileInfo.getEventCount());
                    ConverterSfileErrorLogging.addError(error);
                }

                Amplitude amplitude;
                Object ampObj = null;
                // Map Amplitude Entity - Only map if amplitude value exists
                if (line instanceof Line4) {
                    Line4 l4 = (Line4) line;
                    if (l4.getAmplitude() != null) {
                        ampObj = mapper.mapAmplitude(l4, null, pick, line1s);
                    }
                } else if (line instanceof Line4Dto) {
                    Line4Dto l4 = (Line4Dto) line;
                    String parameterOne = l4.getParameterOne();
                    String phaseID = l4.getPhaseID();
                    ParameterOneType parameterOneType = PhaseParameters.identifyParameterOneType(parameterOne, phaseID);
                    if (parameterOneType == ParameterOneType.AMPLITUDE) {
                        ampObj = mapper.mapAmplitude(null, l4, pick, line1s);
                    }
                }

                if (ampObj != null) {
                    if (ampObj instanceof Amplitude) {
                        amplitude = (Amplitude) ampObj;
                        amplitudes.add(amplitude);
                    } else if (ampObj instanceof IgnoredLineError) {
                        IgnoredLineError error = (IgnoredLineError) ampObj;
                        error.setFilename(sfileInfo.getFilename());
                        error.setEventNumber(sfileInfo.getEventCount());
                        ConverterSfileErrorLogging.addError(error);
                    }
                }
            }

            return new Line4QuakemlEntities(picks, amplitudes, arrivals);
        }

        return new Line4QuakemlEntities();
    }
}
