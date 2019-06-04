package org.atmarkcafe.otocon.model.response;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.RequestRematch;

import java.util.ArrayList;
import java.util.List;

public class ListRequestRematchResponse  extends ResponseExtension<RequestRematch>{
    @SerializedName("total")
    int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
