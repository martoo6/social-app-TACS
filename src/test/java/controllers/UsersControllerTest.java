package controllers;

import com.google.common.util.concurrent.Futures;
import com.hax.models.Trip;
import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 20/04/15.
 */
public class UsersControllerTest extends GenericTest {
    UsersServiceInterface us = mock(UsersServiceInterface.class);

//    @Test
//    public void createUserResponse() {
//        when(us.createUser(any(String.class))).thenReturn((new User()));
//
//        String json = "{\"username\":\"test01\",\n" +
//                " \"password\":\"qwerty\",\n" +
//                " \"email\":\"test01@gmail.com\"\n" +
//                "}";
//
//        final Response response = target("users").request(MediaType.APPLICATION_JSON).post(Entity.json(json));
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }

    @Test
    public void getFriendsResponse() {
        User user = new User();
        user.setFacebookId("1234");

        when(us.getFriends(anyString())).thenReturn(Arrays.asList(user));

        final Response responseWrapper = target("users/me/friends").request(MediaType.APPLICATION_JSON).header("token", "0").get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }


    @Test
    public void geTripsResponse() {
        Trip trip = new Trip();
        trip.setId(1234L);

        when(us.getTrips(anyString())).thenReturn(Arrays.asList(trip));

        final Response responseWrapper = target("users/me/trips").request(MediaType.APPLICATION_JSON).header("token", "0").get();
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
