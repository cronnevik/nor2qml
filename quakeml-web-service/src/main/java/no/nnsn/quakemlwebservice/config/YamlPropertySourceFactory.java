package no.nnsn.quakemlwebservice.config;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;


public class YamlPropertySourceFactory extends DefaultPropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        if (resource == null){
            return super.createPropertySource(name, resource);
        }
        List<PropertySource<?>> sources = new YamlPropertySourceLoader().load(resource.getResource().getFilename(), resource.getResource());
        for (PropertySource<?> checkSource : sources) {
            if (checkSource.containsProperty("spring.profiles.active")) {
                String activeProfile = (String) checkSource.getProperty("spring.profiles.active");
                for (PropertySource<?> source : sources) {
                    if (activeProfile.trim().equals(source.getProperty("spring.config.activate.on-profile"))) {
                        return source;
                    }
                }
            }
        }
        return sources.get(0);
    }

}
