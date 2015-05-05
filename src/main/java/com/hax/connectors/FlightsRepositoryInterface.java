package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;

/**
 * Created by martin on 5/5/15.
 */
public interface FlightsRepositoryInterface {
    public ListenableFuture<Flight> insert(Flight flight);
}
