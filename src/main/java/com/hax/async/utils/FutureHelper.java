package com.hax.async.utils;

import com.google.common.base.Function;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;

import static com.google.common.util.concurrent.Futures.transform;

/**
 * Helper para trabajar mas comodamente con ListenableFuture, con la menor cantidad de boilerlate posible
 */
public class FutureHelper {


    /**
     *  Compone el resultado de dos futures y retorna un nuevo future con el restultado de ambos dentro de una tupla
     * @param f1 Primer future
     * @param f2 Segundo future
     * @param <R1> El tipo de la respuesta del primer future
     * @param <R2> El tipo de la respuesta del segundo future
     * @return
     */
    public static <R1, R2> ListenableFuture<Tuple2<R1, R2>> compose(final ListenableFuture<R1> f1, final ListenableFuture<R2> f2){
        return transform(f1, new AsyncFunction<R1, Tuple2<R1, R2>>() {
            public ListenableFuture<Tuple2<R1, R2>> apply(final R1 r1) throws Exception {

                return transform(f2, new Function<R2, Tuple2<R1, R2>>() {
                    public Tuple2<R1, R2> apply(final R2 r2) {
                        return new Tuple2<R1, R2>(r1, r2);
                    }
                });
            }
        });
    }

    /**
     * Compone el resultado de tres futures y retorna un nuevo future con el resultado todos dentro de una tupla
     * @param f1 Primer future
     * @param f2 Segundo future
     * @param f3 Tercer future
     * @param <R1> El tipo de la respuesta del primer future
     * @param <R2> El tipo de la respuesta del segundo future
     * @param <R3> El tipo de la respuesta del tercer future
     * @return
     */
    public static <R1, R2, R3> ListenableFuture<Tuple3<R1, R2, R3>> compose(final ListenableFuture<R1> f1, final ListenableFuture<R2> f2,  final ListenableFuture<R3> f3){
        return transform(f1, new AsyncFunction<R1, Tuple3<R1, R2, R3>>() {
            public ListenableFuture<Tuple3<R1, R2, R3>> apply(final R1 r1) throws Exception {

                return transform(f2, new AsyncFunction<R2, Tuple3<R1, R2, R3>>() {
                    public ListenableFuture<Tuple3<R1, R2, R3>> apply(final R2 r2) throws Exception {

                        return transform(f3, new Function<R3, Tuple3<R1, R2, R3>>() {
                            public Tuple3<R1, R2, R3> apply(final R3 r3) {
                                return new Tuple3<R1, R2, R3>(r1, r2, r3);
                            }
                        });
                    }
                });

            }
        });
    }
}
