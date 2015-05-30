package com.hax.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.connectors.AirportsConnectorInterface;

import javax.inject.Inject;

/**
 * Created by martin on 4/26/15.
 */
public class AirportsService implements AirportsServiceInterface{
    @Inject
    public AirportsConnectorInterface airportsConnector;

    /**
     * Obtiene el aeropuerto del pa�s m�s cercano a (latitude, longitude)
     * 
     * @param latitude Latitud de un punto
     * @param longitude Longitud de un punto
     * @return Json del aeropuerto mas cercano a (latitud, longitud)
     */
    public ListenableFuture<String> getAirport(String latitude, String longitude){
        return airportsConnector.getAirportAsync(latitude , longitude);
    }
    
    /**
     * Obtiene info de un aeropuerto a partir de su airportCode
     * 
     * @param airportCode
     * @return Json del aeropuerto de codigo airportCode
     */
    public ListenableFuture<String> getAirport(String airportCode){
        return airportsConnector.getAirportAsync(airportCode);
    }
}
