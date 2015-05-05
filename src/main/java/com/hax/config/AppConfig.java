package com.hax.config;

import com.hax.connectors.DespegarConnector;
import com.hax.connectors.DespegarConnectorInterface;
import com.hax.connectors.FlightsRepository;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by martin on 5/1/15.
 */

/**
 * En esta clase se configura la inyeccion de dependencias de la aplicacion
 */
public class AppConfig extends ResourceConfig {

    public AppConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(DespegarConnector.class).to(DespegarConnectorInterface.class);
                bind(FlightsService.class).to(FlightsServiceInterface.class);
                bind(FlightsRepository.class).to(FlightsRepositoryInterface.class);
            }
        });
        packages(true, "com.hax.controllers");
    }
}
