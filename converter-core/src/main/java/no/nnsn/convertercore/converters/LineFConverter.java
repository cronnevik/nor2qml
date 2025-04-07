package no.nnsn.convertercore.converters;

import no.nnsn.convertercore.errors.IgnoredLineError;
import no.nnsn.convertercore.helpers.SfileInfo;
import no.nnsn.convertercore.helpers.collections.LineFQuakemlEntities;
import no.nnsn.convertercore.mappers.interfaces.QmlMapper;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.FocalMechanism;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Magnitude;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.MomentTensor;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Origin;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.Line1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineF;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM1;
import no.nnsn.seisanquakemljpa.models.sfile.v1.lines.LineM2;

import java.util.ArrayList;
import java.util.List;

public class LineFConverter {

    private final List<Line1> line1s;
    private final List<LineF> lineFs;
    private final List<LineM2> lineM2s;
    private final SfileInfo sfileInfo;

    public LineFConverter(List<Line1> line1s, List<LineF> lineFs, List<LineM2> lineM2s, SfileInfo sfileInfo) {
        this.line1s = line1s;
        this.lineFs = lineFs;
        this.lineM2s = lineM2s;
        this.sfileInfo = sfileInfo;
    }

    public LineFQuakemlEntities convert(QmlMapper mapper) {
        String preferredFocalMechanismID = null;
        List<FocalMechanism> focalMechanisms = new ArrayList<>();
        List<Origin> lm1Origins = new ArrayList<>();
        List<Magnitude> lm1Magnitudes = new ArrayList<>();
        List<IgnoredLineError> errors = new ArrayList<>();

        if (line1s != null && lineFs != null) {
            line1Loop:
            for (Line1 line1 : line1s) {
                // Map FocalMechanism
                LineFLoop:
                for (LineF lineF : lineFs) {
                    Line1 l1Comparable = lineF.getRelatedLine1();
                    if (l1Comparable != null) {
                        // Check if FocalMechanism is related to current line1
                        if (line1.equals(l1Comparable)) {
                            FocalMechanism focalMechanism;
                            Object fObj = mapper.mapLine1FocalMechanisms(lineF);
                            if (fObj instanceof FocalMechanism) {
                                focalMechanism = (FocalMechanism) fObj;
                                // Check if the focalmechanism has a related moment tensor
                                if (lineM2s != null && !lineM2s.isEmpty()) {
                                    LineM2 lineM2 = null;

                                    LineM2Loop:
                                    for (LineM2 m2 : lineM2s) {
                                        LineF lFComparable = m2.getRelatedLineF();
                                        if (lineF.equals(lFComparable)) {
                                            // MomentTensor
                                            MomentTensor momentTensor;
                                            Object mtObj = mapper.mapMomentTensor(m2, line1);
                                            if (mtObj instanceof MomentTensor) {
                                                momentTensor = (MomentTensor) mtObj;
                                                LineM1 relatedM1 = m2.getRelatedLineM1();
                                                if (relatedM1 != null) {
                                                    // MomentTensor Origin
                                                    Origin originM1;
                                                    Object oM1Obj = mapper.mapMomentTensorOrigin(relatedM1);
                                                    if (oM1Obj instanceof Origin) {
                                                        originM1 = (Origin) oM1Obj;
                                                        momentTensor.setDerivedOriginID(originM1.getPublicID());
                                                        lm1Origins.add(originM1);

                                                        // MomentTensor Magnitude
                                                        Magnitude magM1;
                                                        Object mM1Obj = mapper.mapMomentTensorMagnitude(relatedM1);
                                                        if (mM1Obj instanceof Magnitude) {
                                                            magM1 = (Magnitude) mM1Obj;
                                                            magM1.setOriginID(originM1.getPublicID());
                                                            lm1Magnitudes.add(magM1);
                                                        } else if (mM1Obj instanceof IgnoredLineError) {
                                                            IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                            e.setFilename(sfileInfo.getFilename());
                                                            errors.add(e);
                                                        }
                                                    } else if (oM1Obj instanceof IgnoredLineError) {
                                                        IgnoredLineError e = (IgnoredLineError) oM1Obj;
                                                        e.setFilename(sfileInfo.getFilename());
                                                        errors.add(e);
                                                    }
                                                }
                                                focalMechanism.setMomentTensor(momentTensor);
                                            } else if (mtObj instanceof IgnoredLineError) {
                                                IgnoredLineError e = (IgnoredLineError) mtObj;
                                                e.setFilename(sfileInfo.getFilename());
                                                errors.add(e);
                                            }
                                        }
                                    }
                                }
                                focalMechanisms.add(focalMechanism);
                            } else if (fObj instanceof IgnoredLineError) {
                                IgnoredLineError e = (IgnoredLineError) fObj;
                                e.setFilename(sfileInfo.getFilename());
                                errors.add(e);
                            }
                        }
                    }
                }
            }
            // Determine Preferred FocalMechanism ID
            if (!focalMechanisms.isEmpty()) {
                // First one listed should be the preferred
                FocalMechanism firstFocalMechListed = focalMechanisms.get(0);
                preferredFocalMechanismID = firstFocalMechListed.getPublicID();
            }

            return new LineFQuakemlEntities(preferredFocalMechanismID, lm1Origins,focalMechanisms, lm1Magnitudes, errors);
        }
        return new LineFQuakemlEntities();
    }
}
