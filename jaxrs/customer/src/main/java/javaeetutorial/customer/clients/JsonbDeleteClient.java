package javaeetutorial.customer.clients;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import javaeetutorial.customer.data.Customer;
import org.apache.http.HttpStatus;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonbDeleteClient {
    private static final Logger logger = Logger.getLogger(JsonbDeleteClient.class.getName());

    public static void main(String[] args)  {

        Client client = Client.create();
        Jsonb jsonb = JsonbBuilder.create();

        WebResource service = client.resource(UriBuilder.fromUri(Configs.TARGET).build());

        /*
         * Retrieve all records
         */
        logger.log(Level.INFO, "About to retrieve all customer records at once");
        Date start = new Date();
        String jsonData = service.path(Configs.REST_PATH).path(Configs.GET_CUSTOMERS_PATH).accept(MediaType.APPLICATION_JSON).get(String.class);
        logger.log(Level.INFO, " Done in {0} ms", new Date().getTime() - start.getTime());
        List<Customer> customers = jsonb.fromJson(jsonData, new ArrayList<Customer>(){}.getClass().getGenericSuperclass());


        logger.log(Level.INFO, "About to update {0} customer records", customers.size());
        start = new Date();
        for(Customer c: customers) {
            c.setFirstname("They called them");
            c.setLastname("Nobody");
            try {
                service.path(Configs.REST_PATH).path(Configs.CUSTOMER_PATH).path(c.getId() + "").entity(jsonb.toJson(c), MediaType.APPLICATION_JSON).put();
            } catch(UniformInterfaceException e) {
                logger.log(Level.INFO, " There was an unexpected response {0}, {1}", new Object[]{e.getResponse().getStatus(), e.getMessage()});
            }
        }
        logger.log(Level.INFO, " Done in {0} ms", new Date().getTime() - start.getTime());

        logger.log(Level.INFO, "About to delete {0} customer records", customers.size());
        start = new Date();
        for(Customer c: customers) {
            service.path(Configs.REST_PATH).path(Configs.CUSTOMER_PATH).path(c.getId() + "").delete();
        }
        logger.log(Level.INFO, " Done in {0} ms", new Date().getTime() - start.getTime());
    }
}