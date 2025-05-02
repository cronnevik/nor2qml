package no.nnsn.seisanquakeml.models.sfile;

public enum SfileVersionLine7 {
    VERSION1_OLD(" STAT SP IPHASW D HRMM SECON CODA AMPLIT PERI AZIMU VELO SNR AR TRES W  DIS CAZ7"),
    VERSION1(" STAT SP IPHASW D HRMM SECON CODA AMPLIT PERI AZIMU VELO AIN AR TRES W  DIS CAZ7"),
    VERSION2(" STAT COM NTLO IPHASE   W HHMM SS.SSS   PAR1  PAR2 AGA OPE  AIN  RES W  DIS CAZ7");

    SfileVersionLine7(String value) { this.value = value; }

    private final String value;

    public static SfileVersionLine7 hasType(String loc) {
        for (SfileVersionLine7 l: values()) {
            if (l.toString().equals(loc)) {
                return l;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
