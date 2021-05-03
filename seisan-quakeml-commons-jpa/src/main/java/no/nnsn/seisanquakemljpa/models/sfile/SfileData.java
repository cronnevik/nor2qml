package no.nnsn.seisanquakemljpa.models.sfile;

import java.util.ArrayList;
import java.util.List;

public abstract class SfileData {

    private List<String> unmapped;

    public abstract List getLine1s();
    public abstract void addLine1(Object o);
    public abstract List getLine2s();
    public abstract void addLine2(Object o);
    public abstract List getLine3s();
    public abstract void addLine3(Object o);
    public abstract List getLine4s();
    public abstract void addLine4(Object o);
    public abstract List getLine5s();
    public abstract void addLine5(Object o);
    public abstract List getLine6s();
    public abstract void addLine6(Object o);
    public abstract List getLineEs();
    public abstract void addLineE(Object o);
    public abstract List getLineFs();
    public abstract void addLineF(Object o);
    public abstract List getLineHs();
    public abstract void addLineH(Object o);
    public abstract List getLineIs();
    public abstract void addLineI(Object o);
    public abstract List getLineM1s();
    public abstract void addLineM1(Object o);
    public abstract List getLineM2s();
    public abstract void addLineM2(Object o);
    public abstract List getLineSs();
    public abstract void addLineS(Object o);

    public List<String> getUnmapped() {
        return this.unmapped;
    }
    public void addUnmapped(String line) {
        if (this.unmapped == null) {
            this.unmapped = new ArrayList<>();
        }
        this.unmapped.add(line);
    }

    public void clearUnmappedList() {this.unmapped = new ArrayList<>();}
    public abstract void clearLineLists();
}
