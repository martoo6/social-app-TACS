package com.hax.connectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.models.AirportResponse;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;
import java.io.IOException;


public class AirportsConnector implements AirportsConnectorInterface {
    
        //podria usar este webservice desde el frontend con JSONP
        //pero el genio de php que lo codeo tenia el debug "on" y lo rompio
        //es una lastima, porque esta bueno y no se justifica usar el back como proxy
        public ListenableFuture<String> getAirportAsync(String latitude, String longitude){
        String url = "http://airports.pidgets.com/v1/airports";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("format", "json")
                .queryParam("n", 1)
                .queryParam("runway_length_min", 8600)
                .queryParam("near",  latitude + "," + longitude)
                .request()
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, String>() {
            public String apply(Response response) {
                String json = response.readEntity(String.class);
                
                return formatJson(json);
            }
        });
    }
        
    public ListenableFuture<AirportResponse> getAirportAsync(String airportCode){
        String url = "http://airports.pidgets.com/v1/airports/" + airportCode;
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("format", "json")
                .queryParam("n", 1)
                .request()
                .rx()
                .get();

        return Futures.transform(future, new Function<Response, AirportResponse>() {
            public AirportResponse apply(Response response) {
                String json = response.readEntity(String.class);
                ObjectMapper mapper = new ObjectMapper();
                try {
                    return mapper.readValue(formatJson(json), AirportResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
    
    private String formatJson(String json){
        return json.substring(json.indexOf('{'), json.lastIndexOf('}') + 1);
    }
}
