package no.nnsn.seisanquakeml.converterstandalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class Arguments {

    final Environment env;

    @Autowired
    public Arguments(Environment env) {
        this.env = env;
    }

    public String getCurrentPath() {
        return System.getProperty("user.dir");
    }

    public Boolean hasInput() { return !env.getProperty("converter.input").equals("false"); }

    public String getInput() { return env.getProperty("converter.input"); }

    public Boolean hasOutput() { return !env.getProperty("converter.output").equals("false"); }

    public String getOutput() { return env.getProperty("converter.output"); }

    public Boolean isToQuakeml() {
        return env.getProperty("converter.type").equals(ArgumentType.TOQUAKEML.type);
    }

    public Boolean isToSfile() {
        return env.getProperty("converter.type").equals(ArgumentType.TOSFILE.type);
    }

    public Boolean isQuakemlV12() {
        return env.getProperty("format.version").equals(ArgumentType.QMLVERSION12.type);
    }
    public Boolean isQuakemlV20() {
        return env.getProperty("format.version").equals(ArgumentType.QMLVERSION20.type);
    }

    public Boolean isNordic1() {
        return env.getProperty("format.version").equals(ArgumentType.NORDICVERSION1.type);
    }
    public Boolean isNordic2() {
        return env.getProperty("format.version").equals(ArgumentType.NORDICVERSION2.type);
    }

    public Boolean isQuakemlSourceFile() {
        return env.getProperty("quakeml.source.type").equals(ArgumentType.QMLSOURCEFILE.type);
    }

    public Boolean isQuakemlSourceWeb() {
        return env.getProperty("quakeml.source.type").equals(ArgumentType.QMLSOURCEWEB.type);
    }

    public String getQmlUrl() {
        return env.getProperty("quakeml.source.url");
    }

    public String getQmlPrefix() {
        return env.getProperty("quakeml.prefix");
    }

    public String getQmlAgency() {
        return env.getProperty("quakeml.agency");
    }

}
