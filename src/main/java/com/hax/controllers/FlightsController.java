package com.hax.controllers;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.utils.CallableWrapper;
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
import javax.ws.rs.core.Response;

import java.util.concurrent.Callable;

import static com.hax.async.executors.Default.ex;
import static com.hax.async.utils.FutureHelper.async;

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
    public String recommendFlight(@PathParam("flightId") int flightID , @PathParam("userId") int userId) throws JSONException
    {
        return new JSONObject().put("success", true).toString();
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

        Futures.addCallback(f, new FutureCallback<String>() {
            public void onSuccess(String s) {
                asyncResponse.resume(s);
            }

            public void onFailure(Throwable throwable) {
                WebApplicationException ex = new WebApplicationException(throwable.getMessage(), Response.Status.BAD_REQUEST);
                asyncResponse.resume(ex);
            }
        });
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
    public void createFlight(@Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ex.submit(new Callable<Void>() {
            public Void call() throws Exception {
                asyncResponse.resume(new JSONObject().put("success", true).toString());
                return null;
            }
        });


    }
}
