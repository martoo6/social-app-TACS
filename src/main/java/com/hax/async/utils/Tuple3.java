package com.hax.async.utils;

/**
 * Objeto que almacena 3 objetos de distinto tipo
 * @param <R1>
 * @param <R2>
 * @param <R3>
 */
public class Tuple3<R1, R2, R3>{
    R1 r1;
    R2 r2;
    R3 r3;

    public Tuple3(R1 r1, R2 r2, R3 r3) {
        this.r1 = r1;
        this.r2 = r2;
        this.r3 = r3;
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

    public R3 getR3() {
        return r3;
    }

    public void setR3(R3 r3) {
        this.r3 = r3;
    }
}
