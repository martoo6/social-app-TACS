package com.hax.utils;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;

/**
 * Created by martin on 5/30/15.
 */
public class ControllerHelper {

    /**
     *
     * Metodo de conveniencia para ser usando en controllers. Permite retornar la respuesta de un ListenableFuture
     * y en caso de que el mismo falle muestra un error 400.
     *
     * @param f ListenableFuture sobre el que agregaremos un callback
     * @param asyncResponse AsyncResponse del metodo del contolador
     * @param <T> Tipo que retorna el ListenableFuture
     */
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

    public static void fail(String failMessage, final AsyncResponse asyncResponse){
        asyncResponse.resume(Response.status(400).
                entity(failMessage).
                type("text/plain").
                build());
    }
}
