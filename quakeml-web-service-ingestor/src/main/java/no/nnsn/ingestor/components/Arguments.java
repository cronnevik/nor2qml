package no.nnsn.ingestor.components;

import no.nnsn.convertercore.helpers.ConverterProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Arguments {

    @Autowired
    Environment env;

    public String getCurrentPath() {
        return System.getProperty("user.dir");
    }
    public Boolean hasInput() {
        return !env.getProperty("ingestor.input").equals("false");
    }
    public String getInput() { return env.getProperty("ingestor.input"); }
    public String getSourceType() { return env.getProperty("ingestor.source"); }
    public String getQmlPrefix() {
        return env.getProperty("quakeml.prefix");
    }
    public String getQmlAgency() {
        return env.getProperty("quakeml.agency");
    }
    public String getCatalog() { return env.getProperty("ingestor.catalog"); }

    public LocalDate getStartDate() {
        return LocalDate.parse(env.getProperty("ingestor.startdate"));
    }
    public Boolean startCleaner() {return env.getProperty("ingestor.clean").equals("true");}
    public int getChunk() { return Integer.valueOf(env.getProperty("ingestor.chunk")); }
    public Boolean catalogFromPath() {return env.getProperty("ingestor.catalog").equals("default");}
    public Boolean forceIngestion() { return env.getProperty("ingestor.force").equals("true"); }

    // Profiles for specific mappings
    public ConverterProfile getProfile() {
        String profile = env.getProperty("ingestor.profile");
        if (profile.equals("intaros")) {
            return ConverterProfile.INTAROS;
        }
        return null;
    }
}
