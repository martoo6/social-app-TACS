package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface TripsServiceInterface {
    ListenableFuture<String> getFlights(String from, String to, String fromDate,String toDate);
    ListenableFuture<List<Trip>> getAllSavedTrips();
    ListenableFuture<Trip> createTrip(Trip trip, Integer userId);
}
