package com.hax.async.utils;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.*;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

import static com.google.common.util.concurrent.Futures.transform;

/**
 * Created by martin on 27/04/15.
 */
public class FutureHelper {
    public static <T,E> ListenableFuture<E> async(ListenableFuture<T> lf, final CallableWrapper<T,E> c) {
        return transform(lf, new AsyncFunction<T, E>() {
            public ListenableFuture<E> apply(final T response) throws Exception {
                return Default.ex.submit(c.config(response).run());
            }
        });
    }

    public static <T> void addControllerCallback(ListenableFuture<T> f, final AsyncResponse asyncResponse){

        Futures.addCallback(f, new FutureCallback<T>() {
            public void onSuccess(T s) {
                asyncResponse.resume(s);
            }

            public void onFailure(Throwable throwable) {
                asyncResponse.resume(Response.status(400).
                        entity(throwable.getMessage()).
                        type("text/plain").
                        build());
            }
        });
    }
}
