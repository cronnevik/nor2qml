package no.nnsn.convertercore.mappers.from_qml.v20.to_nordic.helpers;

import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.common.RealQuantity;

public class LineHelper {
    public static String getRealQuantityValue(RealQuantity realQuantity) {
        if (realQuantity != null) {
            Double value = realQuantity.getValue();
            if (value != null) {
                return value != null && !value.isNaN() ? Double.toString(value) : null;
            }
        }
        return null;
    }

    public static String setMagAgencies(String agencyID, String author) {
        if (agencyID != null && !agencyID.trim().isEmpty()) {
            if (agencyID.length() > 3) {
                return agencyID.substring(0, agencyID.length() -1);
            } else {
                return agencyID;
            }
        } else if (author != null && !author.trim().isEmpty()){
            if (author.length() > 3) {
                return author.substring(0, author.length() -1);
            } else {
                return author;
            }
        }
        return null;
    }

    public static String convertMagTypeToNordic(String qmlMagType) {
        String typeValue = "";

        switch (qmlMagType) {
            case "ML":
                typeValue = "L";
                break;
            case "mb":
                typeValue = "b";
                break;
            case "mB":
                typeValue = "B";
                break;
            case "Ms":
                typeValue = "s";
                break;
            case "MS":
                typeValue = "S";
                break;
            case "MW":
                typeValue = "W";
                break;
            case "MbLg":
                typeValue = "G";
                break;
            case "Mc":
            case "MD":
                typeValue = "C";
                break;
            case "MN":
                typeValue = "N";
                break;
            case "Mw":
                typeValue = "w";
                break;
        }

        return typeValue;
    }
}
