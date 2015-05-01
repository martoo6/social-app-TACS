import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.AppConfig;
import com.hax.connectors.DespegarConnector;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 20/04/15.
 */
public class FlightsControllerTest extends JerseyTest{

    protected Application configure() {

        //return new ResourceConfig(FlightsController.class);
        return new AppConfig();
    }

    @Test
    public void getFilteredFlightsResponse() {
        final Response responseWrapper = target("flights").request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void createFlightResponse() {
        final Response responseWrapper = target("flights").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void recommendFlightResponse() {
        final Response responseWrapper = target("flights/1/recommendations/1").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void publishFlightResponse() {
        final Response responseWrapper = target("flights/1/publish/1").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
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
