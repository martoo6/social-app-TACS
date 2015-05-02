import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.AppConfig;
import com.hax.connectors.DespegarConnector;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.controllers.FlightsController;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.hax.async.executors.Default.ex;
/**
 * Created by martin on 20/04/15.
 */


public class FlightsControllerTest extends GenericTest{

    @Override
    AbstractBinder setBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                FlightsServiceInterface c = mock(FlightsServiceInterface.class);
//                ListenableFuture<String> f =  ex.submit(new Callable<String>() {
//                    public String call() throws Exception {
//                        return "1";
//                    }
//                });
                when(c.getFlights(anyString(), anyString(), anyString(), anyString())).thenReturn(ex.submit(new Callable<String>() {
                    public String call() throws Exception {
                        return "1";
                    }
                }));
                //bind(c).to(DespegarConnectorInterface.class);
                bind(c).to(FlightsServiceInterface.class);
            }
        };
    }

    @Test
    public void getFilteredFlightsResponse() {
        final Response response = target("flights")
                .queryParam("origin", "EZE")
                .queryParam("destiny","MIA")
                .queryParam("departure","2015-10-10")
                .queryParam("arrival","2015-11-10")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void createFlightResponse() {
        final Response response = target("flights").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void recommendFlightResponse() {
        final Response response = target("flights/1/recommendations/1").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void publishFlightResponse() {
        final Response response = target("flights/1/publish/1").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void despegarConnector() {
        ListenableFuture<String> f = new DespegarConnector().getFlightsAsync("EZE", "MIA", "2015-07-21", "2015-08-28");
        String res = null;
        try {
            res = f.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        assertTrue(res.length() > 0);
    }
}
