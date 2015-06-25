package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.AirportResponse;


public interface AirportsServiceInterface {
    public ListenableFuture<String> getAirport(String latitude, String longitude);
    
    public ListenableFuture<AirportResponse> getAirport(String airportCode);
}
