package controllers;

import com.google.common.util.concurrent.Futures;
import com.hax.models.User;
import com.hax.services.UsersServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static com.google.common.util.concurrent.Futures.immediateFuture;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 20/04/15.
 */
public class FriendsControllerTest extends GenericTest {
    UsersServiceInterface us = mock(UsersServiceInterface.class);

    @Test
    public void getFriendsResponse() {
        User user = new User();
        user.setUsername("Pepito");

        when(us.getFriends(anyString())).thenReturn(Futures.immediateFuture(Arrays.asList(user)));

        final Response responseWrapper = target("friends").request(MediaType.APPLICATION_JSON).header("token", "0").get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void addFriendResponse() {
        User user = new User();
        user.setUsername("Pepito");

        when(us.addFriend(anyString(), anyString())).thenReturn(immediateFuture(user));

        final Response responseWrapper = target("friends/11").request(MediaType.APPLICATION_JSON).header("token", "0").post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void removeFriendResponse() {
        User user = new User();
        user.setUsername("Pepito");

        when(us.removeFriend(anyString(), anyString())).thenReturn(immediateFuture(user));

        final Response responseWrapper = target("friends/11").request(MediaType.APPLICATION_JSON).header("token", "0").delete();
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
