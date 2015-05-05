package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnector;
import com.hax.connectors.FlightsRepository;
import com.hax.models.Flight;
import com.hax.models.Ticket;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Ignore;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class FlightsRepositoryTest extends GenericTest {

    @Test
    public void insertFlight(){
        FlightsRepository dc = new FlightsRepository();

        Ticket t1 = new Ticket();
        Ticket t2 = new Ticket();
        Flight flight = new Flight(t1,t2, 100.0);

        ListenableFuture<Flight> lf = dc.insert(flight);

        try {
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }


    @Ignore @Test
    public void insertFlightFailed(){
        //TODO: pensar un error posible para la insersion (ademas de que la BD este caida)
    }

    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
