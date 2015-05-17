package com.hax.services;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.FutureHelper;
import com.hax.async.utils.Tuple2;
import com.hax.connectors.AutocompleteConnectorInterface;
import com.hax.connectors.FlightsConnectorInterface;
import com.hax.connectors.FlightsRepositoryInterface;
import com.hax.connectors.UsersRepositoryInterface;
import com.hax.models.Flight;
import com.hax.models.User;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by martin on 4/26/15.
 */
public class AutocompleteService implements AutocompleteServiceInterface{
    @Inject
    public AutocompleteConnectorInterface autocompleteConnector;


    public ListenableFuture<String> getAirports(String autocomplete) {
        return autocompleteConnector.getAirportsAsync(autocomplete);
    }
}
