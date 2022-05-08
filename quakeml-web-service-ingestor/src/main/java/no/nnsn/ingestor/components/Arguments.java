package no.nnsn.ingestor.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import no.nnsn.convertercore.helpers.ConverterProfile;
import no.nnsn.ingestor.dao.CatalogConfig;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Arguments {

    final
    Environment env;

    ObjectMapper mapper = new ObjectMapper();

    public Arguments(Environment env) {
        this.env = env;
    }

    public String getCurrentPath() {
        return System.getProperty("user.dir");
    }

    public Boolean hasInputFolder() {
        return !env.getProperty("ingestor.folder").equals("false");
    }
    public String getInputFolder() { return env.getProperty("ingestor.folder"); }
    public CatalogConfig[] getCatalogsConfig() throws JsonProcessingException {
        return mapper.readValue(env.getProperty("ingestor.catalogs"), CatalogConfig[].class);
    }
    public String getSourceType() { return env.getProperty("ingestor.source"); }
    public String getCatalog() { return env.getProperty("ingestor.folder"); }

    public List<String> getIgnoreFolders() throws JsonProcessingException {
        return mapper.readValue(env.getProperty("scanner.ignore_folders"), ArrayList.class);
    }

    public String getQmlPrefix() {
        return env.getProperty("quakeml.prefix");
    }
    public String getQmlAgency() {
        return env.getProperty("quakeml.agency");
    }

    public Boolean forceIngestion() { return env.getProperty("ingestor.force").equals("true"); }
    public Boolean isScheduled() { return env.getProperty("scheduler.enabled").equals("true"); }

    // Profiles for specific mappings
    public ConverterProfile getProfile() {
        String profile = env.getProperty("ingestor.profile");
        if (profile.equals("intaros")) {
            return ConverterProfile.INTAROS;
        }
        return null;
    }
}
