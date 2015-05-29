package connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.connectors.AutocompleteConnector;
import com.hax.connectors.FlightsConnector;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.junit.Test;
import utils.GenericTest;

import static org.junit.Assert.assertTrue;

/**
 * Created by martin on 5/3/15.
 */
public class AutocompleteConnectorTest extends GenericTest {


    @Test
    public void validComplete(){
        AutocompleteConnector acc = new AutocompleteConnector();
        ListenableFuture<String> lf = acc.getAirportsAsync("buenos");

        try {
            //TODO: Assert transformacion ??
            lf.get();
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
