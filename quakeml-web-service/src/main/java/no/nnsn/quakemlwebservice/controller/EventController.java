package no.nnsn.quakemlwebservice.controller;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.EventOverview;
import no.nnsn.convertercore.interfaces.NordicToQml;
import no.nnsn.convertercore.interfaces.QmlToSfile;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.quakemlwebservice.dao.FormatType;
import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.quakemlwebservice.helper.TextOutput;
import no.nnsn.quakemlwebservice.service.CatalogService;
import no.nnsn.quakemlwebservice.service.SfileEventService;
import no.nnsn.quakemlwebservice.service.SfileService;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.utils.QuakemlUtils;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileCheck;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Component
@Path("/query")
public class EventController {

    final QmlToSfile qmlToSfile;
    final NordicToQml nordicToQml;
    final SfileEventService sfileEventService;
    final SfileService sfileService;
    final CatalogService catalogService;

    @Autowired
    public EventController(SfileEventService sfileEventService, QmlToSfile qmlToSfile, NordicToQml nordicToQml, SfileService sfileService, CatalogService catalogService) {
        this.sfileEventService = sfileEventService;
        this.qmlToSfile = qmlToSfile;
        this.nordicToQml = nordicToQml;
        this.sfileService = sfileService;
        this.catalogService = catalogService;
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    public Response getEvents(
            @QueryParam("catalog") String catalog,
            @QueryParam("eventtype") String eventTypes,
            @QueryParam("starttime") String starttime,
            @QueryParam("endtime") String endtime,
            @DefaultValue("-90") @QueryParam("minlatitude") Double minlatitude,
            @DefaultValue("90") @QueryParam("maxlatitude") Double maxlatitude,
            @DefaultValue("-180") @QueryParam("minlongitude") Double minlongitude,
            @DefaultValue("180") @QueryParam("maxlongitude") Double maxlongitude,
            @DefaultValue("0") @QueryParam("mindepth") Double mindepth,
            @DefaultValue("700") @QueryParam("maxdepth") Double maxdepth,
            @DefaultValue("-2") @QueryParam("minmagnitude") Double minmagnitude,
            @DefaultValue("10") @QueryParam("maxmagnitude") Double maxmagnitude,
            @QueryParam("eventid") String eventid,
            @DefaultValue("time") @QueryParam("orderby") OrderByType orderby,
            @DefaultValue("1000") @QueryParam("limit") int limit,
            @DefaultValue("xml") @QueryParam("format") FormatType format,
            @DefaultValue("204") @QueryParam("nodata") Integer nodataCode,
            @Context UriInfo uriInfo
            ) {

        String url = uriInfo.getRequestUri().toString();

        Catalog cat;
        List<Catalog> catalogs;
        if (catalog == null) {
            catalogs = this.catalogService.getCatalogs();
            cat = catalogs.get(0);
        } else {
            cat = this.catalogService.getCatalogNyName(catalog);
        }
        List<EventType> types = new ArrayList<>();

        if (eventTypes != null && !eventTypes.isEmpty()) {
            types = new ArrayList<>();

            if (eventTypes.contains(",")) {
                String[] eventTypeSplit = eventTypes.split(",");
                for (String ev: eventTypeSplit) {
                    EventType type;
                    try {
                        type = EventType.valueOf(ev);
                    } catch (Exception e) {
                        type = null;
                    }
                    if (type != null) {
                        types.add(type);

                    }
                }
            } else {
                types.add(EventType.valueOf(eventTypes));
            }
        }

        if (types.size() == 0){
            types = sfileEventService.getUsedEventTypes();
        }

        if (cat != null) {
            IdGenerator idGenerator = IdGenerator.getInstance();
            idGenerator.setPrefix(cat.getPrefix());
            idGenerator.setAuthorityID(cat.getAuthorityID());
        }

        List<String> sfileIDs;

        if (eventid != null) {
            SfileCheck sfileCheck = sfileEventService.getSfileFromEventId(eventid);

            if (sfileCheck != null) {

                byte[] sfile = sfileCheck.getFile();
                InputStream input = new ByteArrayInputStream(sfile);

                List<Sfile> sfiles = nordicToQml.readSfile(input, sfileCheck.getSfileID(), CallerType.WEBSERVICE);
                EventOverview events = nordicToQml.getEvents(sfiles, "error", CallerType.WEBSERVICE, null, eventid);

                return Response.ok(QuakemlUtils.getQuakeml12DocFromEvents(events.getEvents(), cat)) // Return 200 OK
                        .header("Content-Type", "application/xml")
                        .build();
            } else {
                return Response.status(nodataCode).build();
            }

        } else {
            if (starttime == null && endtime == null) {
                return Response.status(400).build();
            } else {
                String catalogName = cat.getCatalogName();
                sfileIDs = sfileEventService.getSfileIds(
                        starttime, endtime,
                        minlatitude, maxlatitude, minlongitude, maxlongitude,
                        types,
                        mindepth, maxdepth,
                        minmagnitude, maxmagnitude,
                        catalogName,
                        limit, orderby);
            }

        }

        if (sfileIDs != null && sfileIDs.size() > 0) {

            // Return specified format
            if (format.equals(FormatType.XML)) {
                List<SfileCheck> sfileChecks = sfileService.getSfiles(sfileIDs);
                List<Event> events = new ArrayList<>();
                for (SfileCheck sf: sfileChecks) {
                    byte[] sfile = sf.getFile();
                    InputStream input = new ByteArrayInputStream(sfile);
                    List<Sfile> sfiles = nordicToQml.readSfile(input, sf.getSfileID(), CallerType.WEBSERVICE);
                    EventOverview eventOverview = nordicToQml.getEvents(sfiles, "error", CallerType.WEBSERVICE, null, eventid);
                    events.addAll(eventOverview.getEvents());
                }
                return Response.ok(QuakemlUtils.getQuakeml12DocFromEvents(events, cat))
                        .header("Content-Type", "application/xml")
                        .build();
            } else if (format.equals(FormatType.QUAKEML20)) {
                List<SfileCheck> sfileChecks = sfileService.getSfiles(sfileIDs);
                List<Event> events = new ArrayList<>();
                for (SfileCheck sf: sfileChecks) {
                    byte[] sfile = sf.getFile();
                    InputStream input = new ByteArrayInputStream(sfile);
                    List<Sfile> sfiles = nordicToQml.readSfile(input, sf.getSfileID(), CallerType.WEBSERVICE);
                    EventOverview eventOverview = nordicToQml.getEvents(sfiles, "error", CallerType.WEBSERVICE, null, eventid);
                    events.addAll(eventOverview.getEvents());
                }
                return Response.ok(QuakemlUtils.getQuakeml20DocFromEvents(events, cat))
                        .header("Content-Type", "application/xml")
                        .build();
            } else if (format.equals(FormatType.NORDIC)) {
                List<SfileCheck> sfileChecks = sfileService.getSfiles(sfileIDs);
                String nordicText = "";
                for (SfileCheck sf: sfileChecks) {
                    byte[] sfile = sf.getFile();
                    nordicText += new String((sfile));
                }

                return Response.ok(nordicText)
                        .header("Content-Type", "text/plain")
                        .build();
            } else if (format.equals(FormatType.TEXT)) {
                String catalogName = cat.getCatalogName();
                List<SfileEvent> sfileEvents = sfileEventService.getSfileEvents(starttime, endtime,
                        minlatitude, maxlatitude, minlongitude, maxlongitude,
                        types,
                        mindepth, maxdepth,
                        minmagnitude, maxmagnitude,
                        catalogName,
                        limit, orderby);
                return Response.ok(TextOutput.getTextFormat(sfileEvents))
                        .header("Content-Type", "text/plain")
                        .build();
            } else {
                return Response.status(nodataCode).build();
            }

        } else {
            return Response.status(nodataCode).build();
        }

    }

}
