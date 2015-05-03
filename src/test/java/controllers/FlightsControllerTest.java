package controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnector;
import com.hax.services.FlightsServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

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


public class FlightsControllerTest extends GenericTest {
    //Al estar compartido por los test tiene efecto de lado pero el biding se da una sola vez asique no queda otra.
    //A menos que se encuentre forma de re-iniciar el mock dentro de cada instancia.
    FlightsServiceInterface c = mock(FlightsServiceInterface.class);

    @Override
    protected AbstractBinder setBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(c).to(FlightsServiceInterface.class);
            }
        };
    }

    @Test
    public void getFilteredFlightsResponse() {
        when(c.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(ex.submit(new Callable<String>() {
            public String call() throws Exception {
                return "1";
            }
        }));


        final Response response = target("flights")
                .queryParam("origin", "EZE")
                .queryParam("destiny", "MIA")
                .queryParam("departure", "2015-10-10")
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
}
