package no.nnsn.quakemlwebservice.config;

import com.sun.research.ws.wadl.*;
import org.glassfish.jersey.server.model.Parameter;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.wadl.WadlGenerator;
import org.glassfish.jersey.server.wadl.internal.ApplicationDescription;

import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.List;

public class OptionsWadlGenerator implements WadlGenerator {

    private WadlGenerator _delegate;

    private ObjectFactory objectFactory = new ObjectFactory();

    @Override
    public Param createParam(Resource resource, ResourceMethod resourceMethod, Parameter parameter) {
        Param param = _delegate.createParam(resource, resourceMethod, parameter);
        final Class<?> c = parameter.getRawType();
        if(c.isEnum()){

            final Object[] enumConstants = c.getEnumConstants();
            Arrays.stream(enumConstants).forEach(value -> {
                Option option = objectFactory.createOption();
                option.setValue(value.toString());
                param.getOption().add(option);
            });

        }

        return param;
    }

    @Override
    public void setWadlGeneratorDelegate(WadlGenerator delegate) {
        this._delegate = delegate;
    }

    @Override
    public void init() throws Exception {
        _delegate.init();
    }

    @Override
    public String getRequiredJaxbContextPath() {
        return _delegate.getRequiredJaxbContextPath();
    }

    @Override
    public Application createApplication() {
        return _delegate.createApplication();
    }

    @Override
    public Resources createResources() {
        return _delegate.createResources();
    }

    @Override
    public com.sun.research.ws.wadl.Resource createResource(Resource resource, String s) {
        return _delegate.createResource(resource, s);
    }

    @Override
    public Method createMethod(Resource resource, ResourceMethod resourceMethod) {
        return _delegate.createMethod(resource, resourceMethod);
    }

    @Override
    public Request createRequest(Resource resource, ResourceMethod resourceMethod) {
        return _delegate.createRequest(resource, resourceMethod);
    }

    @Override
    public Representation createRequestRepresentation(Resource resource, ResourceMethod resourceMethod, MediaType mediaType) {
        return _delegate.createRequestRepresentation(resource, resourceMethod, mediaType);
    }

    @Override
    public List<Response> createResponses(Resource resource, ResourceMethod resourceMethod) {
        return _delegate.createResponses(resource, resourceMethod);
    }

    @Override
    public ExternalGrammarDefinition createExternalGrammar() {
        return _delegate.createExternalGrammar();
    }

    @Override
    public void attachTypes(ApplicationDescription applicationDescription) {

    }

}
