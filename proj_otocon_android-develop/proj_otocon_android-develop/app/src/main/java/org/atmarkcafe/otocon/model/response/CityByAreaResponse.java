package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.response.Area;

import java.util.ArrayList;
import java.util.List;

public class CityByAreaResponse extends OnResponse {

    @SerializedName("data")
    List<Area> list;

    public List<Area> getData() {
        return (list != null) ? list : new ArrayList<Area>();
    }
}