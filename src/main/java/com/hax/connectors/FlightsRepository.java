package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.Flight;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class FlightsRepository implements FlightsRepositoryInterface{
    ArrayList<Flight> flights = new ArrayList<Flight>();

    public ListenableFuture<Flight> insert(final Flight flight) {
        return Default.ex.submit(new Callable<Flight>() {
            public Flight call() throws Exception {
                //TODO: Esto va a ser con la base de datos, no es concurrente ni a ganchos
                if (flight == null) return new Flight();
                flight.setId(flights.size());
                flights.add(flight);
                return flight;
            }
        });
    }
}
