package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.AirportResponse;


public interface AirportsConnectorInterface {
     ListenableFuture<String> getAirportAsync(String latitude, String longitude);
     
     ListenableFuture<AirportResponse> getAirportAsync(String airportCode);
}
