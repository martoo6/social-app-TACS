package com.hax.config;

import com.hax.connectors.*;
import com.hax.services.AirportsService;
import com.hax.services.AirportsServiceInterface;
import com.hax.services.AutocompleteService;
import com.hax.services.AutocompleteServiceInterface;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;
import com.hax.services.UsersService;
import com.hax.services.UsersServiceInterface;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by martin on 5/1/15.
 */

/**
 * En esta clase se configura la inyeccion de dependencias de la aplicacion
 */
public class App extends ResourceConfig {
    public static PropertiesConfiguration config;

    public App() throws ConfigurationException {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                //-------------Connectors y Repositorios----------
                bind(FlightsConnector.class).to(FlightsConnectorInterface.class);
                bind(AirportsConnector.class).to(AirportsConnectorInterface.class);
                bind(AutocompleteConnector.class).to(AutocompleteConnectorInterface.class);
                bind(FlightsInMemoryRepository.class).to(FlightsRepositoryInterface.class);
                bind(UsersInMemoryRepository.class).to(UsersRepositoryInterface.class);
                //-------------------Servicios--------------
                bind(FlightsService.class).to(FlightsServiceInterface.class);
                bind(AirportsService.class).to(AirportsServiceInterface.class);
                bind(UsersService.class).to(UsersServiceInterface.class);
                bind(AutocompleteService.class).to(AutocompleteServiceInterface.class);

            }
        });
        packages(true, "com.hax.controllers");

        config = new PropertiesConfiguration("app.config");
        config.setReloadingStrategy(new FileChangedReloadingStrategy());
    }


}
