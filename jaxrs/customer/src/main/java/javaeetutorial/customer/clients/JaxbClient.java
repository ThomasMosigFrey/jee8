package javaeetutorial.customer.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.apache.http.client.ClientProtocolException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.logging.Logger;

public class JaxbClient {
    private static final Logger logger = Logger.getLogger(JaxbClient.class.getName());


    public static void main(String[] args) throws ClientProtocolException, IOException, JAXBException {

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri(Configs.TARGET).build());

        // getting XML data
        String customersXml = service.path(Configs.REST_PATH).path(Configs.GET_CUSTOMERS_PATH).accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(customersXml);
    }
}