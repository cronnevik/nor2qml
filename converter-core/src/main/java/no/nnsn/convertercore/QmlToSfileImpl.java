package no.nnsn.convertercore;

import no.nnsn.convertercore.errors.IgnoredQmlError;
import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.NordicFormatVersion;
import no.nnsn.convertercore.helpers.SfileOverview;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.interfaces.NordicMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import no.nnsn.seisanquakemljpa.models.sfile.SfileData;
import no.nnsn.seisanquakemljpa.models.sfile.SfileVersionLine7;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.*;
import no.nnsn.seisanquakemljpa.models.sfile.v1.SfileDataImpl;
import no.nnsn.seisanquakemljpa.models.sfile.v1.utils.checkers.Line4Checker;
import no.nnsn.seisanquakemljpa.models.sfile.v2.SfileDataDtoImpl;
import no.nnsn.seisanquakemljpa.models.sfile.v2.lines.Line4Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class QmlToSfileImpl implements QmlToSfile {

    final NordicMapper mapper;

    @Autowired
    public QmlToSfileImpl(NordicMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public SfileOverview convertToSfiles(List<Event> events, CallerType caller, NordicFormatVersion nordicFormatVersion) {
        String sfileText = new String();
        int eventCount = 0;
        List<IgnoredQmlError> errors = new ArrayList<>();

        for (Event ev: events) {
            eventCount++;
            Sfile sfile = new Sfile();
            SfileData sfileData;

            if (nordicFormatVersion == NordicFormatVersion.VERSION2) {
                sfileData = new SfileDataDtoImpl();
            } else {
                sfileData = new SfileDataImpl();
            }


            // Set preferred origin as first origin element in list
            List<Origin> tempOrgList = ev.getOrigin();
            if (ev.getPreferredOriginID() != null) {
                // Check if preferred origin is not already the first origin in list
                if (!tempOrgList.get(0).getPublicID().equals(ev.getPreferredOriginID())) {
                    for (Origin temp: tempOrgList) {
                        if (temp.getPublicID().equals(ev.getPreferredOriginID())) {
                            Collections.swap(tempOrgList, 0, tempOrgList.indexOf(temp));
                        }
                    }
                    ev.setOrigin(tempOrgList);
                }
            }

            // Map Line 1s (QuakeML => Nordic format)
            final List<Object> l1Objects = mapper.mapLine1s(ev);
            if (l1Objects != null && l1Objects.size() > 0) {
                l1Objects.forEach(o -> {
                    if (o instanceof Line1) {
                        sfileData.addLine1(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map Line 3s
            final List<Object> l3Objects = mapper.mapLine3s(ev);
            if (l3Objects != null && l3Objects.size() > 0) {
                l3Objects.forEach(o -> {
                    if (o instanceof Line3) {
                        sfileData.addLine3(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map Line 4s
            final List<Object> l4Objects = mapper.mapLine4s(nordicFormatVersion, ev.getPick(), ev.getAmplitude(), ev.getOrigin());

            if (nordicFormatVersion == NordicFormatVersion.VERSION1) {
                if (l4Objects != null && l4Objects.size() > 0) {
                    l4Objects.forEach(o -> {
                        if (o instanceof Line4) {
                            sfileData.addLine4(o);
                        } else if (o instanceof IgnoredQmlError) {
                            IgnoredQmlError err = (IgnoredQmlError) o;
                            err.setEventPublicID(ev.getPublicID());
                            errors.add(err);
                        }
                    });

                    if (sfile.getData() != null) {
                        if (sfile.getData().getLine4s() != null) {
                            // Sort Line4s based on distance
                            Collections.sort(
                                    sfile.getData().getLine4s(),
                                    Comparator.comparing(
                                            Line4::getEpiDistInt,
                                            Comparator.naturalOrder()
                                    )
                            );
                        }
                    }
                }
            } else if (nordicFormatVersion == NordicFormatVersion.VERSION2) {
                List<Line4Dto> line4s = new ArrayList<>();
                if (l4Objects != null && l4Objects.size() > 0) {
                    l4Objects.forEach(o -> {
                        if (o instanceof Line4Dto) {
                            sfileData.addLine4(o);
                        } else if (o instanceof IgnoredQmlError) {
                            IgnoredQmlError err = (IgnoredQmlError) o;
                            err.setEventPublicID(ev.getPublicID());
                            errors.add(err);
                        }
                    });

                    if (sfile.getData() != null) {
                        if (sfile.getData().getLine4s() != null) {
                            // Sort Line4s based on distance
                            Collections.sort(
                                    sfile.getData().getLine4s(),
                                    Comparator.comparing(
                                            Line4Dto::getEpiDistInt,
                                            Comparator.naturalOrder()
                                    )
                            );
                        }
                    }
                }
            }


            // Map Line 6s
            final List<Object> l6Objects = mapper.mapLine6s(ev);
            if (l6Objects != null && l6Objects.size() > 0) {
                l6Objects.forEach(o -> {
                    if (o instanceof Line6) {
                       sfileData.addLine6(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map Line Es
            final List<Object> lEObjects = mapper.mapLineEs(ev.getOrigin());
            if (lEObjects != null && lEObjects.size() > 0) {
                lEObjects.forEach(o -> {
                    if (o instanceof LineE) {
                        sfileData.addLineE(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map line Fs
            final List<Object> lFObjects = mapper.mapLineFs(ev.getFocalMechanism());
            if (lFObjects != null && lFObjects.size() > 0) {
                lFObjects.forEach(o -> {
                    if (o instanceof LineF) {
                        sfileData.addLineF(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map line Is
            final List<Object> lIObjects = mapper.mapLineIs(ev);
            if (lIObjects != null && lIObjects.size() > 0) {
                lIObjects.forEach(o -> {
                    if (o instanceof LineI) {
                        sfileData.addLineI(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map Line M1s
            final List<Object> lM1Objects = mapper.mapLineM1s(ev.getOrigin(), ev.getFocalMechanism(), ev.getMagnitude());
            if (lM1Objects != null && lM1Objects.size() > 0) {
                lM1Objects.forEach(o -> {
                    if (o instanceof LineM1) {
                        sfileData.addLineM1(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map line M2s
            final List<Object> lM2Objects = mapper.mapLineM2s(ev.getFocalMechanism());
            if (lM2Objects != null && lM2Objects.size() > 0) {
                lM2Objects.forEach(o -> {
                    if (o instanceof LineM2) {
                       sfileData.addLineM2(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            // Map Line Ss
            final List<Object> lSObjects = mapper.mapLineS(ev);
            if (lSObjects != null && lSObjects.size() > 0) {
                lSObjects.forEach(o -> {
                    if (o instanceof LineS) {
                        sfileData.addLineS(o);
                    } else if (o instanceof IgnoredQmlError) {
                        IgnoredQmlError err = (IgnoredQmlError) o;
                        err.setEventPublicID(ev.getPublicID());
                        errors.add(err);
                    }
                });
            }

            sfile.setData(sfileData);


            if (caller.equals(CallerType.STANDALONE)) {
                Line1 line1 = (Line1) sfileData.getLine1s().get(0);
                System.out.println("*** Event:" + line1.createLine());
            }

            // loop through each line 1 and join to a string value
            for (Line1 line1: (List<Line1>) sfileData.getLine1s()) {
                sfileText += line1.createLine() + System.lineSeparator();
            }

            // attach lineE
            if (sfileData.getLineEs() != null) {
                List<LineE> lES = sfileData.getLineEs();
                if (lES.size() > 0) {
                    // only print out the first E line as Seisan only accept one
                    LineE lineE = lES.get(0);
                    sfileText += lineE.createLine() + System.lineSeparator();
                }
            }

            if (sfileData.getLineFs() != null) {
                for (LineF linef: (List<LineF>) sfileData.getLineFs()) {
                    sfileText += linef.createLine() + System.lineSeparator();
                }
            }
            if (sfileData.getLineM2s() != null) {
                for (LineM2 lineM2: (List<LineM2>) sfileData.getLineM2s()) {
                    boolean hasOrigin = false;
                    String lineM2OrgID = lineM2.getOrgID();
                    if (sfileData.getLineM1s() != null) {
                        for (LineM1 lineM1: (List<LineM1>) sfileData.getLineM1s()) {
                            String lineM1OrgID = lineM1.getOrgID();
                            if (lineM1OrgID.equals(lineM2OrgID)) {
                                sfileText += lineM1.createLine() + System.lineSeparator();
                                hasOrigin = true;
                            }
                        }
                    }
                    // Only print out an lineM2 if the momentTensor has a relating origin to make a lineM1
                    if (hasOrigin) {
                        sfileText += lineM2.createLine() + System.lineSeparator();
                    }
                }
            }

            // loop through each line 6 and join to a string value
            if (sfileData.getLine6s() != null) {
                for (Line6 line6: (List<Line6>) sfileData.getLine6s()) {
                    sfileText += line6.createLine() + System.lineSeparator();
                }
            }
            // loop through each line I and join to a string value
            if (sfileData.getLineIs() != null) {
                for (LineI lineI: (List<LineI>) sfileData.getLineIs()) {
                    sfileText += lineI.createLine() + System.lineSeparator();
                }
            }
            // loop through each line S and join to a string value
            if (sfileData.getLineSs() != null) {
                for (LineS lineS: (List<LineS>) sfileData.getLineSs()) {
                    sfileText += lineS.createLine() + System.lineSeparator();
                }
            }
            // loop through each line 3 and join to a string value
            if (sfileData.getLine3s() != null) {
                for (Line3 line3: (List<Line3>) sfileData.getLine3s()) {
                    sfileText += line3.createLine() + System.lineSeparator();
                }
            }

            // Set Header info from Line7 - Should always be present
            if (nordicFormatVersion == NordicFormatVersion.VERSION2) {
                sfileText += SfileVersionLine7.VERSION2 + System.lineSeparator();
            } else {
                sfileText += SfileVersionLine7.VERSION1 + System.lineSeparator();
            }


            // loop through each line 4 and join to a string value
            if (sfileData.getLine4s() != null) {
                if (nordicFormatVersion == NordicFormatVersion.VERSION2) {
                    for (Line4Dto line4Dto: (List<Line4Dto>) sfileData.getLine4s()) {
                        sfileText += line4Dto.createLine() + System.lineSeparator();
                    }
                } else {
                    for (Line4 line4: (List<Line4>) sfileData.getLine4s()) {
                        // Check the values of Line4
                        Line4 l4Checked = Line4Checker.checkLine4Values(line4);
                        sfileText += l4Checked.createLine() + System.lineSeparator();
                    }
                }
            }

            // New line (Separator) for each event
            sfileText += System.lineSeparator();

        }

        return new SfileOverview(sfileText, errors);
    }


}
