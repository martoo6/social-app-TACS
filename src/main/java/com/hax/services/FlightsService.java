package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.inject.Inject;
import com.hax.connectors.DespegarConnectorInterface;

/**
 * Created by martin on 4/26/15.
 */

public class FlightsService implements FlightsServiceInterface{
    DespegarConnectorInterface despegarConnector;

    @Inject
    public FlightsService(DespegarConnectorInterface despegarConnector){
        this.despegarConnector = despegarConnector;
    }

    public ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate){
        return despegarConnector.getFlightsAsync(from, to , fromDate, toDate);
    }
}
