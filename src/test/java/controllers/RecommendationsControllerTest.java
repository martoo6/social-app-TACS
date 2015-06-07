package controllers;

import com.google.common.util.concurrent.Futures;
import com.hax.models.Trip;
import com.hax.models.Recommendation;
import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 20/04/15.
 */
public class RecommendationsControllerTest extends GenericTest {
    UsersServiceInterface us = mock(UsersServiceInterface.class);

    @Test
    public void getFlightsRecommendationsResponse() {
        Trip trip = new Trip(null,null,null, "Argentina", "USA", "2m", "2m");
        User user = new User();
        Recommendation recommendation = new Recommendation(trip, user);

        when(us.getRecommendations(anyInt())).thenReturn(Futures.immediateFuture(Arrays.asList(recommendation)));

        final Response responseWrapper = target("recommendations").request(MediaType.APPLICATION_JSON).header("token","0").get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void recommendFlightResponse() {
        Trip trip = new Trip(null,null,null, "Argentina", "USA", "2m", "2m");
        User user = new User();
        Recommendation recommendation = new Recommendation(trip, user);

        when(us.recommendFlight(anyInt(), anyInt(), anyInt())).thenReturn(immediateFuture(recommendation));

        String json = "{\n" +
                " \"flightId\":9,\n" +
                " \"toUserId\":0\n" +
                "}";

        final Response response = target("recommendations").request(MediaType.APPLICATION_JSON).header("token", "0").post(Entity.json(json));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void acceptRecommendationsResponse() {
        Trip trip = new Trip(null,null,null, "Argentina", "USA", "2m", "2m");
        User user = new User();
        Recommendation recommendation = new Recommendation(trip, user);

        when(us.acceptRecommendation(anyInt(), anyInt())).thenReturn(immediateFuture(recommendation));

        final Response responseWrapper = target("recommendations/1").queryParam("token", 0).request(MediaType.APPLICATION_JSON).header("token", "0").put(Entity.json(""));
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void rejectRecommendationsResponse() {
        Trip trip = new Trip(null,null,null, "Argentina", "USA", "2m", "2m");
        User user = new User();
        Recommendation recommendation = new Recommendation(trip, user);

        when(us.rejectRecommendation(anyInt(), anyInt())).thenReturn(immediateFuture(recommendation));

        final Response responseWrapper = target("recommendations/1").queryParam("token", 0).request(MediaType.APPLICATION_JSON).header("token", "0").delete();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Override
    protected AbstractBinder setBinder() {
        return new AbstractBinder() {
            @Override
            protected void configure() {
                bind(us).to(UsersServiceInterface.class);
            }
        };
    }
}
