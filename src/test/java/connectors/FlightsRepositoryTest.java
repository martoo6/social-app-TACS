package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.FlightsRepository;
import com.hax.models.Flight;
import com.hax.models.Segment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

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
        FlightsRepository dc = new FlightsRepository();

        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, 100.0, "Argentina", "USA");

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
        FlightsRepository dc = new FlightsRepository();



        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, 100.0, "Argentina", "USA");

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
        FlightsRepository dc = new FlightsRepository();

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
        FlightsRepository dc = new FlightsRepository();

        List<Segment> s1 = Arrays.asList(new Segment());
        List<Segment> s2 = Arrays.asList(new Segment());
        Flight flight = new Flight(s1,s2, 100.0, "Argentina", "USA");

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
        return null;
    }
}
