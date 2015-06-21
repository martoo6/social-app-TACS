package com.hax.controllers;

import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.Trip;
import com.hax.services.TripsServiceInterface;
import com.hax.services.UsersServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.hax.utils.ControllerHelper.addControllerCallback;
import static com.hax.utils.ControllerHelper.fail;

@Singleton
@Service
@Path("trips")
public class TripsController {
    @Inject
    TripsServiceInterface tripsService;
    @Inject
    UsersServiceInterface usersService;





    /**
     * JSON DATA:
     {
     "way-ticket":
     {
     "origin":"EZE, Buenos Aires, Argentina",
     "destiny":"MNT, Montevideo, Uruguay",
     "company":"American Airlines",
     "trip-num":"B34A5",
     "departure-time":"20-04-2015 18:30",
     "duration":"1h 20m"
     },
     "return-ticket":
     {
     "origin":"MNT, Montevideo, Uruguay",
     "destiny":"EZE, Buenos Aires, Argentina",
     "company":"American Airlines",
     "trip-number":"A98P5",
     "departure-time":"09-06-2015 06:10",
     "duration":"50m"
     },
     "total-price":"532"
     }
     */

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTrip(final Trip trip,@Context HttpHeaders hh) throws JSONException
    {
        String optToken = hh.getHeaderString("token");
        if(optToken==null) {
            return fail("Missing token");
        } else {
            return addControllerCallback(tripsService.createTrip(trip, optToken));
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
    public Response getAllSavedTrips(@Context HttpServletResponse asyncResponse) throws JSONException
    {
        ListenableFuture<List<Trip>> f = tripsService.getAllSavedTrips();
        return addControllerCallback(f);
    }
}
