package com.hax.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.models.DespegarError;
import com.hax.utils.JsonHelper;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class FlightsConnector implements FlightsConnectorInterface {

    public ListenableFuture<String> getFlightsAsync(String from, String to, String fromDate,String toDate){
        String url = "https://api.despegar.com/v3/flights/itineraries";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("site", "ar")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("departure_date", fromDate)
                .queryParam("return_date", toDate)
                .queryParam("adults", "1")
                .request()
                .header(App.config.getString("despegar.api.key.name"), App.config.getString("despegar.api.key.value"))
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, String>() {
            public String apply(Response response) {
                if(response.getStatus()!=Response.Status.OK.getStatusCode()){
                    DespegarError de = JsonHelper.readValue(response, DespegarError.class);
                    Joiner joiner = Joiner.on(". ").skipNulls();
                    String errorMsg = joiner.join(de.getCauses());
                    throw new RuntimeException(errorMsg);
                }
                return response.readEntity(String.class);
            }
        });
    }
}

