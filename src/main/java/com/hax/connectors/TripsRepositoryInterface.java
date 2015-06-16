package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
public interface TripsRepositoryInterface {
    ListenableFuture<Trip> insert(Trip trip);
    ListenableFuture<Trip> get(Long id);
    ListenableFuture<List<Trip>> getAll();
}
