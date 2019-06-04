package org.atmarkcafe.otocon.function.party.list;

import com.google.gson.annotations.SerializedName;

import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.model.response.ResponseExtension;

public class PartyListResponse extends ResponseExtension<Party> {
    @SerializedName("total")
    public int total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
