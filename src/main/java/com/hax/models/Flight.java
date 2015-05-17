package com.hax.models;

import java.util.List;

/**
 * Created by martin on 5/5/15.
 */

public class Flight {
    private List<Segment> waySegments;
    private List<Segment> returnSegments;
    private String origin;
    private String destiny;
    private Double totalPrice;
    private String state;
    private Integer id;

    //Dummy Contructor
    public Flight() {
    }

    public Flight(List<Segment> waySegments, List<Segment> returnSegments, Double totalPrice, String origin, String destiny) {
        this.waySegments = waySegments;
        this.returnSegments = returnSegments;
        this.totalPrice = totalPrice;
        this.state = "Sin Publicar";
    }

    public List<Segment> getWaySegments() {
        return waySegments;
    }

    public void setWaySegments(List<Segment> waySegments) {
        this.waySegments = waySegments;
    }

    public List<Segment> getReturnSegments() {
        return returnSegments;
    }

    public void setReturnSegments(List<Segment> returnSegments) {
        this.returnSegments = returnSegments;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }
}

