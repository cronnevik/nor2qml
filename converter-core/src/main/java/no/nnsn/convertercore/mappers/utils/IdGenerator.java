package no.nnsn.convertercore.mappers.utils;

import lombok.Getter;
import lombok.Setter;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;

import java.security.SecureRandom;

/**
 * ID generator for QuakeML entities following the generic form of:
 * {@literal [smi|quakeml]:<authority-id>/<resource-id>[#local-id] }
 *
 * @author Christian Rønnevik
 */
public class IdGenerator {

    @Getter @Setter
    private String prefix;
    @Getter @Setter
    private String authorityID;
    @Getter @Setter
    private String catalogName;
    @Getter @Setter
    private Catalog catalog;

    private static IdGenerator instance;

    public static IdGenerator getInstance() {
        if (instance == null) {
            instance = new IdGenerator();
        }
        return instance;
    }

    public String genEventParamsPublicID() {
        if (prefix == null) { prefix = "smi"; }
        if (authorityID == null) { authorityID = "authorityID"; }
        if (catalogName == null) {
            catalogName = "eventParams";
        }
        String id =
                this.prefix + ":" +
                this.authorityID + "/" +
                catalogName;
        return id;
    }

    public String genEventParamsPublicID(Catalog catalog) {
        if (catalog != null) {
            String id =
                    catalog.getPrefix() + ":" +
                            catalog.getAuthorityID() + "/" +
                            catalog.getCatalogName();
            return id;
        } else {
            return this.genEventParamsPublicID();
        }

    }

    /**
     * Generates a PublicID for the most entities.
     *
     * @param year String representing year
     * @param stationOrAgency String representing agency
     * @param type Class type
     * @return String
     */
    public String genTypicalPublicID(String year, String stationOrAgency, Class type) {
        if (prefix == null) { prefix = "smi"; }
        if (authorityID == null) { authorityID = "authorityID"; }
        RandomString genString = new RandomString(12, new SecureRandom());
        String id =
            this.prefix + ":" +
            this.authorityID + "/" +
            type.getSimpleName() + "/" + year + "-" + stationOrAgency + // Resource ID
            "-" + genString.nextString(); // Local ID
        return id;
    }

    /**
     * Generates a PublicID for the event entity with a resource-id containing information about the timing of the event.
     *
     * @param eventID String representing the event ID
     * @param type Class type
     * @return String
     */
    public String genEventPublicID(String eventID, Class type) {
        return
            this.prefix + ":" +
            this.authorityID + "/" +
            type.getSimpleName() + "/" + eventID;
    }

    public String genRandomEventID(String year, String month, String day, String hour, String minutes) {
        RandomString genString = new RandomString(6, new SecureRandom());
        return year + month + day + hour + minutes + "_" + genString.nextString();
    }


}
