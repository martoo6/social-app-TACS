package controllers;

import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.services.FlightsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.Callable;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.hax.async.executors.Default.ex;
/**
 * Created by martin on 20/04/15.
 */


public class FlightsControllerTest extends GenericTest {
    //Al estar compartido por los test tiene efecto de lado pero el biding se da una sola vez asique no queda otra.
    //A menos que se encuentre forma de re-iniciar el mock dentro de cada instancia.
    FlightsServiceInterface fs = mock(FlightsServiceInterface.class);
    UsersServiceInterface us = mock(UsersServiceInterface.class);

    @Override
    protected AbstractBinder setBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(fs).to(FlightsServiceInterface.class);
                bind(us).to(UsersServiceInterface.class);
            }
        };
    }

    @Test
    public void getFilteredFlightsResponse() {
        when(fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(immediateFuture("1"));


        final Response response = target("flights")
                .queryParam("origin", "EZE")
                .queryParam("destiny", "MIA")
                .queryParam("departure", "2015-10-10")
                .queryParam("arrival","2015-11-10")
                .request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getWrongDateFlights() {
        when(fs.getFlights("EZE", "MIA", "2015-12-10", "2015-11-10")).thenReturn(ex.submit(new Callable<String>() {
            public String call() throws Exception {
                throw new RuntimeException("Error 400 !");
            }
        }));


        final Response response = target("flights")
                .queryParam("origin", "EZE")
                .queryParam("destiny", "MIA")
                .queryParam("departure", "2015-12-10")
                .queryParam("arrival", "2015-11-10")
                .request(MediaType.APPLICATION_JSON).get();

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void createFlightResponse() {
        String json = "{\n" +
                "     \"wayTicket\":\n" +
                "     {\n" +
                "     \"origin\":\"EZE, Buenos Aires, Argentina\",\n" +
                "     \"destiny\":\"MNT, Montevideo, Uruguay\",\n" +
                "     \"company\":\"American Airlines\",\n" +
                "     \"flightNum\":\"B34A5\",\n" +
                "     \"departureTime\":\"20-04-2015 18:30\",\n" +
                "     \"duration\":\"1h 20m\",\n" +
                "     \"price\":\"532\"\n" +
                "     },\n" +
                "     \"returnTicket\":\n" +
                "     {\n" +
                "     \"origin\":\"MNT, Montevideo, Uruguay\",\n" +
                "     \"destiny\":\"EZE, Buenos Aires, Argentina\",\n" +
                "     \"company\":\"American Airlines\",\n" +
                "     \"flightNum\":\"A98P5\",\n" +
                "     \"departureTime\":\"09-06-2015 06:10\",\n" +
                "     \"duration\":\"50m\",\n" +
                "     \"price\":\"532\"\n" +
                "     },\n" +
                "     \"totalPrice\":\"532\"\n" +
                "     }";

        when(fs.createFlight(any(Flight.class))).thenReturn(immediateFuture(new Flight()));

        when(fs.createFlight(null)).thenReturn(ex.submit(new Callable<Flight>() {
            public Flight call() throws Exception {
                throw new RuntimeException(("Error !"));
            }
        }));


        final Response response = target("flights").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.valueOf("application/json")));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void publishFlightResponse() {
        final Response response = target("flights/1/publish/1").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
