package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.connectors.TripsInMemoryRepository;
import com.hax.models.Trip;
import com.hax.models.Flight;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.After;
import org.junit.Test;
import utils.GenericTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class TripsRepositoryTest extends GenericTest {

    @Test
    public void insertFlight(){
        TripsInMemoryRepository tripsRepo = new TripsInMemoryRepository();

        List<Flight> s1 = Arrays.asList(new Flight());
        List<Flight> s2 = Arrays.asList(new Flight());
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        try {
            tripsRepo.insert(trip);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlight() throws ExecutionException, InterruptedException {
        TripsInMemoryRepository dc = new TripsInMemoryRepository();

        List<Flight> s1 = Arrays.asList(new Flight());
        List<Flight> s2 = Arrays.asList(new Flight());
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        dc.insert(trip);

        try {
            Trip t = dc.get(0L);
            assertTrue(t == trip);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlightMissingFlight() throws ExecutionException, InterruptedException {
        TripsInMemoryRepository dc = new TripsInMemoryRepository();

        assertNull(dc.get(0L));
    }

    @Test
    public void getAllFlight() throws ExecutionException, InterruptedException {
        TripsInMemoryRepository dc = new TripsInMemoryRepository();


        List<Flight> s1 = Arrays.asList(new Flight());
        List<Flight> s2 = Arrays.asList(new Flight());
        Trip trip = new Trip(s1,s2, 100.0, "Argentina", "USA", "2m", "2m");

        dc.insert(trip);

        try {
            List<Trip> lstTrips = dc.getAll();
            assertTrue(lstTrips.contains(trip));
            assertTrue(lstTrips.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @After
    public void tearDown() {
        TripsInMemoryRepository.tearDown();
    }

    @Override
    protected AbstractBinder setBinder() {
        try {
            App.config = new PropertiesConfiguration("app.config");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
