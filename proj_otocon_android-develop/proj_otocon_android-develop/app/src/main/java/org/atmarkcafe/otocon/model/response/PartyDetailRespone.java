package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PartyDetailRespone extends OnResponse {

    @SerializedName("data")
    private List<PartyDetailData> data;

    public List<PartyDetailData> getData() {
        return data;
    }

    public void setData(List<PartyDetailData> data) {
        this.data = data;
    }
}
