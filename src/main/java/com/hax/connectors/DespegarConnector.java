package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

/**
 * Created by martin on 4/26/15.
 */
public class DespegarConnector {

    public DespegarConnector(){
    }





    public void getSomething(){
        //ContentResponse response = httpClient.GET("https://api.despegar.com/v3/flights/itineraries?site=ar&from=EZE&to=MIA&departure_date=2015-08-21&adults=1");
        String url = "https://api.despegar.com/v3/flights/itineraries";
        ListenableFuture<Response> stage = RxListenableFuture.newClient()
                .target(url)
                .queryParam("site", "ar")
                .queryParam("from", "EZE")
                .queryParam("to", "MIA")
                .queryParam("departure_date","2015-08-21")
                .queryParam("adults","1")
                .request()
                .header("X-ApiKey","a97e70ca025a45adb3761471eb2d9b39")
                .rx()
                .get();

        while(!stage.isDone()){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            String response = stage.get().readEntity(String.class);
            System.out.println(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
