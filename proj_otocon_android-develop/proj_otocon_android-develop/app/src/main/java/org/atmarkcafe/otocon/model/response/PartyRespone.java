package org.atmarkcafe.otocon.model.response;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.utils.EntryStatus;

import java.util.ArrayList;
import java.util.List;

public class PartyRespone extends OnResponse {

    @SerializedName("total")
    public int total;

    @SerializedName("data")
    List<Party> datas;

    public List<Party> getDatas() {
        return datas == null ? new ArrayList<>() : datas;
    }
}