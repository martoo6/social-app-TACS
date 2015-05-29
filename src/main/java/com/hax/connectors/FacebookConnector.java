package com.hax.connectors;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.DespegarError;
import com.hax.utils.JsonHelper;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class FacebookConnector implements FacebookConnectorInterface{

    public ListenableFuture<String> getLongLivedToken(String shortLivedToken) {
        String url = "https://www.facebook.com/oauth/access_token";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", "{app-ip}")
                .queryParam("client_secret", "{app-secret}")
                .queryParam("fb_exchange_token", shortLivedToken)
                .request()
                .header("X-ApiKey","a97e70ca025a45adb3761471eb2d9b39")
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, String>() {
            public String apply(Response response) {
                if(response.getStatus()!=Response.Status.OK.getStatusCode()){
                    //TODO: Ver Manejo de Errores !
                    //TODO: Seguramente vuelve a la UI para obtener el Short lived correcto
                    throw new RuntimeException("Error !!");
                }
                return response.readEntity(String.class);
            }
        });
    }
}

