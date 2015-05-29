package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.connectors.FlightsInMemoryRepository;
import com.hax.models.Flight;
import com.hax.models.Segment;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class FlightsRepositoryTest extends GenericTest {

    @Test
    public void insertFlight(){
        FlightsInMemoryRepository dc = new FlightsInMemoryRepository();

        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, new BigDecimal(100), "Argentina", "USA");

        ListenableFuture<Flight> lf = dc.insert(flight);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlight() throws ExecutionException, InterruptedException {
        FlightsInMemoryRepository dc = new FlightsInMemoryRepository();



        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, new BigDecimal(100), "Argentina", "USA");

        dc.insert(flight).get();

        ListenableFuture<Flight> lf = dc.get(0);

        try {
            Flight f = lf.get();
            assertTrue(f == flight);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void getFlightMissingFlight() throws ExecutionException, InterruptedException {
        FlightsInMemoryRepository dc = new FlightsInMemoryRepository();

        ListenableFuture<Flight> lf = dc.get(0);

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Flight not found"));
        }
    }

    @Test
    public void getAllFlight() throws ExecutionException, InterruptedException {
        FlightsInMemoryRepository dc = new FlightsInMemoryRepository();

        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, new BigDecimal(100), "Argentina", "USA");

        dc.insert(flight).get();

        ListenableFuture<List<Flight>> lf = dc.getAll();

        try {
            List<Flight> lstFlights = lf.get();
            assertTrue(lstFlights.contains(flight));
            assertTrue(lstFlights.size()==1);
        } catch (Exception e) {
            assertTrue(false);
        }
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
