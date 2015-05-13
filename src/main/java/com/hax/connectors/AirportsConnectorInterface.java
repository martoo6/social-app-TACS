package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;


public interface AirportsConnectorInterface {
     ListenableFuture<String> getAirportAsync(String latitude, String longitude);
     
     ListenableFuture<String> getAirportAsync(String airportCode);
}
