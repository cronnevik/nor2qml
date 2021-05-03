package no.nnsn.converterwebserver;

import no.nnsn.convertercore.ConverterCoreConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConverterCoreConfiguration.class)
public class ConverterWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConverterWebServerApplication.class, args);
    }

}
