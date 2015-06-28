package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.services.AutocompleteService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 5/2/15.
 */
public class AutocompleteServiceTest extends GenericTest {

    @Test
    public void getValidFlights() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        String str = "[\n" +
                " {\n" +
                "  \"id\": \"GEO_POINT-192636\",\n" +
                "  \"item\": {\n" +
                "   \"id\": \"192636\",\n" +
                "   \"type\": \"GEO_POINT\"\n" +
                "  },\n" +
                "  \"code\": \"AEP\",\n" +
                "  \"description\": \"Buenos Aires Jorge Newbery Airport, Buenos Aires, Argentina\",\n" +
                "  \"geolocation\": {\n" +
                "   \"latitude\": -34.556267,\n" +
                "   \"longitude\": -58.416611\n" +
                "  },\n" +
                "  \"facet\": \"AIRPORT\",\n" +
                "  \"fuzzy_search_result\": false,\n" +
                "  \"parent\": {\n" +
                "   \"id\": \"982\",\n" +
                "   \"type\": \"CITY\"\n" +
                "  },\n" +
                "  \"geo_point_type\": \"AIRPORT\",\n" +
                "  \"commercial\": true\n" +
                " },\n" +
                " {\n" +
                "  \"id\": \"GEO_POINT-192635\",\n" +
                "  \"item\": {\n" +
                "   \"id\": \"192635\",\n" +
                "   \"type\": \"GEO_POINT\"\n" +
                "  },\n" +
                "  \"code\": \"EZE\",\n" +
                "  \"description\": \"Buenos Aires Ministro Pistarini Ezeiza Airport, Buenos Aires, Argentina\",\n" +
                "  \"geolocation\": {\n" +
                "   \"latitude\": -34.81266,\n" +
                "   \"longitude\": -58.539761\n" +
                "  },\n" +
                "  \"facet\": \"AIRPORT\",\n" +
                "  \"fuzzy_search_result\": false,\n" +
                "  \"parent\": {\n" +
                "   \"id\": \"982\",\n" +
                "   \"type\": \"CITY\"\n" +
                "  },\n" +
                "  \"geo_point_type\": \"AIRPORT\",\n" +
                "  \"commercial\": true\n" +
                " }\n" +
                "]";

        when(dc.getAirportsAsync("buenos")).thenReturn(str);


        AutocompleteService acs = new AutocompleteService();
        acs.despegarConnector = dc;

        String lf = acs.getAirports("buenos");

        assertTrue(lf.equals(str));
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }

}
