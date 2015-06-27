package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;

import java.util.List;

/**
 * Created by martin on 4/27/15.
 */

public interface FlightsServiceInterface {
    String getFlights(String from, String to, String fromDate, String toDate);
}
