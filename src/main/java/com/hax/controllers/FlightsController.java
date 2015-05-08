package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import com.hax.models.Recommendation;
import com.hax.services.FlightsService;
import com.hax.services.FlightsServiceInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.Callable;

import static com.hax.async.executors.Default.ex;
import static com.hax.async.utils.FutureHelper.addControllerCallback;

@Singleton
@Service
@Path("flights")
public class FlightsController {
    @Inject FlightsServiceInterface flightsService;

    /**
     *
     * @param flightID
     * @param userId
     * @return Estado de la operacion
     * @throws JSONException
     */
    @Path("{flightID}/publish/{userID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String publishFlight(@PathParam("flightId") int flightID , @PathParam("userId") int userId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
    }


    /**
     *
     * @param flightID
     * @param userId
     * @return Estado de la operacion
     * @throws JSONException
     */
    @Path("{flightID}/recommendations/{userID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void recommendFlight(@PathParam("flightId") int flightID ,
                                @PathParam("userId") int userId,
                                @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        //TODO: El cero esta harcodeado y tiene que ser el id del usuario loggeado actualmente.
        ListenableFuture<Recommendation> f= flightsService.recommendFlight(flightID, 0, userId);
        addControllerCallback(f, asyncResponse);
    }


    /**
     *
     * @param from
     * @param to
     * @param fromDate
     * @param toDate
     * @return Lista de vuelos que cumplen el criterio de busqueda
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void getFilteredFlight(@QueryParam("origin") final String from, @QueryParam("destiny") final String to,
                                    @QueryParam("departure") final String fromDate, @QueryParam("arrival") String toDate,
                                    @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ListenableFuture<String> f = flightsService.getFlights(from, to, fromDate, toDate);
        addControllerCallback(f, asyncResponse);
    }


    /**
     * JSON DATA:
     {
     "way-ticket":
     {
     "origin":"EZE, Buenos Aires, Argentina",
     "destiny":"MNT, Montevideo, Uruguay",
     "company":"American Airlines",
     "flight-num":"B34A5",
     "departure-time":"20-04-2015 18:30",
     "duration":"1h 20m",
     "price":"532"
     },
     "return-ticket":
     {
     "origin":"MNT, Montevideo, Uruguay",
     "destiny":"EZE, Buenos Aires, Argentina",
     "company":"American Airlines",
     "flight-number":"A98P5",
     "departure-time":"09-06-2015 06:10",
     "duration":"50m",
     "price":"532"
     },
     "total-price":"532"
     }
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createFlight(final Flight flight, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        //TODO: Parsear JSON entrante y convertirlo a Flight
        ListenableFuture<Flight> f = flightsService.createFlight(flight);
        addControllerCallback(f, asyncResponse);
    }
}
