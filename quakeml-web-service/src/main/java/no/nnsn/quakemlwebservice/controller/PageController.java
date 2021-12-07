package no.nnsn.quakemlwebservice.controller;

import no.nnsn.quakemlwebservice.dao.EventFormQuery;
import no.nnsn.quakemlwebservice.dao.MapEvent;
import no.nnsn.quakemlwebservice.dao.OrderByType;
import no.nnsn.quakemlwebservice.service.CatalogService;
import no.nnsn.quakemlwebservice.service.SfileEventService;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.catalog.SfileEvent;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PageController {

    final Environment env;
    final CatalogService catalogService;
    final SfileEventService sfileEventService;

    public PageController(Environment env, CatalogService catalogService, SfileEventService sfileEventService) {
        this.env = env;
        this.catalogService = catalogService;
        this.sfileEventService = sfileEventService;
    }

    @RequestMapping("/")
    public ModelAndView index()
    {
        ModelAndView model = new ModelAndView("index");
        if (env.getProperty("page.profile").equals("nnsn")) {
            model.addObject("form", new EventFormQuery().getNnsnInstance());
        } else if (env.getProperty("page.profile").equals("intaros")) {
            model.addObject("form", new EventFormQuery().getIntarosInstance());
        } else {
            model.addObject("form", new EventFormQuery().getDefaultInstance());
        }

        model.addObject("profile", env.getProperty("page.profile"));
        List<Catalog> catalogs = catalogService.getCatalogs();
        model.addObject("catalogs", catalogs);
        List<EventType> eventTypes = sfileEventService.getUsedEventTypes();
        List<String> types = new ArrayList<>();
        eventTypes.forEach(eventType -> {
            types.add(eventType.name());
        });
        model.addObject("types", types);

        return model;
    }

    @RequestMapping(value = "/dataform", method = RequestMethod.POST, params = "action=query")
    public ModelAndView dataForm(@ModelAttribute("form") EventFormQuery formQuery) {
        return new ModelAndView("redirect:" + getQueryString(formQuery));
    }

    @RequestMapping(value = "/dataform", method = RequestMethod.POST, params = "action=map")
    public ModelAndView mapViewRedirect(@ModelAttribute("form") EventFormQuery formQuery, final RedirectAttributes redirectAttributes) {
        ModelAndView model = new ModelAndView("redirect:/events-map");
        redirectAttributes.addFlashAttribute("form", formQuery);
        return model;
    }

    @RequestMapping(value = "/events-map", method = RequestMethod.GET)
    public ModelAndView mapView(@ModelAttribute("form") EventFormQuery formQuery, HttpServletRequest request) {
        String queryString = getQueryString(formQuery);

        String referer = request.getHeader("referer");
        ModelAndView model = new ModelAndView("map");
        model.addObject("requrl", referer.substring(0, referer.length() - 1));
        model.addObject("query", queryString);

        List<SfileEvent> sfileEvents = sfileEventService.getSfileEvents(
                formQuery.getStartTime(),
                formQuery.getEndTime(),
                formQuery.getMinLatitude() != null ? formQuery.getMinLatitude() : -90,
                formQuery.getMaxLatitude() != null ? formQuery.getMaxLatitude() : 90,
                formQuery.getMinLongitude() != null ? formQuery.getMinLongitude() : -180,
                formQuery.getMaxLongitude() != null ? formQuery.getMaxLongitude() : 180,
                getEventTypes(formQuery.getEventTypes()),
                formQuery.getMinDepth() != null ? formQuery.getMinDepth() : 0,
                formQuery.getMaxDepth() != null ? formQuery.getMaxDepth() : 700,
                formQuery.getMinMagnitude() != null ? formQuery.getMinMagnitude() : -2,
                formQuery.getMaxMagnitude() != null ? formQuery.getMaxMagnitude() : 10,
                formQuery.getCatalogName(),
                formQuery.getLimit(),
                OrderByType.fromString(formQuery.getOrderBy()));

        List<MapEvent> mapEvents = new ArrayList<>();
        sfileEvents.forEach(e -> {
            MapEvent event = new MapEvent();
            event.setEventID(e.getEventID());
            event.setTime(e.getTime());
            event.setLongitude(e.getLongitude());
            event.setLatitude(e.getLatitude());
            event.setDepth(e.getDepth());
            event.setMagnitude(e.getMagnitude());
            event.setEventType(e.getType().toString());
            mapEvents.add(event);
        });

        model.addObject("events", mapEvents);

        return model;
    }

    private String getQueryString(EventFormQuery formQuery) {
        String format = formQuery.getFormat();
        String orderBy = formQuery.getOrderBy();
        Integer noData = formQuery.getNoData();
        Integer limit = formQuery.getLimit();

        String queryString = "/fdsnws/event/1/query?";
        if (formQuery.getCatalogName() != null) queryString += "catalog=" + formQuery.getCatalogName() + "&";
        if (formQuery.getStartTime() != null) queryString += "starttime=" + formQuery.getStartTime() + "&";
        if (formQuery.getEndTime() != null) queryString += "endtime=" + formQuery.getEndTime() + "&";
        if (formQuery.getMinLatitude() != null) queryString += "minlatitude=" + formQuery.getMinLatitude() + "&";
        if (formQuery.getMaxLatitude() != null) queryString += "maxlatitude=" + formQuery.getMaxLatitude() + "&";
        if (formQuery.getMinLongitude() != null) queryString += "minlongitude=" + formQuery.getMinLongitude() + "&";
        if (formQuery.getMaxLongitude() != null) queryString += "maxlongitude=" + formQuery.getMaxLongitude() + "&";
        if (formQuery.getEventTypes() != null) queryString += "eventtype=" + formQuery.getEventTypes() + "&";
        if (formQuery.getMinDepth() != null) queryString += "mindepth=" + formQuery.getMinDepth() + "&";
        if (formQuery.getMaxDepth() != null) queryString += "maxdepth=" + formQuery.getMaxDepth() + "&";
        if (formQuery.getMinMagnitude() != null)queryString += "minmagnitude=" + formQuery.getMinMagnitude() + "&";
        if (formQuery.getMaxMagnitude() != null) queryString += "maxmagnitude=" + formQuery.getMaxMagnitude() + "&";
        queryString += "format=" + format + "&";
        queryString += "orderby=" + orderBy + "&";
        queryString += "nodata=" + noData + "&";
        queryString += "limit=" + limit;
        return queryString;
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

}
