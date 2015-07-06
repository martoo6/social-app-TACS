package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.connectors.DespegarConnector;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class DespegarConnectorTest extends GenericTest {

    @Test
    public void validFlight(){
        DespegarConnector dc = new DespegarConnector();

        try {
            //TODO: Assert transformacion ??
            dc.getFlightsAsync("BUE", "MIA", "2015-10-10", "2015-11-10");
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    public void invalidDate(){
        DespegarConnector dc = new DespegarConnector();

        try {
            dc.getFlightsAsync("BUE", "MIA", "2015-11-10", "2015-10-10");
            assertTrue(false);
        } catch (Exception e) {
            //TODO: Assert mensaje de error
            assertTrue(true);
        }
    }

    @Test
    public void invalidLocation(){
        DespegarConnector dc = new DespegarConnector();

        try {
            dc.getFlightsAsync("ZZZ", "MIA", "2015-10-10", "2015-11-10");
            assertTrue(false);
        } catch (Exception e) {
            //TODO: Assert mensaje de error
            assertTrue(true);
        }
    }

    @Test
    public void validComplete(){
        DespegarConnector dc = new DespegarConnector();

        try {
            //TODO: Assert transformacion ??
            dc.getAirportsAsync("buenos");
            assertTrue(true);
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
