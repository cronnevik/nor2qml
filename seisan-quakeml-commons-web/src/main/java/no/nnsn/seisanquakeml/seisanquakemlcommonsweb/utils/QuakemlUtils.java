package no.nnsn.seisanquakeml.seisanquakemlcommonsweb.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import no.nnsn.convertercore.mappers.from_qml.to_qml.QmlToEventMapper;
import no.nnsn.convertercore.mappers.from_qml.to_qml.QmlToEventParamsMapper;
import no.nnsn.convertercore.mappers.utils.IdGenerator;
import no.nnsn.seisanquakeml.seisanquakemlcommonsweb.helpers.QuakemlContent;
import no.nnsn.seisanquakeml.models.catalog.Catalog;
import no.nnsn.seisanquakeml.models.quakeml.v12.QuakeMLDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.EventDto;
import no.nnsn.seisanquakeml.models.quakeml.v12.event.elements.EventParametersDto;
import no.nnsn.seisanquakeml.models.quakeml.v20.QuakeML;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.Event;
import no.nnsn.seisanquakeml.models.quakeml.v20.basicevent.EventParameters;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2CollectionHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuakemlUtils {
    public static InputStream getQuakemlStreamFromWebService(String webServiceUrl) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();

        // Get the default messageConverterList
        List<HttpMessageConverter<?>> messageConverterList = restTemplate.getMessageConverters();

        // Add MappingJackson2HttpMessageConverter and MarshallingHttpMessageConverter to messageConverterList
        Jaxb2CollectionHttpMessageConverter xmlMessageConverter = new Jaxb2CollectionHttpMessageConverter();
        MarshallingHttpMessageConverter marshallingHttpMessageConverter = new MarshallingHttpMessageConverter();
        messageConverterList.add(xmlMessageConverter);
        messageConverterList.add(marshallingHttpMessageConverter);
        restTemplate.setMessageConverters(messageConverterList);

        // Prepare HTTP Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));

        // Invoke the REST service
        RequestEntity<String> wsRequest = new RequestEntity<>(
                headers,
                HttpMethod.GET,
                new URI(webServiceUrl)
        );

        ResponseEntity<String> wsResponse = restTemplate.exchange(wsRequest, String.class);
        return new ByteArrayInputStream(wsResponse.getBody().getBytes(StandardCharsets.UTF_8));
    }

    public static InputStream getQuakemlStreamFromRawText(String rawText) {
        return new ByteArrayInputStream(rawText.getBytes(StandardCharsets.UTF_8));
    }

    public static QuakemlContent getQuakemlContent(InputStream inputStream) {
        // Check QuakeML version
        String xmlns = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element root = null;
        String xml = "";
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            root = document.getDocumentElement();
            xmlns = root.getAttribute("xmlns");
            xml = nodeToString(root.getElementsByTagName("eventParameters"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new QuakemlContent(xmlns,xml);
    }

    public static List<Event> getEventsFromQuakemlString(QuakemlContent content) {
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);
        XmlMapper mapper = new XmlMapper(module);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
        mapper.registerModule(new JaxbAnnotationModule());
        mapper.registerModule(new Jdk8Module());

        QuakeML quakeML = new QuakeML();
        if (content.getXmlns().equals("http://quakeml.org/xmlns/bed/1.2") || content.getXmlns().equals("http://quakeml.org/xmlns/quakeml/1.2")) {
            try {
                EventParametersDto eventParametersDto = mapper.readValue(content.getEvParamString(), EventParametersDto.class);
                EventParameters evParamsQml20 = QmlToEventParamsMapper.INSTANCE.mapV20EventParams(eventParametersDto);
                quakeML.setEventParameters(evParamsQml20);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            try {
                EventParameters eventParameters = mapper.readValue(content.getEvParamString(), EventParameters.class);
                quakeML.setEventParameters(eventParameters);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return quakeML.getEventParameters().getEvent();
    }

    public static String generateQuakemlStringFromSfiles(List<Event> events, String prefix, String agencyID, String qmlVersion) throws Exception {
        IdGenerator idGenerator = IdGenerator.getInstance();
        idGenerator.setPrefix(prefix);
        idGenerator.setAuthorityID(agencyID);

        QuakeML qml = new QuakeML();
        EventParameters evParams = new EventParameters();
        evParams.setPublicID(idGenerator.genEventParamsPublicID());
        evParams.setEvent(events);
        qml.setEventParameters(evParams);

        if (qmlVersion.equals("v12")) {
            QuakeMLDto quakeMLDto = new QuakeMLDto();
            EventParametersDto eventParametersDto = QmlToEventParamsMapper.INSTANCE.mapV12EventParams(evParams);
            quakeMLDto.setEventParameters(eventParametersDto);
            return generateQuakeml12Doc(quakeMLDto);
        } else {
            return generateQuakeml20Doc(qml);
        }
    }

    public static String generateQuakeml12Doc(QuakeMLDto quakeMLDto) throws Exception {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jc = JAXBContext.newInstance(QuakeMLDto.class);
            Marshaller marshaller = jc.createMarshaller();
            //marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE); // for no xml tag declaration
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(
                    Marshaller.JAXB_SCHEMA_LOCATION,
                    "http://quakeml.org/schema/xsd http://quakeml.org/schema/xsd/QuakeML-1.2.xsd"
            );
            marshaller.marshal(quakeMLDto, sw);

        } catch (JAXBException e) {
            throw new Exception(e.getMessage());
        }
        return sw.toString();
    }

    public static String generateQuakeml20Doc(QuakeML quakeML) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jc = JAXBContext.newInstance(QuakeML.class);
            Marshaller marshaller = jc.createMarshaller();
            //marshaller.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE); // for no xml tag declaration
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(
                    Marshaller.JAXB_SCHEMA_LOCATION,
                    "http://quakeml.org/xmlns/quakeml" // https://www.emidius.eu/AHEAD/xmlns/quakeml/2.0/QuakeML-2.0-Macroseismic.xsd"
            );

            marshaller.marshal(quakeML, sw);

        } catch (JAXBException e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.FORBIDDEN).body("error captures");
        }
        return sw.toString();
    }

    public static String getQuakeml12DocFromEvents(List<Event> events, Catalog catalog) throws Exception {
        QuakeMLDto quakeMLDto = new QuakeMLDto();
        EventParametersDto eventParametersDto = new EventParametersDto();

        IdGenerator idGenerator = IdGenerator.getInstance();
        String eventParamsPublicID = idGenerator.genEventParamsPublicID(catalog);
        eventParametersDto.setPublicID(eventParamsPublicID);

        List<EventDto> eventList = new ArrayList<>();
        events.forEach(ev -> eventList.add(QmlToEventMapper.INSTANCE.mapV12Event(ev)));
        eventParametersDto.setEvent(eventList);

        quakeMLDto.setEventParameters(eventParametersDto);
        return generateQuakeml12Doc(quakeMLDto);
    }

    public static String getQuakeml20DocFromEvents(List<Event> events, Catalog catalog) {
        QuakeML quakeML = new QuakeML();
        EventParameters eventParameters = new EventParameters();
        IdGenerator idGenerator = IdGenerator.getInstance();
        String eventParamsPublicID = idGenerator.genEventParamsPublicID(catalog);
        eventParameters.setPublicID(eventParamsPublicID);

        eventParameters.setEvent(events);
        quakeML.setEventParameters(eventParameters);
        return generateQuakeml20Doc(quakeML);
    }

    private static String nodeToString(NodeList nodeList) throws Exception{
        StringWriter sw = new StringWriter();

        Transformer t = TransformerFactory.newInstance().newTransformer();
        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        t.setOutputProperty(OutputKeys.INDENT, "yes");
        t.transform(new DOMSource(nodeList.item(0)), new StreamResult(sw));

        return sw.toString();
    }
}
