package com.hax.models;

import java.util.List;

/**
 * Created by martin on 5/3/15.
 */
public class DespegarError {
    private Integer status;
    private String message;
    private List<String> causes;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getCauses() {
        return causes;
    }

    public void setCauses(List<String> causes) {
        this.causes = causes;
    }
}
