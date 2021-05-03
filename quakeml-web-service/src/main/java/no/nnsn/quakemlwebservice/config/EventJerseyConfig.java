package no.nnsn.quakemlwebservice.config;

import no.nnsn.quakemlwebservice.controller.CatalogController;
import no.nnsn.quakemlwebservice.controller.ContributorController;
import no.nnsn.quakemlwebservice.controller.EventController;
import no.nnsn.quakemlwebservice.controller.VersionController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/fdsnws/event/1")
public class EventJerseyConfig extends ResourceConfig {

    public EventJerseyConfig() {
        packages("no.nnsn.quakemlwebservice");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);

        register(EventController.class);
        register(VersionController.class);
        register(CatalogController.class);
        register(ContributorController.class);

        // Template engine
        property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "pages");
        register(FreemarkerMvcFeature.class);

        //WADL
        property("jersey.config.server.wadl.generatorConfig",
                "no.nnsn.quakemlwebservice.config.CustomWADLGenerator");
    }
}
