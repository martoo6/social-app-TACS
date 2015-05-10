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
public class UsersControllerTest extends GenericTest {
    UsersServiceInterface us = mock(UsersServiceInterface.class);

    @Test
    public void createUserResponse() {
        when(us.createUser(any(User.class))).thenReturn(immediateFuture(new User()));

        String json = "{\"username\":\"test01\",\n" +
                " \"password\":\"qwerty\",\n" +
                " \"email\":\"test01@gmail.com\"\n" +
                "}";

        final Response response = target("users").request(MediaType.APPLICATION_JSON).post(Entity.entity(json, MediaType.valueOf("application/json")));
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
