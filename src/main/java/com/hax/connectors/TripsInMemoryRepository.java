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
    static List<Trip> trips = new ArrayList<Trip>();

    public Trip insert(final Trip trip) {
        if (trip == null) throw new RuntimeException("Missing trip");
        trip.setId(Long.valueOf(trips.size()));
        trips.add(trip);
        return trip;
    }

    public Trip get(final Long id){
        for(Trip trip : trips){
            if(trip.getId().equals(id)) return trip;
        }
        return null;
    }

    public List<Trip> getAll(){
        return (trips);
    }

    static public void tearDown(){
        trips.clear();
    }
}
