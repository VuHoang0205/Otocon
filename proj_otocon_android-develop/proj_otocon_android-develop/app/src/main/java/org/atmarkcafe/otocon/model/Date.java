package org.atmarkcafe.otocon.model;

public class Date {

    private String date;

    public Date(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        String data;
        date.replace("年", "-");
        date.replace("月", "-");
        date.replace("日", "-");
        data = date;
        return data;
    }
}
