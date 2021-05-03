package no.nnsn.quakemlwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"no.nnsn.quakemlwebservice", "no.nnsn.convertercore"})
public class QuakemlWebServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuakemlWebServiceApplication.class, args);
    }

}
