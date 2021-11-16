package no.nnsn.quakemlwebservice.controller;

import no.nnsn.quakemlwebservice.dao.EventFormQuery;
import no.nnsn.quakemlwebservice.service.CatalogService;
import no.nnsn.quakemlwebservice.service.SfileEventService;
import no.nnsn.seisanquakemljpa.models.catalog.Catalog;
import no.nnsn.seisanquakemljpa.models.quakeml.v20.helpers.bedtypes.enums.EventType;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/dataform", method = RequestMethod.POST)
    public ModelAndView dataForm(@ModelAttribute("form") EventFormQuery formQuery) {
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

        return new ModelAndView("redirect:" + queryString);
    }
}
