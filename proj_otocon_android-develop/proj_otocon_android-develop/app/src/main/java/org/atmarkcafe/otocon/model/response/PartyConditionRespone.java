package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.MatchingModel;
import org.atmarkcafe.otocon.model.Party;

import java.util.ArrayList;
import java.util.List;

public class PartyConditionRespone extends OnResponse {

    @SerializedName("total")
    public int total;

    @SerializedName("data")
    List<MatchingModel> datas;

    public List<MatchingModel> getDatas() {
        return datas == null ? new ArrayList<>() : datas;
    }
}