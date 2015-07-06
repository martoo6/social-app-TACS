package com.hax.models.fb;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Created by martin on 6/21/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class FbFriends {
    private ArrayList<FbFriend> data;

    public ArrayList<FbFriend> getData() {
        return data;
    }

    public void setData(ArrayList<FbFriend> data) {
        this.data = data;
    }
}
