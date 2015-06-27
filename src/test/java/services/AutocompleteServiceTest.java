//package services;
//
//import com.google.common.util.concurrent.Futures;
//import com.google.common.util.concurrent.ListenableFuture;
//import com.hax.connectors.DespegarConnectorInterface;
//import com.hax.services.AutocompleteService;
//import org.glassfish.hk2.utilities.binding.AbstractBinder;
//import org.junit.Test;
//import utils.GenericTest;
//
//import java.util.concurrent.Callable;
//
//import static com.hax.async.executors.Default.ex;
//import static org.junit.Assert.assertTrue;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
///**
// * Created by martin on 5/2/15.
// */
//public class AutocompleteServiceTest extends GenericTest {
//
//    @Test
//    public void getValidFlights() {
//        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);
//
//        when(dc.getAirportsAsync("buenos")).thenReturn(Futures.immediateFuture(
//                "[\n" +
//                        " {\n" +
//                        "  \"id\": \"GEO_POINT-192636\",\n" +
//                        "  \"item\": {\n" +
//                        "   \"id\": \"192636\",\n" +
//                        "   \"type\": \"GEO_POINT\"\n" +
//                        "  },\n" +
//                        "  \"code\": \"AEP\",\n" +
//                        "  \"description\": \"Buenos Aires Jorge Newbery Airport, Buenos Aires, Argentina\",\n" +
//                        "  \"geolocation\": {\n" +
//                        "   \"latitude\": -34.556267,\n" +
//                        "   \"longitude\": -58.416611\n" +
//                        "  },\n" +
//                        "  \"facet\": \"AIRPORT\",\n" +
//                        "  \"fuzzy_search_result\": false,\n" +
//                        "  \"parent\": {\n" +
//                        "   \"id\": \"982\",\n" +
//                        "   \"type\": \"CITY\"\n" +
//                        "  },\n" +
//                        "  \"geo_point_type\": \"AIRPORT\",\n" +
//                        "  \"commercial\": true\n" +
//                        " },\n" +
//                        " {\n" +
//                        "  \"id\": \"GEO_POINT-192635\",\n" +
//                        "  \"item\": {\n" +
//                        "   \"id\": \"192635\",\n" +
//                        "   \"type\": \"GEO_POINT\"\n" +
//                        "  },\n" +
//                        "  \"code\": \"EZE\",\n" +
//                        "  \"description\": \"Buenos Aires Ministro Pistarini Ezeiza Airport, Buenos Aires, Argentina\",\n" +
//                        "  \"geolocation\": {\n" +
//                        "   \"latitude\": -34.81266,\n" +
//                        "   \"longitude\": -58.539761\n" +
//                        "  },\n" +
//                        "  \"facet\": \"AIRPORT\",\n" +
//                        "  \"fuzzy_search_result\": false,\n" +
//                        "  \"parent\": {\n" +
//                        "   \"id\": \"982\",\n" +
//                        "   \"type\": \"CITY\"\n" +
//                        "  },\n" +
//                        "  \"geo_point_type\": \"AIRPORT\",\n" +
//                        "  \"commercial\": true\n" +
//                        " }\n" +
//                        "]"
//        ));
//
//
//        AutocompleteService acs = new AutocompleteService();
//        acs.despegarConnector = dc;
//
//        ListenableFuture<String> lf = acs.getAirports("buenos");
//
//        try {
//            lf.get();
//            assertTrue(true);
//        } catch (Exception e) {
//            assertTrue(false);
//        }
//    }
//
//    @Override
//    protected AbstractBinder setBinder() {
//        return null;
//    }
//
//}
