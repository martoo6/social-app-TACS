package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;

import javax.inject.Inject;

/**
 * Created by martin on 4/26/15.
 */
public class AutocompleteService implements AutocompleteServiceInterface{
    @Inject
    public DespegarConnectorInterface despegarConnector;


    public String getAirports(String autocomplete) {
        return despegarConnector.getAirportsAsync(autocomplete);
    }
}
