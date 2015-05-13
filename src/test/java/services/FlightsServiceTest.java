package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepository;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.User;
import com.hax.services.FlightsService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import utils.GenericTest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static com.hax.async.executors.Default.ex;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 5/2/15.
 */
public class FlightsServiceTest extends GenericTest {

    @Test
    public void getValidFlights() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(ex.submit(new Callable<String>() {
            public String call() throws Exception {
                //TODO: INgresar resuesta correcta, robalmente un POJO
                return "";
            }
        }));


        FlightsService fs = new FlightsService();
        fs.despegarConnector = dc;

        ListenableFuture<String> lf = fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10");

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlightsWrongDates() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(ex.submit(new Callable<String>() {
            public String call() throws Exception {
                throw new RuntimeException("400 !!!");
            }
        }));


        FlightsService fs = new FlightsService();
        fs.despegarConnector = dc;

        ListenableFuture<String> lf = fs.getFlights("EZE", "MIA", "2015-10-10", "2015-11-10");

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void getFlightsWrongDestiny() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("ZZZ", "MIA", "2015-11-10", "2015-10-10")).thenReturn(ex.submit(new Callable<String>() {
            public String call() throws Exception {
                throw new RuntimeException("400 !!!");
            }
        }));


        FlightsService fs = new FlightsService();
        fs.despegarConnector = dc;

        ListenableFuture<String> lf = fs.getFlights("ZZZ", "MIA", "2015-11-10", "2015-10-10");

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void createFlight() {
        FlightsRepositoryInterface fr = mock(FlightsRepositoryInterface.class);
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.get(0)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(any(User.class))).thenReturn(Futures.immediateFuture(user));
        when(fr.insert(any(Flight.class))).thenReturn(ex.submit(new Callable<Flight>() {
            public Flight call() throws Exception {
                return new Flight();
            }
        }));


        FlightsService fs = new FlightsService();
        fs.flightsRepository = fr;
        fs.userRepository = ur;

        ListenableFuture<Flight> lf = fs.createFlight(new Flight(), 0);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void createFlightFailed() {
        FlightsRepositoryInterface fr = mock(FlightsRepositoryInterface.class);
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);

        User user = new User();

        when(ur.get(0)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(any(User.class))).thenReturn(Futures.immediateFuture(user));

        when(fr.insert(any(Flight.class))).thenReturn(ex.submit(new Callable<Flight>() {
            public Flight call() throws Exception {
                throw  new RuntimeException("Error");
            }
        }));


        FlightsService fs = new FlightsService();
        fs.flightsRepository = fr;
        fs.userRepository = ur;

        ListenableFuture<Flight> lf = fs.createFlight(new Flight(), 0);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }

}
