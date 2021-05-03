package no.nnsn.quakemlwebservice.config;

import org.glassfish.jersey.server.wadl.config.WadlGeneratorConfig;
import org.glassfish.jersey.server.wadl.config.WadlGeneratorDescription;
import org.glassfish.jersey.server.wadl.internal.generators.WadlGeneratorApplicationDoc;

import java.util.List;

public class CustomWADLGenerator extends WadlGeneratorConfig {

    @Override
    public List<WadlGeneratorDescription> configure() {
        return generator(WadlGeneratorApplicationDoc.class)
                .prop("applicationDocsStream", "application-doc.xml")
                .generator(OptionsWadlGenerator.class).descriptions();
    }
}
