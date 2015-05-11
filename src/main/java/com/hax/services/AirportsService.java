package com.hax.services;

import com.hax.connectors.AirportsConnectorInterface;
import com.google.common.util.concurrent.ListenableFuture;
import javax.inject.Inject;

/**
 * Created by martin on 4/26/15.
 */
public class AirportsService implements AirportsServiceInterface{
    @Inject
    public AirportsConnectorInterface airportsConnector;

    /**
     * Obtiene el aeropuerto del país más cercano a (latitude, longitude)
     * 
     * @param latitude Latitud de un punto
     * @param longitude Longitud de un punto
     * @return Json del aeropuerto mas cercano a (latitud, longitud)
     */
    public ListenableFuture<String> getAirport(String latitude, String longitude){
        return airportsConnector.getAirportAsync(latitude , longitude);
    }
}
