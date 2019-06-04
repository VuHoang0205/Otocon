package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class MyPageMenuResponse extends OnResponse {

    @SerializedName("total")
    int total;

    @SerializedName("total_unread")
    int total_unread;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_unread() {
        return total_unread;
    }

    public void setTotal_unread(int total_unread) {
        this.total_unread = total_unread;
    }
}
