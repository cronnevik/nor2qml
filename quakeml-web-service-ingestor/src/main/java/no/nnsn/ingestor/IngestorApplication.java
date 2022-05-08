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

import java.util.concurrent.locks.ReentrantLock;


@SpringBootApplication(scanBasePackages = {
        "no.nnsn.ingestor",
        "no.nnsn.convertercore"
})
public class IngestorApplication {

    final Ingestor ingestor;
    final Arguments arguments;
    final ApplicationContext context;
    private final ReentrantLock lock;

    @Autowired
    public IngestorApplication(Ingestor ingestor, Arguments arguments, ApplicationContext context) {
        this.ingestor = ingestor;
        this.arguments = arguments;
        this.context = context;
        lock = new ReentrantLock();
    }

    public static void main(String[] args) {
        SpringApplication.run(IngestorApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void ContextRefreshedEventExecute(){

    }

    @EventListener(ApplicationReadyEvent.class)
    public void EventListenerExecute() throws Exception{
        // if scheduler is not enabled then execute manually, otherwise the scheduler is doing running the execute method
        if (!arguments.isScheduled()) {
            execute();
        }
    }

    @EventListener(ApplicationFailedEvent.class)
    public void EventListenerExecuteFailed(){
        System.out.println("Application Event Listener is Failed");
    }

    @Scheduled(fixedDelayString = "${scheduler.interval}")
    public void execute() throws Exception {
        String pathFolder = arguments.hasInputFolder() ? arguments.getInputFolder() : arguments.getCurrentPath() + "/catalogs";
        CatalogConfig[] catalogs = arguments.getCatalogsConfig();

        for (CatalogConfig catConf: catalogs) {
            lock.lock();
            try {
                String catalogName = catConf.getName();

                String pathInput = pathFolder + "/" + catalogName;
                String sourceType = arguments.getSourceType();

                IngestorOptions options = new IngestorOptions();
                options.setInput(pathInput);
                options.setCatalogName(catalogName);
                options.setProfile(arguments.getProfile());
                options.setForceIngestion(arguments.forceIngestion());
                options.setSourceType(sourceType);
                options.setQmlPrefix(catConf.getPrefix());
                options.setQmlAgency(catConf.getAuthorityID());
                options.setIgnoreFolders(arguments.getIgnoreFolders());

                FileInfo fileInfo = ingestor.getNumOfFiles(pathInput, sourceType, options.getIgnoreFolders());

                ingestor.execute(fileInfo, options);
            } finally {
                lock.unlock();
            }

        }
        if (!arguments.isScheduled()) {
            System.exit(SpringApplication.exit(context));
        }
    }

}
