package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public class TripsInMemoryRepository implements TripsRepositoryInterface {
    List<Trip> trips = new ArrayList<Trip>();

    public ListenableFuture<Trip> insert(final Trip trip) {
        if (trip == null) return Futures.immediateFuture(new Trip());
        trip.setId(Long.valueOf(trips.size()));
        trips.add(trip);
        return Futures.immediateFuture(trip);
    }

    public ListenableFuture<Trip> get(final Long id){
        for(Trip trip : trips){
            if(trip.getId()==id) return Futures.immediateFuture(trip);;
        }
        return Futures.immediateFailedFuture(new RuntimeException("Trip not found"));
    }

    public ListenableFuture<List<Trip>> getAll(){
        return Futures.immediateFuture(trips);
    }
}
