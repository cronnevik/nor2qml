package no.nnsn.quakemlwebservice.controller;

import no.nnsn.quakemlwebservice.dao.Contributors;
import no.nnsn.quakemlwebservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Component
@Path("/contributors")
public class ContributorController {

    final CatalogService catalogService;

    public ContributorController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Contributors getContributors() {
        Contributors contributors = new Contributors();
        contributors.setContributors(catalogService.getContributors());
        return contributors;
    }
}
