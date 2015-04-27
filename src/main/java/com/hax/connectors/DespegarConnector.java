package com.hax.connectors;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import  com.hax.async.utils.CallableWrapper;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;

import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

import static com.google.common.util.concurrent.Futures.transform;
import static com.hax.async.utils.Async.async;

/**
 * Created by martin on 4/26/15.
 */
public class DespegarConnector {

    public DespegarConnector(){
    }


    public ListenableFuture<String> getFlights(String from, String to, String date){
        //ContentResponse response = httpClient.GET("https://api.despegar.com/v3/flights/itineraries?site=ar&from=EZE&to=MIA&departure_date=2015-08-21&adults=1");
        String url = "https://api.despegar.com/v3/flights/itineraries";
        ListenableFuture<Response> future = RxListenableFuture.newClient()
                .target(url)
                .queryParam("site", "ar")
                .queryParam("from", from)
                .queryParam("to", to)
                .queryParam("departure_date",date)
                .queryParam("adults","1")
                .request()
                .header("X-ApiKey","a97e70ca025a45adb3761471eb2d9b39")
                .rx()
                .get();

        //Se deberia de hacer asi sin mis helpers ! Horrendo...

//        AsyncFunction<Response, String> queryFunction = new AsyncFunction<Response, String>(){
//            public ListenableFuture<String> apply(final Response response) throws Exception {
//                return Default.ex.submit(new Callable<String>(){
//                    public String call() throws Exception {
//                        return response.readEntity(String.class);
//                    }
//                });
//            }
//        };
//        return transform(future, f);


        //Hermosura...

        return async(future, new CallableWrapper<Response, String>() {
            public String apply(Response result) {
                return result.readEntity(String.class);
            }
        });
    }
}

