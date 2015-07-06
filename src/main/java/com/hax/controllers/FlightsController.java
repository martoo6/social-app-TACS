package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;
import com.hax.services.TripsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.hax.utils.ControllerHelper.addControllerCallback;
import static com.hax.utils.ControllerHelper.fail;

@Singleton
@Service
@Path("flights")
public class FlightsController {
    @Inject
    TripsServiceInterface flightsService;
    @Inject
    UsersServiceInterface usersService;

//    /**
//     *
//     * @param flightID
//     * @param userId
//     * @return Estado de la operacion
//     * @throws JSONException
//     */
//    @Path("{flightID}/publish/{userID}")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String publishFlight(@PathParam("flightId") int flightID , @PathParam("token") int userId) throws JSONException
//    {
//        return new JSONObject().put("success", true).toString();
//    }


    /**
     *getAll
     * @param from
     * @param to
     * @param fromDate
     * @param toDate
     * @return Lista de vuelos que cumplen el criterio de busqueda
     * @throws JSONException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFilteredFlight(@QueryParam("origin") final String from, @QueryParam("destiny") final String to,
                                  @QueryParam("departure") final String fromDate, @QueryParam("arrival") String toDate,
                                  @Context HttpServletResponse asyncResponse) throws JSONException
    {
            return addControllerCallback(flightsService.getFlights(from, to, fromDate, toDate));
    }
}
