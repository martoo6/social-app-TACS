package com.hax.connectors;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.googlecode.objectify.ObjectifyService;
import com.hax.async.executors.Default;
import com.hax.models.Trip;
import com.hax.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by martin on 5/5/15.
 */
public class TripsGAERepository implements TripsRepositoryInterface {
    public Trip insert(final Trip trip) {
        if (trip == null) throw new RuntimeException("Trip is null");

        ObjectifyService.ofy().save().entity(trip).now();
        return trip;
    }

    public Trip update(final Trip trip) {
        if (trip == null) throw new RuntimeException("User is null");
        ObjectifyService.ofy().save().entity(trip).now();
        return trip;
    }

    public Trip get(final Long id){
        Trip trip = ObjectifyService.ofy().load().type(Trip.class).id(id).safeGet();
        if (trip == null) throw new RuntimeException("Trip not found: " + id);
        return trip;
    }


    public List<Trip> getAll(){
        return ObjectifyService.ofy().load().type(Trip.class).list();
    }


}
