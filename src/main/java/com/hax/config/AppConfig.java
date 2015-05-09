package com.hax.config;

import com.hax.connectors.*;
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
                bind(FlightsRepository.class).to(FlightsRepositoryInterface.class);
                bind(RecommendationsRepository.class).to(RecommendationsRepositoryInterface.class);
                bind(UsersRepository.class).to(UsersRepositoryInterface.class);
                bind(FlightsService.class).to(FlightsServiceInterface.class);

            }
        });
        packages(true, "com.hax.controllers");
    }
}
