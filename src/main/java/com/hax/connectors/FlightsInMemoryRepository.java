package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class FlightsInMemoryRepository implements FlightsRepositoryInterface{
    List<Flight> flights = new ArrayList<Flight>();

    public ListenableFuture<Flight> insert(final Flight flight) {
        if (flight == null) return Futures.immediateFuture(new Flight());
        flight.setId(flights.size());
        flights.add(flight);
        return Futures.immediateFuture(flight);
    }

    public ListenableFuture<Flight> get(final Integer id){
        for(Flight flight :flights){
            if(flight.getId()==id) return Futures.immediateFuture(flight);;
        }
        return Futures.immediateFailedFuture(new RuntimeException("Flight not found"));
    }

    public ListenableFuture<List<Flight>> getAll(){
        return Futures.immediateFuture(flights);
    }
}
