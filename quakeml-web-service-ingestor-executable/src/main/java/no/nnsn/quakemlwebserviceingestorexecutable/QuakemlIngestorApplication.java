package no.nnsn.quakemlwebserviceingestorexecutable;

import no.nnsn.quakemlwebserviceingestorexecutable.components.Arguments;
import no.nnsn.quakemlwebserviceingestorexecutable.components.Ingestor;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages =
        {
                "no.nnsn.quakemlwebserviceingestorexecutable",
                "no.nnsn.convertercore"
        })
public class QuakemlIngestorApplication {


    final Ingestor ingestor;
    final Arguments arguments;
    final ApplicationContext context;

    @Autowired
    public QuakemlIngestorApplication(Ingestor ingestor, Arguments arguments, ApplicationContext context) {
        this.ingestor = ingestor;
        this.arguments = arguments;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(QuakemlIngestorApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void ContextRefreshedEventExecute(){

    }

    @EventListener(ApplicationReadyEvent.class)
    public void EventListenerExecute() throws Exception{
        String pathInput = ingestor.getPath();
        System.out.println("Scanning catalog...");
        FileInfo fileInfo = ingestor.getNumOfFiles(pathInput);
        ingestor.printFilecount(fileInfo);

        String catalog = fileInfo.getCatalogName();
        System.out.println("Catalog: " + catalog);

        ingestor.ingest(fileInfo, pathInput);


        System.exit(SpringApplication.exit(context));
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
        System.out.println("Application Event Listener is Failed");
    }

}
