package utils;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;

import javax.ws.rs.core.Application;

/**
 * Created by martin on 5/1/15.
 */
abstract public class GenericTest extends JerseyTest {
    protected abstract AbstractBinder setBinder();

    protected Application configure() {
        //Para poder correr test en paralelo
        forceSet(TestProperties.CONTAINER_PORT, "0");
        return new TestConfig(setBinder());
    }

    class TestConfig extends ResourceConfig {
        public TestConfig(AbstractBinder binder){
            if(binder!=null) register(binder);
            packages(true, "com.hax.controllers");
        }
    }
}

