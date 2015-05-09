package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;

import java.util.ArrayList;

/**
 * Created by martin on 5/5/15.
 */
public interface FlightsRepositoryInterface {
    public ListenableFuture<Flight> insert(Flight flight);
    public ListenableFuture<Flight> get(Integer id);
    public ListenableFuture<ArrayList<Flight>> getAll();
}
