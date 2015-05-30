package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface FlightsRepositoryInterface {
    ListenableFuture<Flight> insert(Flight flight);
    ListenableFuture<Flight> get(Integer id);
    ListenableFuture<List<Flight>> getAll();
}
