package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.models.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class FlightsRepository implements FlightsRepositoryInterface{
    List<Flight> flights = new ArrayList<Flight>();

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

    public ListenableFuture<Flight> get(final Integer id){
        return Default.ex.submit(new Callable<Flight>() {
            public Flight call() throws Exception {
                //TODO: Esto va a ser con la base de datos, no es concurrente ni a ganchos
                for(Flight flight :flights){
                    if(flight.getId()==id) return flight;
                }
                throw new RuntimeException("Flight not found");
            }
        });
    }

    public ListenableFuture<List<Flight>> getAll(){
        return Default.ex.submit(new Callable<List<Flight>>() {
            public List<Flight> call() throws Exception {
                return flights;
            }
        });
    }
}
