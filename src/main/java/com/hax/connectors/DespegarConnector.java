package com.hax.connectors;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.config.App;
import com.hax.models.DespegarError;
import com.hax.utils.JsonHelper;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class DespegarConnector implements DespegarConnectorInterface {

    public String getFlightsAsync(String from, String to, String fromDate,String toDate){
        String url = "https://api.despegar.com/v3/flights/itineraries";
        Response response =  ClientBuilder.newClient()
                .property(ClientProperties.CONNECT_TIMEOUT, 20000)
                .property(ClientProperties.READ_TIMEOUT, 20000)
                .target(url)
                .queryParam("site", "ar")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("departure_date", fromDate)
                .queryParam("return_date", toDate)
                .queryParam("adults", "1")
                .request()
                .header("X-ApiKey", App.config.getString("despegar.api.key.value"))
                .get();

                if(response.getStatus()!=Response.Status.OK.getStatusCode()){
                    DespegarError de = JsonHelper.readValue(response, DespegarError.class);
                    Joiner joiner = Joiner.on(". ").skipNulls();
                    String errorMsg = joiner.join(de.getCauses());
                    throw new RuntimeException(errorMsg);
                }
                return response.readEntity(String.class);
    }

    public String getAirportsAsync(String autocomplete) {
        String url = "https://api.despegar.com/v3/autocomplete";

        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("airport_result", 5)
                .queryParam("query", autocomplete)
                .request()
                .header("X-ApiKey", App.config.getString("despegar.api.key.value"))
                .get();

                if(response.getStatus()!=Response.Status.OK.getStatusCode()){
                    DespegarError de = JsonHelper.readValue(response, DespegarError.class);
                    Joiner joiner = Joiner.on(". ").skipNulls();
                    String errorMsg = joiner.join(de.getCauses());
                    throw new RuntimeException(errorMsg);
                }
                return response.readEntity(String.class);
    }
}

