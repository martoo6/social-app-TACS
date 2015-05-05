package com.hax.async.utils;

import java.util.concurrent.Callable;

/**
 * Created by martin on 27/04/15.
 */

/**
 * Clase de conveniencia para operar con async en FutureHelper.
 * Puesto que Callable (la implementacion que usa Guava), no recibe por parametro el
 * resultado del ListenableFuture, esta clase se encarga de que pueda recibirlo.
 *
 * @param <T>
 * @param <E>
 */
public abstract class CallableWrapper<T,E>{
    T result;
    public CallableWrapper<T,E> config(T result){
        this.result= result;
        return this;
    }
    public abstract E apply(T result);
    public Callable<E> run(){
        return new Callable<E>(){
            public E call() throws Exception {
                return apply(result);
            }
        };
    }
}
