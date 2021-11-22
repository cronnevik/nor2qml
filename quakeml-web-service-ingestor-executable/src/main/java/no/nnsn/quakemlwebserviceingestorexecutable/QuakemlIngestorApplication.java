package no.nnsn.quakemlwebserviceingestorexecutable;

import no.nnsn.ingestorcore.components.Ingestor;
import no.nnsn.ingestorcore.dao.IngestorOptions;
import no.nnsn.quakemlwebserviceingestorexecutable.components.Arguments;
import no.nnsn.seisanquakemlcommonsfile.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication(scanBasePackages = {
        "no.nnsn.quakemlwebserviceingestorexecutable",
        "no.nnsn.ingestorcore"
})
public class QuakemlIngestorApplication {

    final Arguments arguments;
    final Ingestor ingestor;
    final ApplicationContext context;

    @Autowired
    public QuakemlIngestorApplication(Arguments arguments, Ingestor ingestor, ApplicationContext context) {
        this.arguments = arguments;
        this.ingestor = ingestor;
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
        String pathInput = arguments.hasInput() ? arguments.getInput() : arguments.getCurrentPath();
        String sourceType = arguments.getSourceType();

        System.out.println("Scanning catalog...");
        FileInfo fileInfo = ingestor.getNumOfFiles(pathInput, sourceType);
        ingestor.printFilecount(fileInfo);

        IngestorOptions options = new IngestorOptions();
        options.setInput(pathInput);
        options.setCatalogName((arguments.catalogFromPath()) ? fileInfo.getCatalogName() : arguments.getCatalog());
        options.setProfile(arguments.getProfile());
        options.setForceIngestion(arguments.forceIngestion());
        options.setSourceType(sourceType);
        options.setQmlPrefix(arguments.getQmlPrefix());
        options.setQmlAgency(arguments.getQmlAgency());

        String catalog = fileInfo.getCatalogName();
        System.out.println("Catalog: " + catalog);

        ingestor.ingest(fileInfo, options);


        System.exit(SpringApplication.exit(context));
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
        System.out.println("Application Event Listener is Failed");
    }
}
