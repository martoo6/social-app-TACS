package com.hax.connectors;

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

public class AutocompleteConnector implements AutocompleteConnectorInterface {

    public ListenableFuture<String> getAirportsAsync(String autocomplete) {
        String url = "https://api.despegar.com/v3/autocomplete";

        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("airport_result", 5)
                .queryParam("query", autocomplete)
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

