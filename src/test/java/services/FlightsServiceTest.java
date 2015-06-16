package services;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FacebookConnectorInterface;
import com.hax.connectors.TripsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Trip;
import com.hax.models.User;
import com.hax.models.fb.FbVerify;
import com.hax.services.TripsService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import java.util.concurrent.Callable;

import static com.hax.async.executors.Default.ex;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by martin on 5/2/15.
 */
public class FlightsServiceTest extends GenericTest {

    @Test
    public void getValidFlights() {
        DespegarConnectorInterface dc = mock(DespegarConnectorInterface.class);

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(Futures.immediateFuture(""));


        TripsService fs = new TripsService();
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

        when(dc.getFlightsAsync("EZE", "MIA", "2015-10-10", "2015-11-10")).thenReturn(Futures.<String>immediateFailedFuture(new RuntimeException("Error")));


        TripsService fs = new TripsService();
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

        when(dc.getFlightsAsync("ZZZ", "MIA", "2015-11-10", "2015-10-10")).thenReturn(Futures.<String>immediateFailedFuture(new RuntimeException("Error")));


        TripsService fs = new TripsService();
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
        TripsRepositoryInterface fr = mock(TripsRepositoryInterface.class);
        UsersRepositoryInterface ur = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fbConn = mock(FacebookConnectorInterface.class);

        User user = new User();

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId(0L);

        when(ur.get(0L)).thenReturn(Futures.immediateFuture(user));
        when(ur.update(any(User.class))).thenReturn(Futures.immediateFuture(user));
        when(fr.insert(any(Trip.class))).thenReturn(Futures.immediateFuture(new Trip()));
        when(fbConn.verifyAccessToken("tokenDePrueba")).thenReturn(Futures.immediateFuture(fbVerify));

        TripsService fs = new TripsService();
        fs.flightsRepository = fr;
        fs.userRepository = ur;
        fs.fbConnector = fbConn;

        ListenableFuture<Trip> lf = fs.createTrip(new Trip(), "tokenDePrueba");

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void createFlightFailed() {
        TripsRepositoryInterface tripRepo = mock(TripsRepositoryInterface.class);
        UsersRepositoryInterface userRepo = mock(UsersRepositoryInterface.class);
        FacebookConnectorInterface fbConn = mock(FacebookConnectorInterface.class);

        User user = new User();

        FbVerify fbVerify = new FbVerify();
        fbVerify.setId(1L);

        when(userRepo.get(0L)).thenReturn(Futures.immediateFuture(user));
        when(userRepo.update(any(User.class))).thenReturn(Futures.immediateFuture(user));

        when(tripRepo.insert(any(Trip.class))).thenReturn(Futures.<Trip>immediateFailedFuture(new RuntimeException("Error")));

        when(fbConn.verifyAccessToken("tokenDePrueba")).thenReturn(Futures.immediateFuture(fbVerify));


        TripsService fs = new TripsService();
        fs.flightsRepository = tripRepo;
        fs.userRepository = userRepo;
        fs.fbConnector = fbConn;

        ListenableFuture<Trip> lf = fs.createTrip(new Trip(), "tokenDePrueba");

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
