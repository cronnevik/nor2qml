package no.nnsn.converterwebserver.controllers;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class RedirectController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView redirectWithUsingForwardPrefix(ModelMap model) {
        return new ModelAndView("forward:/index.html", model);
    }
}
