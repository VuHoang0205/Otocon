package org.atmarkcafe.otocon.model;

import com.google.gson.annotations.SerializedName;

public class AdvancedSearchItem {

    @SerializedName("icon")
    private int icon;


    @SerializedName("title")
    private String title;


    @SerializedName("type")
    private String type;


    private boolean isCheck;

    public AdvancedSearchItem(int icon, String title, String type) {
        this.icon = icon;
        this.title = title;
        this.type = type;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
