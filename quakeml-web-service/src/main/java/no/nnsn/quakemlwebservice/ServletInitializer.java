package no.nnsn.quakemlwebservice;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(QuakemlWebServiceApplication.class);
    }
/*
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter(
                "com.sun.jersey.config.property.WadlGeneratorConfig",
                "no.nnsn.quakemlwebservice.config.CustomWADLGenerator"
        );
        servletContext.setInitParameter(
                "com.sun.jersey.config.property.packages",
                "no.nnsn.quakemlwebservice"
        );
        super.onStartup(servletContext);
    }
*/
}
