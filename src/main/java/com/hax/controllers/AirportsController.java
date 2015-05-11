package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import static com.hax.async.utils.FutureHelper.addControllerCallback;
import com.hax.services.AirportsServiceInterface;

@Singleton
@Service
@Path("airports")
public class AirportsController {
    @Inject AirportsServiceInterface airportsService;

    /**
     * Obtiene el aeropuerto del país más cercano a (latitude, longitude)
     * 
     * @param latitude Latitud de un punto
     * @param longitude Longitud de un punto
     * @param asyncResponse
     * @return Json del aeropuerto mas cercano a (latitud, longitud)
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getFilteredFlight(@QueryParam("latitude") final String latitude,
                                    @QueryParam("longitude") final String longitude, 
                                    @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ListenableFuture<String> f = airportsService.getAirport(latitude, longitude);
        addControllerCallback(f, asyncResponse);
    }

}