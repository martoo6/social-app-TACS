package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface TripsRepositoryInterface {
    Trip insert(Trip trip);
    Trip get(Long id);
    List<Trip> getAll();
}
