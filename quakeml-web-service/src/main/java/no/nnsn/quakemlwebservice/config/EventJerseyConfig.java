package no.nnsn.quakemlwebservice.config;

import no.nnsn.quakemlwebservice.controller.*;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;


@Configuration
@ApplicationPath("/fdsnws/event/1")
public class EventJerseyConfig extends ResourceConfig {

    public EventJerseyConfig(JerseyProperties jerseyProperties) {
        packages("no.nnsn.quakemlwebservice");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);

        register(EventController.class);
        register(VersionController.class);
        register(CatalogController.class);
        register(ContributorController.class);

        // Template engine
        property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, "pages");
        register(FreemarkerMvcFeature.class);

        property(ServerProperties.MONITORING_ENABLED, true);
        register(JerseyStatisticsController.class);

        //WADL
        property("jersey.config.server.wadl.generatorConfig",
                "no.nnsn.quakemlwebservice.config.CustomWADLGenerator");
    }


}
