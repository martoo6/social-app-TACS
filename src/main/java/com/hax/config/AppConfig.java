package com.hax.config;

import com.hax.connectors.DespegarConnector;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by martin on 5/1/15.
 */
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DespegarConnector.class).to(DespegarConnectorInterface.class);
                bind(FlightsService.class).to(FlightsServiceInterface.class);
            }
        });
        packages(true, "com.hax.controllers");
    }
}
