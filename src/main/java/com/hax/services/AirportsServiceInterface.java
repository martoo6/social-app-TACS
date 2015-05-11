package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;


public interface AirportsServiceInterface {
    public ListenableFuture<String> getAirport(String latitude, String longitude);
}
