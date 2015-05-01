package com.hax.connectors;

import com.google.common.util.concurrent.ListenableFuture;
import  com.hax.async.utils.CallableWrapper;
import org.glassfish.jersey.client.rx.guava.RxListenableFuture;
import org.jvnet.hk2.annotations.Service;

import javax.ws.rs.core.Response;

import static com.google.common.util.concurrent.Futures.transform;
import static com.hax.async.utils.FutureHelper.async;

/**
 * Created by martin on 4/26/15.
 */

public class DespegarConnector implements DespegarConnectorInterface {

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

