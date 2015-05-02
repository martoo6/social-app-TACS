import com.hax.controllers.RecommendationsController;
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
public class RecommendationsControllerTest extends GenericTest{

//      TODO: ver que pasarle al put
//    @Test
//    public void putAffectRecommendationResponse() {
//        final Response responseWrapper = target("recommendations/1").request(MediaType.APPLICATION_JSON).put();
//        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
//    }

    @Test
    public void postRecommendationAcceptedNotificationResponse() {
        final Response responseWrapper = target("recommendations/1/notify/accepted").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Test
    public void RecommendationRecommendedNotificationResponse() {
        final Response responseWrapper = target("recommendations/1/notify/recommended").request(MediaType.APPLICATION_JSON).post(null);
        assertEquals(Response.Status.OK.getStatusCode(), responseWrapper.getStatus());
    }

    @Override
    AbstractBinder setBinder() {
        return null;
    }
}

