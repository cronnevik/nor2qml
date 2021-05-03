package no.nnsn.seisanquakemljpa.models.sfile.v1.utils;

import java.util.List;

public class ValueSetter {
    public static void setPropObjValues(List<PropertyObject> fields, String line) {
        for(PropertyObject obj : fields) {
            StringBuilder value = new StringBuilder();
            for(int i = (obj.getColumnStart() - 1); i < obj.getColumnEnd(); i++) {
                value.append(line.charAt(i));
            }
            obj.setValue(value.toString().trim());
        }
    }
}
