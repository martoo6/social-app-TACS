import com.hax.controllers.FlightsController;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by martin on 20/04/15.
 */
public class BasicEndpointTest extends JerseyTest{

    protected Application configure() {
        return new ResourceConfig(FlightsController.class);
    }

    @Test
    public void testAllRoutesResponse() {
        List<String> urls = Arrays.asList("flights", "flights/1", "friends/1", "friends/recommended/1");
        for(String url:urls) assertOkStatus(url);
    }

    public void assertOkStatus(String url) {
        final Response responseWrapper = target(url).request(MediaType.APPLICATION_JSON).get();
        assertEquals("Url not 200: "+url,Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }
}
