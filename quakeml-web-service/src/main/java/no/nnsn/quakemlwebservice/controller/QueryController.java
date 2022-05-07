package no.nnsn.quakemlwebservice.controller;

import no.nnsn.convertercore.helpers.CallerType;
import no.nnsn.convertercore.helpers.ConverterOptions;
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
import no.nnsn.seisanquakemljpa.models.catalog.SfileInformation;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import no.nnsn.seisanquakemljpa.models.sfile.Sfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
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
@Path("/")
@Endpoint(id = "event-query")
public class QueryController {

    final QmlToSfile qmlToSfile;
    final NordicToQml nordicToQml;
    final SfileEventService sfileEventService;
    final SfileService sfileService;
    final CatalogService catalogService;

    @Autowired
    public QueryController(SfileEventService sfileEventService, QmlToSfile qmlToSfile, NordicToQml nordicToQml, SfileService sfileService, CatalogService catalogService) {
        this.sfileEventService = sfileEventService;
        this.qmlToSfile = qmlToSfile;
        this.nordicToQml = nordicToQml;
        this.sfileService = sfileService;
        this.catalogService = catalogService;
    }

    @GET
    @Path("query")
    @Produces({MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN})
    @ReadOperation
    public Response getEvents(
            @QueryParam("catalog") String catalogParam,
            @QueryParam("eventtype") String eventTypesParam,
            @QueryParam("starttime") String starttime,
            @QueryParam("endtime") String endtime,
            @QueryParam("eventid") String eventid,
            @DefaultValue("-90") @QueryParam("minlatitude") Double minlatitude,
            @DefaultValue("90") @QueryParam("maxlatitude") Double maxlatitude,
            @DefaultValue("-180") @QueryParam("minlongitude") Double minlongitude,
            @DefaultValue("180") @QueryParam("maxlongitude") Double maxlongitude,
            @QueryParam("minlat") Double minlat,
            @QueryParam("maxlat") Double maxlat,
            @QueryParam("minlon") Double minlon,
            @QueryParam("maxlon") Double maxlon,
            @DefaultValue("0") @QueryParam("mindepth") Double mindepth,
            @DefaultValue("700") @QueryParam("maxdepth") Double maxdepth,
            @DefaultValue("-2") @QueryParam("minmagnitude") Double minmagnitude,
            @DefaultValue("10") @QueryParam("maxmagnitude") Double maxmagnitude,
            @QueryParam("minmag") Double minmag,
            @QueryParam("maxmag") Double maxmag,
            @DefaultValue("time") @QueryParam("orderby") OrderByType orderby,
            @DefaultValue("1000") @QueryParam("limit") int limit,
            @DefaultValue("xml") @QueryParam("format") FormatType format,
            @DefaultValue("204") @QueryParam("nodata") Integer nodataCode,
            @Context UriInfo uriInfo
            ) {


        Catalog catalog = getCatalog(catalogParam);
        List<EventType> types = getEventTypes(eventTypesParam);

        if (catalog != null) {
            IdGenerator idGenerator = IdGenerator.getInstance();
            idGenerator.setPrefix(catalog.getPrefix());
            idGenerator.setAuthorityID(catalog.getAuthorityID());
        }

        // First check if event ID is queried for providing a single event
        if (eventid != null) {
            SfileInformation sfileInformation = sfileEventService.getSfileFromEventId(eventid);

            if (sfileInformation != null) {
                EventOverview events = getEventsFromSfile(sfileInformation, eventid);
                return Response.ok(QuakemlUtils.getQuakeml12DocFromEvents(events.getEvents(), catalog)) // Return 200 OK
                        .header("Content-Type", "application/xml")
                        .build();
            } else {
                return Response.status(nodataCode).build();
            }

        }

        // Start time and end time are mandatory
        if (starttime == null && endtime == null) {
            return Response.status(400).build();
        }

        // Check if abbreviation params is used
        if (minlat != null) minlatitude = minlat;
        if (maxlat != null) maxlatitude = maxlat;
        if (minlon != null) minlongitude = minlon;
        if (maxlon != null) maxlongitude = maxlon;
        if (maxmag != null) maxmagnitude = maxmag;
        if (minmag != null) minmagnitude = minmag;

        String catalogName = catalog.getCatalogName();
        List<String> sfileIDs = sfileEventService.getSfileIds(
                starttime, endtime,
                minlatitude, maxlatitude, minlongitude, maxlongitude,
                types,
                mindepth, maxdepth,
                minmagnitude, maxmagnitude,
                catalogName,
                limit, orderby);

        if (sfileIDs != null && sfileIDs.size() > 0) {
            // Return specified format
            if (format.equals(FormatType.XML)) {
                List<Event> events = getEventsFromMultipleSfiles(sfileIDs);
                return Response.ok(QuakemlUtils.getQuakeml12DocFromEvents(events, catalog))
                        .header("Content-Type", "application/xml")
                        .build();
            } else if (format.equals(FormatType.QUAKEML20)) {
                List<Event> events = getEventsFromMultipleSfiles(sfileIDs);
                return Response.ok(QuakemlUtils.getQuakeml20DocFromEvents(events, catalog))
                        .header("Content-Type", "application/xml")
                        .build();
            } else if (format.equals(FormatType.NORDIC)) {
                List<SfileInformation> sfileInformations = sfileService.getSfiles(sfileIDs);
                String nordicText = "";
                for (SfileInformation sf: sfileInformations) {
                    byte[] sfile = sf.getFile();
                    nordicText += new String((sfile));
                }
                return Response.ok(nordicText)
                        .header("Content-Type", "text/plain")
                        .build();
            } else if (format.equals(FormatType.TEXT)) {
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
            }
            return Response.status(nodataCode).build();

        }
        return Response.status(nodataCode).build();
    }

    private Catalog getCatalog(String catalog) {
        Catalog cat;
        List<Catalog> catalogs;
        if (catalog == null) {
            catalogs = this.catalogService.getCatalogs();
            cat = catalogs.get(0);
        } else {
            cat = this.catalogService.getCatalogNyName(catalog);
        }

        return cat;
    }

    private List<EventType> getEventTypes(String eventTypes) {
        List<EventType> types = new ArrayList<>();

        if (eventTypes != null && !eventTypes.isEmpty()) {
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
        return types;
    }

    private EventOverview getEventsFromSfile(SfileInformation sfileInformation, String eventid) {
        byte[] sfile = sfileInformation.getFile();
        InputStream input = new ByteArrayInputStream(sfile);

        List<Sfile> sfiles = nordicToQml.readSfile(input, sfileInformation.getSfileID(), CallerType.WEBSERVICE);
        ConverterOptions options = new ConverterOptions("error", CallerType.WEBSERVICE, null, eventid);
        return nordicToQml.convertToQuakeml(sfiles, options);
    }

    private List<Event> getEventsFromMultipleSfiles(List<String> sfileIDs) {
        List<SfileInformation> sfileInformations = sfileService.getSfiles(sfileIDs);
        List<Event> events = new ArrayList<>();
        for (SfileInformation sf: sfileInformations) {
            byte[] sfile = sf.getFile();
            InputStream input = new ByteArrayInputStream(sfile);
            List<Sfile> sfiles = nordicToQml.readSfile(input, sf.getSfileID(), CallerType.WEBSERVICE);
            ConverterOptions options = new ConverterOptions("error", CallerType.WEBSERVICE, null, sf.getSfileID());
            EventOverview eventOverview = nordicToQml.convertToQuakeml(sfiles, options);
            events.addAll(eventOverview.getEvents());
        }
        return events;
    }

}
