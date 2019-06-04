package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.UserRematch;

import java.util.ArrayList;
import java.util.List;

public class UserRematchResponse extends OnResponse {
    @SerializedName("data")
    List<UserRematch> userRematches;

    @SerializedName("total")
    int total;

    public List<UserRematch> getUserRematches() {
        return userRematches == null ? new ArrayList<>() : userRematches;
    }

    public int getTotal() {
        return total;
    }
}
