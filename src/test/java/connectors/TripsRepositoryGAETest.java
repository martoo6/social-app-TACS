package connectors;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import com.hax.config.App;
import com.hax.connectors.TripsGAERepository;
import com.hax.connectors.TripsRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.Trip;
import com.hax.utils.OfyHelper;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.GenericTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class TripsRepositoryGAETest extends GenericTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable closeable;

    @Before
    public void setUp() {
        helper.setUp();
        closeable = ObjectifyService.begin();
    }

    @After
    public void tearDown() {
        closeable.close();
        helper.tearDown();
    }

    @Test
    public void insertTripGAE(){
        TripsRepositoryInterface tripsRepo = new TripsGAERepository();

        List<Flight> s1 = Arrays.asList(new Flight("1","1","1","1","1","1"));
        List<Flight> s2 = Arrays.asList(new Flight("2","2","2","2","2","2"));
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        try {
            Trip t = tripsRepo.insert(trip);
            assertTrue(t.getId()== 1L);
            assertTrue(t.getPrice() == 100.0);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getTrip(){
        TripsRepositoryInterface tripsRepo = new TripsGAERepository();

        List<Flight> s1 = Arrays.asList(new Flight());
        List<Flight> s2 = Arrays.asList(new Flight());
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        tripsRepo.insert(trip);

        assertTrue(tripsRepo.get(1L) == trip);
    }

    @Test
    public void getFlightMissingTrip(){
        TripsRepositoryInterface tripsRepo = new TripsGAERepository();
        assertNull(tripsRepo.get(1L));
    }

    @Test
    public void getAllTrip(){
        TripsRepositoryInterface tripsRepo = new TripsGAERepository();


        List<Flight> s1 = Arrays.asList(new Flight());
        List<Flight> s2 = Arrays.asList(new Flight());
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        tripsRepo.insert(trip);

        try {
            List<Trip> lstTrips = tripsRepo.getAll();
            assertTrue(lstTrips.contains(trip));
            assertTrue(lstTrips.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Override
    protected AbstractBinder setBinder() {
        try {
            App.config = new PropertiesConfiguration("app.config");
            new OfyHelper().contextInitialized(null);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
