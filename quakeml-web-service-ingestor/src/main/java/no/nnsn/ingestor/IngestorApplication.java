package no.nnsn.ingestor;

import no.nnsn.ingestor.components.Arguments;
import no.nnsn.ingestor.components.Ingestor;
import no.nnsn.ingestor.dao.CatalogConfig;
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
import org.springframework.scheduling.annotation.Scheduled;


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
        execute();
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
        System.out.println("Application Event Listener is Failed");
    }

    public IngestorOptions getOptions(String pathInput, String sourceType, FileInfo fileInfo) {
        IngestorOptions options = new IngestorOptions();
        options.setInput(pathInput);
        options.setCatalogName((arguments.catalogFromPath()) ? fileInfo.getCatalogName() : arguments.getCatalog());
        options.setProfile(arguments.getProfile());
        options.setForceIngestion(arguments.forceIngestion());
        options.setSourceType(sourceType);
        options.setQmlPrefix(arguments.getQmlPrefix());
        options.setQmlAgency(arguments.getQmlAgency());
        return options;
    }

    @Scheduled(fixedDelayString = "${scheduler.interval}")
    public void execute() throws Exception {
        String pathFolder = arguments.hasInputFolder() ? arguments.getInputFolder() : arguments.getCurrentPath();
        CatalogConfig[] catalogs = arguments.getCatalogsConfig();
        for (CatalogConfig catConf: catalogs) {
            String catalogName = catConf.getName();
            String authorityID = catConf.getAuthorityID();
            String prefix = catConf.getPrefix();

            System.out.println("catalogName: " + catalogName);
            System.out.println("authorityID: " + authorityID);
            System.out.println("prefix: " + prefix);

            String pathInput = pathFolder + "/" + catalogName;
            String sourceType = arguments.getSourceType();
            FileInfo fileInfo = ingestor.getNumOfFiles(pathInput, sourceType);
            IngestorOptions options = getOptions(pathInput, sourceType, fileInfo);
            ingestor.execute(fileInfo, options);
        }

        if (!arguments.isScheduled()) {
            System.exit(SpringApplication.exit(context));
        }
    }

}
