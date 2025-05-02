package no.nnsn.convertercore;

import no.nnsn.seisanquakeml.models.ModelsAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ModelsAutoConfigure.class)
public class ConverterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConverterApplication.class, args);
    }
}
