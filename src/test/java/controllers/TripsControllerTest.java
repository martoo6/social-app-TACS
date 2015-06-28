package controllers;

import com.hax.models.Trip;
import com.hax.services.TripsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 20/04/15.
 */


public class TripsControllerTest extends GenericTest {
    //Al estar compartido por los test tiene efecto de lado pero el biding se da una sola vez asique no queda otra.
    //A menos que se encuentre forma de re-iniciar el mock dentro de cada instancia.
    TripsServiceInterface fs = mock(TripsServiceInterface.class);
    UsersServiceInterface us = mock(UsersServiceInterface.class);

    @Override
    protected AbstractBinder setBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(fs).to(TripsServiceInterface.class);
                bind(us).to(UsersServiceInterface.class);
            }
        };
    }

    @Test
    public void createTripResponse() {
        String json = "{\n" +
                "     \"wayFlights\":\n" +
                "     [{\n" +
                "     \"origin\":\"EZE, Buenos Aires, Argentina\",\n" +
                "     \"destiny\":\"MNT, Montevideo, Uruguay\",\n" +
                "     \"airline\":\"American Airlines\",\n" +
                "     \"flightNum\":\"B34A5\",\n" +
                "     \"departureTime\":\"20-04-2015 18:30\",\n" +
                "     \"duration\":\"1h 20m\"\n" +
                "     }],\n" +
                "     \"returnFlights\":\n" +
                "     [{\n" +
                "     \"origin\":\"MNT, Montevideo, Uruguay\",\n" +
                "     \"destiny\":\"EZE, Buenos Aires, Argentina\",\n" +
                "     \"airline\":\"American Airlines\",\n" +
                "     \"flightNum\":\"A98P5\",\n" +
                "     \"departureTime\":\"09-06-2015 06:10\",\n" +
                "     \"duration\":\"50m\"\n" +
                "     }],\n" +
                "     \"wayDuration\":\"50m\",\n" +
                "     \"returnDuration\":\"50m\",\n" +
                "     \"price\":\"532\",\n" +
                "     \"origin\":\"MNT, Montevideo, Uruguay\",\n" +
                "     \"destiny\":\"EZE, Buenos Aires, Argentina\"\n" +
                "     }";

        when(fs.createTrip(any(Trip.class), anyString())).thenReturn(new Trip());


        final Response response = target("trips").request(MediaType.APPLICATION_JSON).header("token",0).post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void getTripResponse() {
        when(fs.getTrip(100L)).thenReturn(new Trip());

        final Response response = target("trips/100").request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }
}
