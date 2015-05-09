package com.hax.async.utils;

/**
 * Created by martin on 27/04/15.
 */
public class Tuple2<T,E>{
    T t;
    E e;

    public Tuple2(T t, E e) {
        this.t = t;
        this.e = e;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }
}

public class Tuple3<T,E,G>{
    T t;
    E e;
    G g;

    public Tuple3(T t, E e, G g) {
        this.t = t;
        this.e = e;
        this.g = g;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public E getE() {
        return e;
    }

    public void setE(E e) {
        this.e = e;
    }

    public G getG() {
        return g;
    }

    public void setG(G g) {
        this.g = g;
    }
}