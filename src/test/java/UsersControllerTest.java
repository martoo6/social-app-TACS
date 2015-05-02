import com.hax.controllers.FlightsController;
import com.hax.controllers.UsersController;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by martin on 20/04/15.
 */
public class UsersControllerTest extends GenericTest{

    @Test
    public void getFlightsResponse() {
        final Response responseWrapper = target("users/1/flights/").request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void getFriendsResponse() {
        final Response responseWrapper = target("users/1/friends").request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void getFlightsRecommendationsResponse() {
        final Response responseWrapper = target("users/1/recommendations/flights").request(MediaType.APPLICATION_JSON).get();
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void createUserResponse() {
        final Response responseWrapper = target("users").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Override
    AbstractBinder setBinder() {
        return null;
    }
}
