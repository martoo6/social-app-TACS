package com.hax.async.utils;

/**
 * Created by martin on 27/04/15.
 */

/**
 *  Objeto que almacena 2 objetos de distinto tipo
 * @param <R1>
 * @param <R2>
 */
public class Tuple2<R1, R2>{
    R1 r1;
    R2 r2;

    public Tuple2(R1 r1, R2 r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public R1 getR1() {
        return r1;
    }

    public void setR1(R1 r1) {
        this.r1 = r1;
    }

    public R2 getR2() {
        return r2;
    }

    public void setR2(R2 r2) {
        this.r2 = r2;
    }
}

