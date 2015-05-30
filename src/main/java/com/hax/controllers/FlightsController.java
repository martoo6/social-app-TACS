package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Flight;
import com.hax.services.FlightsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;
import org.json.JSONObject;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static com.hax.utils.ControllerHelper.addControllerCallback;
import static com.hax.utils.ControllerHelper.fail;

@Singleton
@Service
@Path("flights")
public class FlightsController {
    @Inject
    FlightsServiceInterface flightsService;
    @Inject
    UsersServiceInterface usersService;

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
    public void getFilteredFlight(@QueryParam("origin") final String from, @QueryParam("destiny") final String to,
                                  @QueryParam("departure") final String fromDate, @QueryParam("arrival") String toDate,
                                  @QueryParam("userId") Integer userId,
                                  @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        if(userId==null){
            addControllerCallback(flightsService.getFlights(from, to, fromDate, toDate), asyncResponse);
        } else{
            addControllerCallback(usersService.getFlights(userId), asyncResponse);
        }
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
     "duration":"1h 20m"
     },
     "return-ticket":
     {
     "origin":"MNT, Montevideo, Uruguay",
     "destiny":"EZE, Buenos Aires, Argentina",
     "company":"American Airlines",
     "flight-number":"A98P5",
     "departure-time":"09-06-2015 06:10",
     "duration":"50m"
     },
     "total-price":"532"
     }
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void createFlight(final Flight flight,@Context HttpHeaders hh, @Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        String optUserId = hh.getHeaderString("userId");
        if(optUserId==null) {
            fail("Missing userId", asyncResponse);
        } else {
            Integer userId = Integer.parseInt(optUserId);
            addControllerCallback(flightsService.createFlight(flight, userId), asyncResponse);
        }
    }
    
    
    /**
     * ruta de prueba para ver todos los vuelos guardados
     * 
     * @throws JSONException 
     */
    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public void getAllSavedFlights(@Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        ListenableFuture<List<Flight>> f = flightsService.getAllSavedFlights();
        addControllerCallback(f, asyncResponse);
    }
}
