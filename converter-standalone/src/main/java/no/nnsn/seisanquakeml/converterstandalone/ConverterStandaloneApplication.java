package no.nnsn.seisanquakeml.converterstandalone;

import no.nnsn.convertercore.ConverterCoreConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.time.Duration;
import java.time.Instant;

@SpringBootApplication
@Import(ConverterCoreConfiguration.class)
public class ConverterStandaloneApplication implements CommandLineRunner {

    private final ConfigurableApplicationContext context;
    final Converter converter;

    @Autowired
    public ConverterStandaloneApplication(ConfigurableApplicationContext context, Converter converter) {
        this.context = context;
        this.converter = converter;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConverterStandaloneApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Application init finished");
        System.out.println("Conversion started...");
        Instant start = Instant.now();
        converter.convert();
        Instant finish = Instant.now();

        long timeElapsedMin = Duration.between(start, finish).toMinutes();
        long timeElapsedSec = Duration.between(start, finish).getSeconds();
        long timeElapsedMilliSec = Duration.between(start, finish).toMillis();
        long timeElapsedNanoSec = Duration.between(start, finish).toNanos();

        if (timeElapsedMin != 0) {
            System.out.println("Conversion finished after " + timeElapsedMin + " minutes");
        } else if (timeElapsedSec != 0) {
            System.out.println("Conversion finished after " + timeElapsedSec + " seconds");
        } else if (timeElapsedMilliSec != 0){
            System.out.println("Conversion finished after " + timeElapsedMilliSec + " milli seconds");
        } else {
            System.out.println("Conversion finished after " + timeElapsedNanoSec + " nano seconds");
        }

        System.exit(SpringApplication.exit(context));
    }
}
