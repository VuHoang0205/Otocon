package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class TotalNotiRematch extends OnResponse {

    @SerializedName("total")
    private int total;

    public int getTotal() {
        return total;
    }
}