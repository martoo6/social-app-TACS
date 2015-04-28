package com.hax.async.utils;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.hax.async.executors.*;

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
}
