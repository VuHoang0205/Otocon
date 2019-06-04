package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class MenuNotifyCountResponse extends OnResponse {

    @SerializedName("total_news_unread")
    int newCount = 0;

    public int getNewCount() {
        return newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

}
