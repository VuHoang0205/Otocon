package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.RecommendSlider;

import java.util.List;

public class SettingsResponse extends OnResponse {
    @SerializedName("data")
    List<RecommendSlider> list;

    public List<RecommendSlider> getList() {
        return list;
    }
}
