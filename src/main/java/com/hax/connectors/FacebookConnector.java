package com.hax.connectors;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.config.App;
import com.hax.models.fb.FbFeed;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbVerify;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class FacebookConnector implements FacebookConnectorInterface{
    String fbUrl = "https://graph.facebook.com/";

    public ListenableFuture<FbVerify> verifyAccessToken(String token){
        String url = fbUrl+"me";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, FbVerify>() {
            public FbVerify apply(Response response) {
                if(response.getStatus()==Response.Status.OK.getStatusCode()){
                    return response.readEntity(FbVerify.class);
                }
                return null;
            }
        });
    }

    public ListenableFuture<FbFriends> getUserFriends(String token){
        String url = fbUrl+"me/friends?limit=500&offset=0";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, FbFriends>() {
            public FbFriends apply(Response response) {
                if(response.getStatus()==Response.Status.OK.getStatusCode()){
                    return response.readEntity(FbFriends.class);
                }
                return null;
            }
        });
    }

    public ListenableFuture<String> publishToWall(String token,String message){
        String url = fbUrl+"me/feed";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .rx()
                .post(Entity.entity(new FbFeed(message), MediaType.APPLICATION_JSON));

        return Futures.transform(future, new Function<Response, String>() {
            public String apply(Response response) {
                if(response.getStatus()==Response.Status.OK.getStatusCode()){
                    return response.readEntity(String.class);
                }
                return null;
            }
        });
    }

    //No vimos necesidad de usarlo aun.
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

