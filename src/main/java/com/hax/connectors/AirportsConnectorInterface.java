package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.AirportResponse;


public interface AirportsConnectorInterface {
     String getAirportAsync(String latitude, String longitude);
     AirportResponse getAirportAsync(String airportCode);
}
