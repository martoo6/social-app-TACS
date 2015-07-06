package com.hax.models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by martin on 5/5/15.
 */
@Entity
public class Recommendation {
    public String fromUserId;
    public String toUserId;
    public Long trip;
    public RecommendationState state;
    @Id public Long id;

    public Recommendation() {
        //dummy
    }

    public Recommendation(Long tripId, String fromUserId, String toUserId) {
        this.fromUserId = fromUserId;
        this.trip = tripId;
        this.state = RecommendationState.PENDING;
        this.toUserId = toUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String toUser) {
        this.fromUserId = toUser;
    }

    public Long getTrip() {
        return trip;
    }

    public void setTrip(Long trip) {
        this.trip = trip;
    }

    public RecommendationState getState() {
        return state;
    }

    public void setState(RecommendationState state) {
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
