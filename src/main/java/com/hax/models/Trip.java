package com.hax.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by martin on 5/5/15.
 */
@Entity
public class Trip {
    public List<Flight> wayFlights = new ArrayList<Flight>();
    public List<Flight> returnFlights = new ArrayList<Flight>();
    public String origin;
    public String destiny;
    public Double price;
    public String state;
    @Id public Long id;
    public String wayDuration;
    public String returnDuration;
    public String originDescription;
    public String destinyDescription;


    //Dummy Contructor
    public Trip() {}

    public Trip(List<Flight> wayFlights, List<Flight> returnFlights, Double price, String origin, String destiny, String wayDuration, String returnDuration) {
        this.wayDuration = wayDuration;
        this.returnDuration = returnDuration;
        this.wayFlights = wayFlights;
        this.returnFlights = returnFlights;
        this.price = price;
        this.state = "Sin Publicar";
    }

    public String getOriginDescription() {
        return originDescription;
    }

    public void setOriginDescription(String originDescription) {
        this.originDescription = originDescription;
    }

    public String getDestinyDescription() {
        return destinyDescription;
    }

    public void setDestinyDescription(String destinyDescription) {
        this.destinyDescription = destinyDescription;
    }

    public void setWayDuration(String wayDuration){
        this.wayDuration = wayDuration;
    }
    
    public String getWayDuration(){
        return this.wayDuration;
    }
    
    public void setReturnDuration(String returnDuration){
        this.returnDuration = returnDuration;
    }
    
    public String getReturnDuration(){
        return this.returnDuration;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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

