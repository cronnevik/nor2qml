package no.nnsn.converterwebserver.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class TestController {
    @RequestMapping(value = "/test-data" ,method = RequestMethod.GET)
    public String getTestData() {
        return "testing";
    }
}
