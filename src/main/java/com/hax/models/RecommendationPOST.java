package com.hax.models;

/**
 * Created by martin on 5/5/15.
 */
public class RecommendationPOST {
    private Long toUserId;
    private Long flightId;
    private String state;

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }
}
