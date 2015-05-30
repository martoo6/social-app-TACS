package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.services.AirportsServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import static com.hax.utils.ControllerHelper.addControllerCallback;

@Singleton
@Service
@Path("airports")
public class AirportsController {
    @Inject AirportsServiceInterface airportsService;

    /**
     * Obtiene el aeropuerto del pa�s m�s cercano a (latitude, longitude)
     * 
     * @param latitude Latitud de un punto
     * @param longitude Longitud de un punto
     * @param asyncResponse
     * @return Json del aeropuerto mas cercano a (latitud, longitud)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getAirport(@QueryParam("latitude") final String latitude,
                                    @QueryParam("longitude") final String longitude, 
                                    @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ListenableFuture<String> f = airportsService.getAirport(latitude, longitude);
        addControllerCallback(f, asyncResponse);
    }
    
    @Path("{airportCode}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getAirportFromCode(@PathParam("airportCode") String airportCode,
                                   @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ListenableFuture<String> f = airportsService.getAirport(airportCode);
        addControllerCallback(f, asyncResponse);
    }

}