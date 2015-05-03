package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import utils.GenericTest;

import com.hax.connectors.DespegarConnector;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class DesegarConnectorTest extends GenericTest {

    @Test
    public void validFlight(){
        DespegarConnector dc = new DespegarConnector();
        ListenableFuture<String> lf = dc.getFlightsAsync("BUE", "MIA", "2015-10-10", "2015-11-10");

        try {
            //TODO: Assert transformacion ??
            lf.get();
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void invalidDate(){
        DespegarConnector dc = new DespegarConnector();
        ListenableFuture<String> lf = dc.getFlightsAsync("BUE", "MIA", "2015-11-10", "2015-10-10");

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            //TODO: Assert mensaje de error
            assertTrue(true);
        }
    }

    @Test
    public void invalidLocation(){
        DespegarConnector dc = new DespegarConnector();
        ListenableFuture<String> lf = dc.getFlightsAsync("ZZZ", "MIA", "2015-10-10", "2015-11-10");

        try {
            lf.get();
            assertTrue(false);
        } catch (Exception e) {
            //TODO: Assert mensaje de error
            assertTrue(true);
        }
    }


    @Override
    protected AbstractBinder setBinder() {
        return null;
    }
}
