package no.nnsn.quakemlwebservice.controller;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/version")
public class VersionController {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getVersion() {
        return "Version 1.2";
    }
}
