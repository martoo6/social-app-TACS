package com.hax.controllers;

import com.hax.services.AutocompleteServiceInterface;
import org.json.JSONException;
import org.jvnet.hk2.annotations.Service;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.hax.utils.ControllerHelper.addControllerCallback;

@Singleton
@Service
@Path("healthcheck")
public class HealthCheckController {

    @GET
    public void publishFlight(@Suspended final AsyncResponse asyncResponse) throws JSONException
    {
        asyncResponse.resume(Response.status(200).type("text/plain").build());
    }

}
