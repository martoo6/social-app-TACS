package com.hax.connectors;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;
import com.hax.config.App;
import com.hax.models.fb.FbFeed;
import com.hax.models.fb.FbFriends;
import com.hax.models.fb.FbNotification;
import com.hax.models.fb.FbVerify;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by martin on 4/26/15.
 */

public class FacebookConnector implements FacebookConnectorInterface{
    String fbUrl = "https://graph.facebook.com/";

    public FbVerify verifyAccessToken(String token){
        String url = fbUrl+"me";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .get();

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(FbVerify.class);
        }
        return null;
    }

    public FbFriends getUserFriends(String token){
        String url = fbUrl+"me/friends?limit=500&offset=0";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .get();

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(FbFriends.class);
        }
        return null;
    }

    public String publishToWall(String token,String message){
        String url = fbUrl+"me/feed";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .post(Entity.entity(new FbFeed(message), MediaType.APPLICATION_JSON));

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(String.class);
        }
        return null;
    }

    public String publishNotification(String token, String message){
        String url = fbUrl+"me/notifications";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("access_token", token)
                .request()
                .post(Entity.entity(new FbNotification(message), MediaType.APPLICATION_JSON));

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(String.class);
        }
        return null;
    }

    public String publishNotification(String token,String toUserId, String message){
        String url = fbUrl+toUserId+"/notifications";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("access_token", getAppAccessToken())
                .queryParam("template", message)
                .request()
                .post(null);
        //.post(Entity.entity(new FbNotification(message), MediaType.APPLICATION_JSON));

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(String.class);
        }
        return null;
    }

    public String getAppAccessToken(){
        String url = fbUrl+"oauth/access_token";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("client_id", App.config.getString("facebook.api.app.id"))
                .queryParam("client_secret", App.config.getString("facebook.api.app.secret"))
                .queryParam("grant_type", "client_credentials")
                .request()
                .get();
        //.post(Entity.entity(new FbNotification(message), MediaType.APPLICATION_JSON));

        if(response.getStatus()==Response.Status.OK.getStatusCode()){
            return response.readEntity(String.class);
        }
        return null;
    }

    //No vimos necesidad de usarlo aun.
    public String getLongLivedToken(String shortLivedToken) {
        String url = "https://www.facebook.com/oauth/access_token";
        Response response =  ClientBuilder.newClient()
                .target(url)
                .queryParam("grant_type", "fb_exchange_token")
                .queryParam("client_id", App.config.getString("facebook.api.app.id"))
                .queryParam("client_secret", App.config.getString("facebook.api.app.secret"))
                .queryParam("fb_exchange_token", shortLivedToken)
                .request()
                .get();

        if(response.getStatus()!=Response.Status.OK.getStatusCode()){
            //TODO: Ver Manejo de Errores !
            //TODO: Seguramente vuelve a la UI para obtener el Short lived correcto
            throw new RuntimeException("Error !!");
        }
        return response.readEntity(String.class);
    }
}

