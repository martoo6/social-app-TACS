package com.hax.connectors;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class FacebookConnector implements FacebookConnectorInterface{


    public ListenableFuture<Boolean> verifyAccessToken(String token){
        String url = "https://graph.facebook.com/me";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, Boolean>() {
            public Boolean apply(Response response) {
                return response.getStatus()==Response.Status.OK.getStatusCode();
            }
        });
    }


    public ListenableFuture<String> getLongLivedToken(String shortLivedToken) {
        String url = "https://www.facebook.com/oauth/access_token";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", App.config.getString("facebook.api.app.id"))
                .queryParam("client_secret", App.config.getString("facebook.api.app.secret"))
                .queryParam("fb_exchange_token", shortLivedToken)
                .request()
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

