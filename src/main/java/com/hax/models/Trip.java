package com.hax.models;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */

public class Trip {
    private List<Flight> wayFlights;
    private List<Flight> returnFlights;
    private String origin;
    private String destiny;
    private BigDecimal price;
    private String state;
    private Integer id;

    //Dummy Contructor
    public Trip() {
    }

    public Trip(List<Flight> wayFlights, List<Flight> returnFlights, BigDecimal price, String origin, String destiny) {
        this.wayFlights = wayFlights;
        this.returnFlights = returnFlights;
        this.price = price;
        this.state = "Sin Publicar";
    }

    public List<Flight> getWayFlights() {
        return wayFlights;
    }

    public void setWayFlights(List<Flight> wayFlights) {
        this.wayFlights = wayFlights;
    }

    public List<Flight> getReturnFlights() {
        return returnFlights;
    }

    public void setReturnFlights(List<Flight> returnFlights) {
        this.returnFlights = returnFlights;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

