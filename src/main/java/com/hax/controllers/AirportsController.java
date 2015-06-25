package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.AirportResponse;
import com.hax.services.AirportsServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response getAirport(@QueryParam("latitude") final String latitude,
                                    @QueryParam("longitude") final String longitude, 
                                    @Context HttpServletResponse asyncResponse) throws JSONException
    {
        ListenableFuture<String> f = airportsService.getAirport(latitude, longitude);
        return addControllerCallback(f);
    }
    
    @Path("{airportCode}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAirportFromCode(@PathParam("airportCode") String airportCode,
                                   @Context HttpServletResponse asyncResponse) throws JSONException
    {
        ListenableFuture<AirportResponse> f = airportsService.getAirport(airportCode);
        return addControllerCallback(f);
    }

}