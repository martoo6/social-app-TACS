package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface FlightsServiceInterface {
    ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate);
    ListenableFuture<List<Flight>> getAllSavedFlights();
    ListenableFuture<Flight> createFlight(Flight flight, Integer userId);
}
