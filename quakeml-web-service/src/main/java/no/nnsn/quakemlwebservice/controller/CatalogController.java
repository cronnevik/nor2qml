package no.nnsn.quakemlwebservice.controller;

import no.nnsn.quakemlwebservice.helper.CatalogWrapper;
import no.nnsn.quakemlwebservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/catalogs")
public class CatalogController {

    final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GET()
    @Produces(MediaType.APPLICATION_XML)
    public CatalogWrapper getCatalogs() {
        return new CatalogWrapper(catalogService.getCatalogNames());
    }

}
