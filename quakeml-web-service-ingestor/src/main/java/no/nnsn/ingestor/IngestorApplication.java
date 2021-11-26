package no.nnsn.ingestor;

import no.nnsn.ingestor.components.Arguments;
import no.nnsn.ingestor.components.Ingestor;
import no.nnsn.ingestor.dao.IngestorOptions;
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
        "no.nnsn.ingestor",
        "no.nnsn.convertercore"
})
public class IngestorApplication {

    final Ingestor ingestor;
    final Arguments arguments;
    final ApplicationContext context;

    @Autowired
    public IngestorApplication(Ingestor ingestor, Arguments arguments, ApplicationContext context) {
        this.ingestor = ingestor;
        this.arguments = arguments;
        this.context = context;
    }

    public static void main(String[] args) {
        SpringApplication.run(IngestorApplication.class, args);
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
