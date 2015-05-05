package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import org.jvnet.hk2.annotations.Contract;

/**
 * Created by martin on 4/27/15.
 */

public interface FlightsServiceInterface {
    public ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate);

    public ListenableFuture<Flight> createFlight(Flight flight);
}
