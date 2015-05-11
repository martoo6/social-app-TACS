package controllers;

import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
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
        when(us.getRecommendations(anyInt())).thenReturn(immediateFuture(new ArrayList<Recommendation>()));

        final Response responseWrapper = target("recommendations").queryParam("userId",0).request(MediaType.APPLICATION_JSON).header("userId","0").get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void recommendFlightResponse() {
        when(us.recommendFlight(anyInt(), anyInt(), anyInt())).thenReturn(immediateFuture(new Recommendation(null,null)));

        String json = "{\n" +
                " \"flightId\":9,\n" +
                " \"toUserId\":0\n" +
                "}";

        final Response response = target("recommendations").request(MediaType.APPLICATION_JSON).header("userId", "0").post(Entity.entity(json, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
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
