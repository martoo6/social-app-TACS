//package controllers;
//
//import com.hax.services.AutocompleteServiceInterface;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.junit.Test;
//import utils.GenericTest;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import static com.google.common.util.concurrent.Futures.immediateFuture;
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Created by martin on 20/04/15.
// */
//
//
//public class AutocompleteControllerTest extends GenericTest {
//
//    AutocompleteServiceInterface acs = mock(AutocompleteServiceInterface.class);
//
//    @Override
//    protected AbstractBinder setBinder() {
//        return new AbstractBinder() {
//            @Override
//            protected void configure() {
//                bind(acs).to(AutocompleteServiceInterface.class);
//            }
//        };
//    }
//
//    @Test
//    public void getFilteredFlightsResponse() {
//        String autocompleteResponse = "[\n" +
//                " {\n" +
//                "  \"id\": \"GEO_POINT-192636\",\n" +
//                "  \"item\": {\n" +
//                "   \"id\": \"192636\",\n" +
//                "   \"type\": \"GEO_POINT\"\n" +
//                "  },\n" +
//                "  \"code\": \"AEP\",\n" +
//                "  \"description\": \"Buenos Aires Jorge Newbery Airport, Buenos Aires, Argentina\",\n" +
//                "  \"geolocation\": {\n" +
//                "   \"latitude\": -34.556267,\n" +
//                "   \"longitude\": -58.416611\n" +
//                "  },\n" +
//                "  \"facet\": \"AIRPORT\",\n" +
//                "  \"fuzzy_search_result\": false,\n" +
//                "  \"parent\": {\n" +
//                "   \"id\": \"982\",\n" +
//                "   \"type\": \"CITY\"\n" +
//                "  },\n" +
//                "  \"geo_point_type\": \"AIRPORT\",\n" +
//                "  \"commercial\": true\n" +
//                " },\n" +
//                " {\n" +
//                "  \"id\": \"GEO_POINT-192635\",\n" +
//                "  \"item\": {\n" +
//                "   \"id\": \"192635\",\n" +
//                "   \"type\": \"GEO_POINT\"\n" +
//                "  },\n" +
//                "  \"code\": \"EZE\",\n" +
//                "  \"description\": \"Buenos Aires Ministro Pistarini Ezeiza Airport, Buenos Aires, Argentina\",\n" +
//                "  \"geolocation\": {\n" +
//                "   \"latitude\": -34.81266,\n" +
//                "   \"longitude\": -58.539761\n" +
//                "  },\n" +
//                "  \"facet\": \"AIRPORT\",\n" +
//                "  \"fuzzy_search_result\": false,\n" +
//                "  \"parent\": {\n" +
//                "   \"id\": \"982\",\n" +
//                "   \"type\": \"CITY\"\n" +
//                "  },\n" +
//                "  \"geo_point_type\": \"AIRPORT\",\n" +
//                "  \"commercial\": true\n" +
//                " }\n" +
//                "]";
//
//        when(acs.getAirports("buenos")).thenReturn(immediateFuture(autocompleteResponse));
//
//
//        final Response response = target("autocomplete/airports/buenos")
//                .request(MediaType.APPLICATION_JSON).get();
//        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
//    }
//}
