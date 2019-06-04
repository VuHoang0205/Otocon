package org.atmarkcafe.otocon.model.menunew;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.response.OnResponse;

import java.util.ArrayList;
import java.util.List;

public class MenuNewRespone extends OnResponse {

    @SerializedName("data")
    List<MenuNewModel> data;

    @SerializedName("total")
    int total;

    public List<MenuNewModel> getData() {
        return data != null ? data : new ArrayList<>();
    }

    public void setData(List<MenuNewModel> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }
}
