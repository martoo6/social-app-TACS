package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.DespegarConnectorInterface;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;

/**
 * Created by martin on 4/26/15.
 */
public class FlightsService implements FlightsServiceInterface{
    @Inject
    DespegarConnectorInterface despegarConnector;


//    public FlightsService(DespegarConnectorInterface despegarConnector){
//        this.despegarConnector = despegarConnector;
//    }

    public ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate){
        return despegarConnector.getFlightsAsync(from, to , fromDate, toDate);
    }
}
