package org.atmarkcafe.otocon.model.response;

public class NotificationRespone extends OnResponse {

    private int total;
    private int total_unread;

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
