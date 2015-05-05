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

/**
 * Helper para trabajar mas comodamente con ListenableFuture, con la menor cantidad de boilerlate posible
 */
public class FutureHelper {

    /**
     * Este metodo permite transformar un ListenableFuture al aplicarle una operacion al resultado del mismo
     * cuando termine y reotrnando un nuevo ListenableFuture. De modo que todas las operaciones sobre el mismo
     * sea comletamente asincronica.
     *
     * @param lf ListenableFuture que deseamos transformar
     * @param c CallableWrapper en el que definimos la transformacion
     * @param <T> Tipo que retorna el ListenableFuture que deseamos transformar
     * @param <E> Tipo que retorna la transformacion
     * @return Nuevo ListenableFuture
     */
    public static <T,E> ListenableFuture<E> async(ListenableFuture<T> lf, final CallableWrapper<T,E> c) {
        return transform(lf, new AsyncFunction<T, E>() {
            public ListenableFuture<E> apply(final T response) throws Exception {
                return Default.ex.submit(c.config(response).run());
            }
        });
    }


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
}
