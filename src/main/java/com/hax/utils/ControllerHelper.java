package com.hax.utils;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.Default;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutionException;

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
    public static <T> Response addControllerCallback(ListenableFuture<T> f){
        try {
            return Response.ok(f.get(), "application/json").build();
        } catch (InterruptedException | ExecutionException e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    public static Response fail(String failMessage){
        return Response.serverError().entity(failMessage).build();
    }
}
