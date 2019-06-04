package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

public class UnreadNotificationResponse extends OnResponse {

    @SerializedName("total_events_unread")
    int unreadCount = 0;

    public int getUnreadCount() {
        return unreadCount;
    }
}
